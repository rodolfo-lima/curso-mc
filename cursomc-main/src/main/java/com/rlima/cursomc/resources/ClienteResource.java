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

import com.rlima.cursomc.domain.Cliente;
import com.rlima.cursomc.dto.ClienteDTO;
import com.rlima.cursomc.dto.ClienteNewDTO;
import com.rlima.cursomc.services.ClienteService;

// Controlador REST - acessa a classe service - recebe as requisiçoes de aplicações - comunica dados por Json ou Xml
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // O value define que sera recebido um parametro, aqui é um id
	public ResponseEntity<Cliente> find(@PathVariable Integer id) { // PathVariable declara que o id recebido sera inserido na variavel
		// poderia ser usado aqui um bloco try catch, porem isso sujaria o codigo, a ideia é que o controlador tenha clean code
		Cliente obj = service.find(id); //acessando a classe service e usar o metodo criado na classe ClienteService
		return ResponseEntity.ok().body(obj); // verifica se a operação foi feita com sucesso.		
			
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO) {
		Cliente obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDTO);
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
	public ResponseEntity<List <ClienteDTO>> findAll() { // PathVariable declara que o id recebido sera inserido na variavel
		// poderia ser usado aqui um bloco try catch, porem isso sujaria o codigo, a ideia é que o controlador tenha clean code
		List <Cliente> list = service.findAll(); //acessando a classe service e usar o metodo criado na classe ClienteService
		// para converter uma list em outra utilizando recurso do Java 8
		List <ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO); // verifica se a operação foi feita com sucesso.		
			
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET) // O value define que sera recebido um parametro, aqui é um id
	public ResponseEntity<Page <ClienteDTO>> findPage(
			// as seguintes anotacoes definem os parametros como opcionais e definem o valor padrao
			@RequestParam(value = "page", defaultValue = "0" ) Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24" ) Integer linesPerPage, //24 é multiplo de 4 e de 2, o que facilita a responsividade
			@RequestParam(value = "orderBy", defaultValue = "nome" ) String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC" )String direction) { // PathVariable declara que o id recebido sera inserido na variavel
		
		Page <Cliente> list = service.findPage(page, linesPerPage, orderBy, direction); //acessando a classe service e usar o metodo criado na classe ClienteService
		// para converter uma list em outra utilizando recurso do Java 8
		Page <ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDTO); // verifica se a operação foi feita com sucesso.
	
	}
}
