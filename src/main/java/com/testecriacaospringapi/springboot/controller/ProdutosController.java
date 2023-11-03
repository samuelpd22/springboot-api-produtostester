package com.testecriacaospringapi.springboot.controller;

import com.testecriacaospringapi.springboot.dto.ProdutoDTO;
import com.testecriacaospringapi.springboot.entity.ProdutosModelo;
import com.testecriacaospringapi.springboot.repositorio.ProdutosRepositorio;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProdutosController {

    @Autowired
    ProdutosRepositorio produtosRepositorio; // VAI CHAMAR OS METODOS DO JPAREPOSITORY

    @PostMapping("/produtos") //VAI CRIAR OS PRODUTOS
    public ResponseEntity<ProdutosModelo> salvaProduto(@RequestBody @Valid ProdutoDTO produtoDTO){
        var produtosModelo = new ProdutosModelo();
        BeanUtils.copyProperties(produtoDTO , produtosModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtosRepositorio.save(produtosModelo));
    }
    @GetMapping("/produtos") // VAI PEGAR OS PROTUDOS
    public ResponseEntity<List<ProdutosModelo>> pegaTodosProdutos(){
        List<ProdutosModelo> listaprodutos = produtosRepositorio.findAll();
        if(!listaprodutos.isEmpty()){
            for (ProdutosModelo produto : listaprodutos){
                UUID id = produto.getIdProduto();
                produto.add(linkTo(methodOn(ProdutosController.class).pegarPorId(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaprodutos);
    }
    @GetMapping("/produtos/{id}") // VAI PEGAR OS PRODUTOS PELO ID
    public ResponseEntity<Object> pegarPorId(@PathVariable(value = "id")UUID id){
        Optional<ProdutosModelo> produtoid = produtosRepositorio.findById(id);
        if(produtoid.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        produtoid.get().add(linkTo(methodOn(ProdutosController.class).pegaTodosProdutos()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(produtoid.get());
    }

    @PutMapping("produtos/{id}") // VAI ATUALIZAR OS PRODUTOS PELO ID
    public ResponseEntity<Object> atualizaProduto(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProdutoDTO
                                                   produtoDTO) {
        Optional<ProdutosModelo> produtoid = produtosRepositorio.findById(id);
        if (produtoid.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        var ProdutosModelo = produtoid.get();
        BeanUtils.copyProperties(produtoDTO , ProdutosModelo);
        return ResponseEntity.status(HttpStatus.OK).body(produtosRepositorio.save(ProdutosModelo));
    }
    @DeleteMapping("/produtos/{id}") // VAI PEGAR O PRODUTO E DELETAR PELO ID
    public ResponseEntity<Object> deletarProdutos(@PathVariable(value = "id") UUID id){
        Optional<ProdutosModelo> produtoid = produtosRepositorio.findById(id);
        if (produtoid.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        produtosRepositorio.delete(produtoid.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso.");
    }
}
