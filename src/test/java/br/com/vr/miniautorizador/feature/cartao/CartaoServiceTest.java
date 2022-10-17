package br.com.vr.miniautorizador.feature.cartao;

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
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CartaoServiceTest {

    private Cartao cartao;

    @InjectMocks
    private CartaoService service;

    @Mock
    private CartaoRepository repository;

    @BeforeEach
    public void setup() {
        cartao = new Cartao();
        cartao.setNumero("12345");
        cartao.setSenha("senha");
    }

    @Test
    @DisplayName("Verificar criação do cartão")
    public void whenSaveCartao_thenReturnCartao() {
        lenient().when(repository.findByNumero(cartao.getNumero()))
                .thenReturn(Optional.empty());

        when(repository.save(cartao))
                .thenReturn(cartao);

        Optional<Cartao> savedCartao = service.saveCartao(cartao);

        assertTrue(savedCartao.isPresent());
        assertEquals(new BigDecimal("500.00"), savedCartao.get().getSaldo());
    }

    @Test
    @DisplayName("Verificar consulta cartão por número")
    public void whenGetCartaoByNumero_thenReturnCartao() {
        lenient().when(repository.findByNumero(cartao.getNumero()))
                .thenReturn(Optional.of(cartao));

        Optional<Cartao> savedCartao = service.getCartaoByNumero(cartao.getNumero());

        assertTrue(savedCartao.isPresent());
    }

    @Test
    @DisplayName("Verificar se atualização do saldo é efetivada")
    public void whenUpdateSaldoCartao_theValorTrasacaoSubtract() {
        lenient().when(repository.findByNumero(cartao.getNumero()))
                .thenReturn(Optional.of(cartao));

        when(repository.save(cartao))
                .thenReturn(cartao);

        Cartao savedUpdate = service.updateSaldoCartao(cartao.getNumero(), new BigDecimal("499.99"));

        assertNotNull(savedUpdate);
        assertTrue(savedUpdate.getSaldo().compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test
    @DisplayName("Verificar se atualização do saldo não é efetivada para saldo insuficiente")
    public void whenUpdateSaldoCartao_theReturnCartao() {
        lenient().when(repository.findByNumero(cartao.getNumero()))
                .thenReturn(Optional.of(cartao));

        when(repository.save(cartao))
                .thenReturn(cartao);

        Cartao savedUpdate = service.updateSaldoCartao(cartao.getNumero(), new BigDecimal("500.99"));

        assertNotNull(savedUpdate);
        assertTrue(savedUpdate.getSaldo().compareTo(BigDecimal.ZERO) >= 0);
        assertEquals(new BigDecimal("500.00"), savedUpdate.getSaldo());
    }
}
