package br.com.vr.miniautorizador.feature.cartao;

import br.com.vr.miniautorizador.shared.persistence.Cartao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor

@Repository
@Transactional
public class CartaoService implements Serializable {

    private static final long serialVersionUID = 1L;

    private final CartaoRepository repository;

    public Optional<Cartao> getCartaoByNumero(String numeroCartao) {
        return repository.findByNumero(numeroCartao);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<Cartao> saveCartao(Cartao cartao) {
        boolean existCartao = repository.existsByNumero(cartao.getNumero());
        return existCartao ? Optional.empty() : Optional.of(repository.save(cartao));
    }

    @Transactional
    public Cartao updateSaldoCartao(String numeroCartao, BigDecimal valorTransacao) {
        Optional<Cartao> cartao = getCartaoByNumero(numeroCartao);
        cartao.ifPresent(e -> e.setSaldo(e.getSaldo().subtract(valorTransacao)));

        return cartao.map(repository::save).orElse(null);
    }

}
