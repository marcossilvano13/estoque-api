package br.com.senac.estoque_api.repositorios;

import br.com.senac.estoque_api.entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoBarras(String codigoBarras);

    boolean existsByCodigoAndIdProdutoNot(String codigo, Long idProduto);

    boolean existsByCodigoBarrasAndIdProdutoNot(String codigoBarras, Long idProduto);

    @Query("SELECT p FROM Produto p " +
            "WHERE (LOWER(p.nome) LIKE LOWER(CONCAT('%', :busca, '%')) " +
            "   OR LOWER(p.codigo) LIKE LOWER(CONCAT('%', :busca, '%')) " +
            "   OR p.codigoBarras LIKE CONCAT('%', :busca, '%')) " +
            "AND (:ativo IS NULL OR p.ativo = :ativo) " +
            "ORDER BY p.nome")
    List<Produto> buscar(@Param("busca") String busca, @Param("ativo") Boolean ativo);
}
