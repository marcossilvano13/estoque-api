package br.com.senac.estoque_api.repositorios;

import br.com.senac.estoque_api.entidades.TipoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProdutoRepositorio extends JpaRepository<TipoProduto, Long> {
    boolean existsByDescricao(String descricao);
}
