package br.com.senac.estoque_api.services;

import br.com.senac.estoque_api.dtos.ProdutoRequestDto;
import br.com.senac.estoque_api.entidades.Produto;
import br.com.senac.estoque_api.entidades.TipoProduto;
import br.com.senac.estoque_api.repositorios.ProdutoRepositorio;
import br.com.senac.estoque_api.repositorios.TipoProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    private TipoProdutoRepositorio tipoProdutoRepositorio;

    public List<Produto> listar(String busca, Boolean ativo){
        String termo = (busca == null) ? "" : busca.trim();
        return produtoRepositorio.buscar(termo, ativo);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
    }

    public Produto criar(ProdutoRequestDto produto){
        this.validarCamposObrigatorios(produto);

        if (produtoRepositorio.existsByCodigo(produto.getCodigo())) {

            throw new RuntimeException("Já existe um produto com esse código!");
        }
        if (produtoRepositorio.existsByCodigoBarras(produto.getCodigoBarras())){

            throw new RuntimeException("Já existe um produto com esse código de barras!");
        }

        TipoProduto tipoProduto = this.buscarTipoProduto(produto.getIdTipoProduto());

        Produto produtoSaida = new Produto();
        this.preencherProduto(produtoSaida, produto, tipoProduto);
        produtoSaida.setSaldoAtual(0);
        produtoSaida.setAtivo(true);

        return produtoRepositorio.save(produtoSaida);
    }

    public Produto atualizar(Long id, ProdutoRequestDto produto){
        Produto produtoExistente = this.buscarPorId(id);

        this.validarCamposObrigatorios(produto);

        if(produtoRepositorio.existsByCodigoAndIdProdutoNot(produto.getCodigo(),id)){
            throw new RuntimeException("Já existe outro produto com esse código!");
        }

        if (produtoRepositorio.existsByCodigoBarrasAndIdProdutoNot(produto.getCodigoBarras(), id)){
            throw new RuntimeException("Já existe outro produto com esse código de barras!");
        }

        TipoProduto tipoProduto = this.buscarTipoProduto(produto.getIdTipoProduto());

        this.preencherProduto(produtoExistente, produto, tipoProduto);

        if (produto.getAtivo() != null){
            produtoExistente.setAtivo(produto.getAtivo());
        }

        return produtoRepositorio.save(produtoExistente);
    }

    private TipoProduto buscarTipoProduto(Long idTipoProduto){
        return tipoProdutoRepositorio.findById(idTipoProduto)
                .orElseThrow(() -> new RuntimeException("Tipo de produto não encontrado!"));
    }

    private void validarCamposObrigatorios(ProdutoRequestDto produto){
        if (this.estaVazio(produto.getCodigo())){
            throw new RuntimeException("O código é obrigatório!");
        }
        if (this.estaVazio(produto.getCodigoBarras())){
            throw new RuntimeException("O código de barras é obrigatório!");
        }
        if (this.estaVazio(produto.getNome())){
            throw new RuntimeException("O nome é obrigatório!");
        }
        if (this.estaVazio(produto.getUnidadeMedida())){
            throw new RuntimeException("A unidade de medida é obrigatória!");
        }
        if (produto.getIdTipoProduto() == null){
            throw new RuntimeException("O tipo de produto é obrigatório!");
        }
    }

    private boolean estaVazio(String valor){
        return valor == null || valor.isBlank();
    }

    private void preencherProduto(Produto destino, ProdutoRequestDto origem, TipoProduto tipoProduto){
        destino.setCodigo(origem.getCodigo());
        destino.setCodigoBarras(origem.getCodigoBarras());
        destino.setNome(origem.getNome());
        destino.setDescricao(origem.getDescricao());
        destino.setUnidadeMedida(origem.getUnidadeMedida());
        destino.setTipoProduto(tipoProduto);
    }
}
