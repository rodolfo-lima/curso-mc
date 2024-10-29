package com.rlima.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rlima.cursomc.domain.Produto;
import com.rlima.cursomc.dto.ProdutoDTO;
import com.rlima.cursomc.resources.utils.URL;
import com.rlima.cursomc.services.ProdutoService;

// Controlador REST - acessa a classe service - recebe as requisiçoes de aplicações - comunica dados por Json ou Xml
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // O value define que sera recebido um parametro, aqui é um id
	public ResponseEntity<Produto> find(@PathVariable Integer id) { // PathVariable declara que o id recebido sera inserido na variavel
		// poderia ser usado aqui um bloco try catch, porem isso sujaria o codigo, a ideia é que o controlador tenha clean code
		Produto obj = service.find(id); //acessando a classe service e usar o metodo criado na classe ProdutoService
		return ResponseEntity.ok().body(obj); // verifica se a operação foi feita com sucesso.		
			
	}
	
	@RequestMapping(method = RequestMethod.GET) // O value define que sera recebido um parametro, aqui é um id
	public ResponseEntity<Page <ProdutoDTO>> findPage(
			// as seguintes anotacoes definem os parametros como opcionais e definem o valor padrao
			@RequestParam(value = "nome", defaultValue = "" ) String nome,
			@RequestParam(value = "categorias", defaultValue = "" ) String categorias,
			@RequestParam(value = "page", defaultValue = "0" ) Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24" ) Integer linesPerPage, //24 é multiplo de 4 e de 2, o que facilita a responsividade
			@RequestParam(value = "orderBy", defaultValue = "nome" ) String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC" )String direction) { // PathVariable declara que o id recebido sera inserido na variavel
		
		String nomeDecoded = URL.decodeParam(nome); //nome precisa ser tratado antes de ser usado como parametro
		List <Integer> ids = URL.decodeIntList(categorias);
		Page <Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction); //acessando a classe service e usar o metodo criado na classe CategoriaService
		// para converter uma list em outra utilizando recurso do Java 8
		Page <ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDTO); // verifica se a operação foi feita com sucesso.		
			
	}
	
	
}
