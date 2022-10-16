package br.com.vr.miniautorizador.feature.cartao;

import br.com.vr.miniautorizador.shared.persistence.Cartao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor

@Repository
@Transactional
public class CartaoService {

    private final CartaoRepository repository;

    public Optional<Cartao> getCartaoByNumero(String numeroCartao) {
        return repository.findByNumero(numeroCartao);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<Cartao> saveCartao(Cartao cartao) {
        boolean existCartao = repository.existsByNumero(cartao.getNumero());
        return existCartao ? Optional.empty() : Optional.of(repository.save(cartao));
    }

}
