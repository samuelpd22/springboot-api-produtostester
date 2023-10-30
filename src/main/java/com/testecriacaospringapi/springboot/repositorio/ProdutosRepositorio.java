package com.testecriacaospringapi.springboot.repositorio;

import com.testecriacaospringapi.springboot.entity.ProdutosModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProdutosRepositorio extends JpaRepository<ProdutosModelo, UUID> {
}
