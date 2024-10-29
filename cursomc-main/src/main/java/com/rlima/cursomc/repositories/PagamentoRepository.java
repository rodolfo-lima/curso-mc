package com.rlima.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rlima.cursomc.domain.Pagamento;

//Camada de acesso a dados (DAO) - cada repository armazena sua propria informação nesse caso uma Categoria
// nao ha necessidade de criar repositorys paras classes que herdam de Pagamento
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{
	

}
