package br.com.senac.estoque_api.services;

import br.com.senac.estoque_api.dtos.TipoProdutoRequestDto;
import br.com.senac.estoque_api.entidades.TipoProduto;
import br.com.senac.estoque_api.repositorios.TipoProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoProdutoService {

    @Autowired
    private TipoProdutoRepositorio tipoProdutoRepositorio;

    public List<TipoProduto> listar(){
        return tipoProdutoRepositorio.findAll();
    }

    public TipoProduto criar(TipoProdutoRequestDto tipoProduto){
        if (tipoProduto.getDescricao() == null || tipoProduto.getDescricao().isBlank()) {
            throw new RuntimeException("A descrição é obrigatória!");
        }
        if (tipoProdutoRepositorio.existsByDescricao(tipoProduto.getDescricao())) {
            throw new RuntimeException("Já existe um tipo de produto com essa descrição!");
        }

        TipoProduto tipoProdutoSaida = this.tipoProdutoRequestDtoToTipoProduto(tipoProduto);

        return tipoProdutoRepositorio.save(tipoProdutoSaida);
    }

    private TipoProduto tipoProdutoRequestDtoToTipoProduto(TipoProdutoRequestDto entrada){
        TipoProduto saida = new TipoProduto();
        saida.setDescricao(entrada.getDescricao());

        return saida;
    }
}
