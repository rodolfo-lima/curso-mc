package com.rlima.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlima.cursomc.domain.Pedido;

//Camada de acesso a dados (DAO) - cada repository armazena sua propria informação nesse caso uma Categoria
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
	

}
