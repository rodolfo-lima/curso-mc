package com.rlima.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rlima.cursomc.domain.Categoria;
import com.rlima.cursomc.dto.CategoriaDTO;
import com.rlima.cursomc.services.CategoriaService;

// Controlador REST - acessa a classe service - recebe as requisiçoes de aplicações - comunica dados por Json ou Xml
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource { // os metodos são declarados no controlador e depois implementados no service // chamada do metodo
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // O value define que sera recebido um parametro, aqui é um id
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { // PathVariable declara que o id recebido sera inserido na variavel
		// poderia ser usado aqui um bloco try catch, porem isso sujaria o codigo, a ideia é que o controlador tenha clean code
		Categoria obj = service.find(id); //acessando a classe service e usar o metodo criado na classe CategoriaService
		return ResponseEntity.ok().body(obj); // verifica se a operação foi feita com sucesso.		
			
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO) {
		Categoria obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id) {
		Categoria obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) // O value define que sera recebido um parametro, aqui é um id
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(method = RequestMethod.GET) // O value define que sera recebido um parametro, aqui é um id
	public ResponseEntity<List <CategoriaDTO>> findAll() { // PathVariable declara que o id recebido sera inserido na variavel
		// poderia ser usado aqui um bloco try catch, porem isso sujaria o codigo, a ideia é que o controlador tenha clean code
		List <Categoria> list = service.findAll(); //acessando a classe service e usar o metodo criado na classe CategoriaService
		// para converter uma list em outra utilizando recurso do Java 8
		List <CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO); // verifica se a operação foi feita com sucesso.		
			
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET) // O value define que sera recebido um parametro, aqui é um id
	public ResponseEntity<Page <CategoriaDTO>> findPage(
			// as seguintes anotacoes definem os parametros como opcionais e definem o valor padrao
			@RequestParam(value = "page", defaultValue = "0" ) Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24" ) Integer linesPerPage, //24 é multiplo de 4 e de 2, o que facilita a responsividade
			@RequestParam(value = "orderBy", defaultValue = "nome" ) String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC" )String direction) { // PathVariable declara que o id recebido sera inserido na variavel
		
		Page <Categoria> list = service.findPage(page, linesPerPage, orderBy, direction); //acessando a classe service e usar o metodo criado na classe CategoriaService
		// para converter uma list em outra utilizando recurso do Java 8
		Page <CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDTO); // verifica se a operação foi feita com sucesso.		
			
	}
	
	
	
	
	
	
	
	
	
	
	
}
