package br.com.vr.miniautorizador.feature.cartao;

import br.com.vr.miniautorizador.shared.persistence.Cartao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao, Long> {

    boolean existsByNumero(String numero);

    Optional<Cartao> findByNumero(String numero);

}
