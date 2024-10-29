package com.rlima.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rlima.cursomc.domain.Categoria;
import com.rlima.cursomc.domain.Produto;

//Camada de acesso a dados (DAO) - cada repository armazena sua propria informação nesse caso uma Categoria
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	
	@Transactional(readOnly = true)
	/*@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List <Categoria> categorias, Pageable pageRequest);*/
	
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List <Categoria> categorias, Pageable pageRequest);
	
	
}
