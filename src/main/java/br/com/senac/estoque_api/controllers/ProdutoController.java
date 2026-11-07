package br.com.senac.estoque_api.controllers;

import br.com.senac.estoque_api.dtos.ProdutoRequestDto;
import br.com.senac.estoque_api.entidades.Produto;
import br.com.senac.estoque_api.services.ProdutoService;
import br.com.senac.estoque_api.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Produto>> listar(@RequestParam(required = false) String busca, @RequestParam(required = false) Boolean ativo){
        return ResponseEntity.ok(produtoService.listar(busca, ativo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(produtoService.buscarPorId(id));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(RequestUtil.parserMensagem(e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(RequestUtil.parserMensagem(e.getMessage()));
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody ProdutoRequestDto produto){
        try {
            return ResponseEntity.status(201).body(produtoService.criar(produto));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(RequestUtil.parserMensagem(e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(RequestUtil.parserMensagem(e.getMessage()));
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ProdutoRequestDto produto){
        try {
            return ResponseEntity.ok(produtoService.atualizar(id,produto));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(RequestUtil.parserMensagem(e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(RequestUtil.parserMensagem(e.getMessage()));
        }

    }


}
