package com.testecriacaospringapi.springboot.entity;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.UUID;

@Entity
@Table (name = "SAM-Produtos")
public class ProdutosModelo extends RepresentationModel<ProdutosModelo> implements Serializable {


    private static final long seriaVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProduto;
    private String nome;
    private BigDecimal valor;

    public UUID getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(UUID idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
