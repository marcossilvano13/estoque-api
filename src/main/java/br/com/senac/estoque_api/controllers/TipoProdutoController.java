package br.com.senac.estoque_api.controllers;

import br.com.senac.estoque_api.dtos.TipoProdutoRequestDto;
import br.com.senac.estoque_api.entidades.TipoProduto;
import br.com.senac.estoque_api.services.TipoProdutoService;
import br.com.senac.estoque_api.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tipos-produto")
public class TipoProdutoController {

    @Autowired
    private TipoProdutoService tipoProdutoService;

    @GetMapping("/listar")
    public ResponseEntity<List<TipoProduto>> listar(){
        return ResponseEntity.ok(tipoProdutoService.listar());
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody TipoProdutoRequestDto tipoProduto){
        try {
            return ResponseEntity.status(201).body(tipoProdutoService.criar(tipoProduto));
        } catch(RuntimeException e){
            return ResponseEntity.badRequest().body(RequestUtil.parserMensagem(e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(RequestUtil.parserMensagem(e.getMessage()));
        }
    }
}
