package br.com.vr.miniautorizador.feature.transacao;

import br.com.vr.miniautorizador.feature.cartao.CartaoService;
import br.com.vr.miniautorizador.feature.transacao.dto.TransacaoDTO;
import br.com.vr.miniautorizador.shared.enuns.TransacaoStatusEnum;
import br.com.vr.miniautorizador.shared.persistence.Cartao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(SpringExtension.class)
public class TransacaoServiceTest {

    private Cartao cartao;
    private TransacaoDTO transacaoDTO;

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private CartaoService cartaoService;

    @BeforeEach
    public void setup() {
        cartao = new Cartao();
        cartao.setSenha("teste");
        cartao.setNumero("teste");

        transacaoDTO = TransacaoDTO.builder()
                .numeroCartao("teste")
                .senhaCartao("teste")
                .valor(new BigDecimal("250.55"))
                .build();
    }

    @Test
    @DisplayName("Verificar validação transacao com sucesso")
    public void shoulReturnNull_validateSuccess() {
        lenient().when(cartaoService.getCartaoByNumero(transacaoDTO.getNumeroCartao()))
                .thenReturn(Optional.of(cartao));

        TransacaoStatusEnum transacaoStatus = transacaoService.validateTransacao(transacaoDTO);

        assertEquals(TransacaoStatusEnum.OK, transacaoStatus);
    }

    @Test
    @DisplayName("Verificar cartão inexistente da transação")
    public void shoulReturnCartaoInexistente_validateNotSuccess() {
        cartao.setNumero("teste2");

        lenient().when(cartaoService.getCartaoByNumero(transacaoDTO.getNumeroCartao()))
                .thenReturn(Optional.of(cartao));

        TransacaoStatusEnum transacaoStatus = transacaoService.validateTransacao(transacaoDTO);

        assertEquals(TransacaoStatusEnum.CARTAO_INEXISTENTE, transacaoStatus);
    }

    @Test
    @DisplayName("Verificar senha inválida da transação")
    public void shoulReturnSenhaInvalida_validateNotSuccess() {
        cartao.setSenha("teste2");

        lenient().when(cartaoService.getCartaoByNumero(transacaoDTO.getNumeroCartao()))
                .thenReturn(Optional.of(cartao));

        TransacaoStatusEnum transacaoStatus = transacaoService.validateTransacao(transacaoDTO);

        assertEquals(TransacaoStatusEnum.SENHA_INVALIDA, transacaoStatus);
    }

    @Test
    @DisplayName("Verificar saldo insuficiente para transação")
    public void shoulReturnSaldoInsuficiente_validateNotSuccess() {
        lenient().when(cartaoService.getCartaoByNumero(transacaoDTO.getNumeroCartao()))
                .thenReturn(Optional.of(cartao));

        transacaoDTO.setValor(new BigDecimal("550.00"));
        TransacaoStatusEnum transacaoStatus = transacaoService.validateTransacao(transacaoDTO);

        assertEquals(TransacaoStatusEnum.SALDO_INSUFICIENTE, transacaoStatus);
    }

//    @Test
//    @DisplayName("Verificar atualização de saldo com sucesso")
//    public void shoulReturnSaldoInsuficiente_updateTransacao() {
//        Cartao cartaUpdate = cartao;
//        cartao.setSaldo(new BigDecimal("100.00"));
//
//        lenient().when(cartaoService.updateSaldoCartao(transacaoDTO.getNumeroCartao(), transacaoDTO.getValor()))
//                .thenReturn(cartaUpdate);
//
//        transacaoDTO.setValor(new BigDecimal("400.00"));
//        TransacaoStatusEnum transacaoStatus = transacaoService.updateSaldoCartao(transacaoDTO);
//
//        assertEquals(TransacaoStatusEnum.OK, transacaoStatus);
//    }

}
