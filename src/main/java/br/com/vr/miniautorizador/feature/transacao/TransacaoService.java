package br.com.vr.miniautorizador.feature.transacao;

import br.com.vr.miniautorizador.feature.cartao.CartaoService;
import br.com.vr.miniautorizador.feature.transacao.dto.TransacaoDTO;
import br.com.vr.miniautorizador.shared.enuns.TransacaoStatusEnum;
import br.com.vr.miniautorizador.shared.persistence.Cartao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor

@Service
@Transactional
public class TransacaoService implements Serializable {

    private static final long serialVersionUID = 1L;

    private final CartaoService cartaoService;

    public TransacaoStatusEnum validateTransacao(TransacaoDTO transacaoDTO) {
        Optional<Cartao> cartao = cartaoService.getCartaoByNumero(transacaoDTO.getNumeroCartao());

        TransacaoStatusEnum status = this.validateCartao(cartao);
        status = status != null ? status : this.validateSenha(cartao, transacaoDTO.getSenhaCartao());

        return status != null ? status : this.validateSaldo(cartao, transacaoDTO.getValor());
    }

    public TransacaoStatusEnum updateSaldoCartao(TransacaoDTO transacaoDTO) {
        Cartao cartao = cartaoService.updateSaldoCartao(transacaoDTO.getNumeroCartao(), transacaoDTO.getValor());
        return cartao != null ? TransacaoStatusEnum.OK : null;
    }

    private TransacaoStatusEnum validateCartao(Optional<Cartao> cartao) {
        return !cartao.isPresent() ? TransacaoStatusEnum.CARTAO_INEXISTENTE : null;
    }

    private TransacaoStatusEnum validateSenha(Optional<Cartao> cartao, String senhaTransacao) {
        return cartao.filter(e -> !e.getSenha().equals(senhaTransacao))
                .map(e -> TransacaoStatusEnum.SENHA_INVALIDA)
                .orElse(null);
    }

    private TransacaoStatusEnum validateSaldo(Optional<Cartao> cartao, BigDecimal valorTransacao) {
        return cartao.filter(e -> e.getSaldo().subtract(valorTransacao).compareTo(BigDecimal.ZERO) <= 0)
                .map(e -> TransacaoStatusEnum.SALDO_INSUFICIENTE)
                .orElse(null);
    }

}
