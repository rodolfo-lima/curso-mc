package com.rlima.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rlima.cursomc.domain.Categoria;
import com.rlima.cursomc.dto.CategoriaDTO;
import com.rlima.cursomc.repositories.CategoriaRepository;
import com.rlima.cursomc.services.exceptions.DataIntegrityException;
import com.rlima.cursomc.services.exceptions.ObjectNotFoundException;

//Camada de serviço p/ consultas de categorias - utiliza camada de acesso a dados(repository) para realizar regras de negocio
//Define endPoint
@Service
public class CategoriaService {
	
	@Autowired // Para instanciar automaticamente (injeção de dependencia ou inversao de controle)
	private CategoriaRepository repo; // objeto da camada de acesso a dados
	
	public Categoria find (Integer id) { // Busca a categoria pelo Id indicado pelo controlador
		Optional<Categoria> obj = repo.findById(id); //Optional, objeto container
			//return obj.orElse(null); // impede a instancia de obj nulo - se existe ele retorna obj se não retorna o VALOR nulo		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null); // garantia
		return repo.save(obj);
	}
	
	/*public Categoria update (Categoria obj) {
		find(obj.getId());
		return repo.save(obj);		
	}*/
	
	public Categoria update (Categoria obj) {
		Categoria newObj = find(obj.getId()); // a instancia permite que o obj seja monitorado pelo JPA
		updateData(newObj, obj);
		return repo.save(newObj);
		
	}
	
	public void delete (Integer id) {
		find(id); //verifica se existe		
		try {
			repo.deleteById(id); // metodo interno do spring data
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria com produtos associados");
			
		}
	}
	
	public List <Categoria> findAll() {
		return repo.findAll();
		
	}
	
	// Recurso de paginacao do spring data
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		return repo.findAll(pageRequest);
		
	}
	
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
		
	}
	
	
	
	
	
	

}
