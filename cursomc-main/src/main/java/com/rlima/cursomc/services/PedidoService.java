package com.rlima.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rlima.cursomc.domain.ItemPedido;
import com.rlima.cursomc.domain.PagamentoComBoleto;
import com.rlima.cursomc.domain.Pedido;
import com.rlima.cursomc.domain.enums.EstadoPagamento;
import com.rlima.cursomc.repositories.ItemPedidoRepository;
import com.rlima.cursomc.repositories.PagamentoRepository;
import com.rlima.cursomc.repositories.PedidoRepository;
import com.rlima.cursomc.services.exceptions.ObjectNotFoundException;

//Camada de serviço p/ consultas de categorias - utiliza camada de acesso a dados(repository) para realizar regras de negocio
//Define endPoint
@Service
public class PedidoService {
	
	@Autowired // Para instanciar automaticamente (injeção de dependencia ou inversao de controle)
	private PedidoRepository repo; // objeto da camada de acesso a dados
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find (Integer id) { // Busca a categoria pelo Id indicado pelo controlador
		Optional<Pedido> obj = repo.findById(id); //Optional, objeto container
			//return obj.orElse(null); // impede a instancia de obj nulo - se existe ele retorna obj se não retorna o VALOR nulo		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) { //clausula instanceof para testar o tipo instanciado
			PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preecherPagamentoComBoleto(pgto, obj.getInstante());
			
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
			
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
		
	}
	
	

}
