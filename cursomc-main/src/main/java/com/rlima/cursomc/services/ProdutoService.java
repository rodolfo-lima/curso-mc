package com.rlima.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rlima.cursomc.domain.Categoria;
import com.rlima.cursomc.domain.Produto;
import com.rlima.cursomc.repositories.CategoriaRepository;
import com.rlima.cursomc.repositories.ProdutoRepository;
import com.rlima.cursomc.services.exceptions.ObjectNotFoundException;

//Camada de serviço p/ consultas de categorias - utiliza camada de acesso a dados(repository) para realizar regras de negocio
//Define endPoint
@Service
public class ProdutoService {
	
	@Autowired // Para instanciar automaticamente (injeção de dependencia ou inversao de controle)
	private ProdutoRepository repo; // objeto da camada de acesso a dados
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	public Produto find (Integer id) { // Busca a categoria pelo Id indicado pelo controlador
		Optional<Produto> obj = repo.findById(id); //Optional, objeto container
			//return obj.orElse(null); // impede a instancia de obj nulo - se existe ele retorna obj se não retorna o VALOR nulo		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
		
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, 
			Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction
				.valueOf(direction), orderBy);
		
		List <Categoria> categorias = categoriaRepository.findAllById(ids);// busca todas as cat conforme id
		//return repo.search(nome, categorias, pageRequest);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		
		
	}
	
	

}
