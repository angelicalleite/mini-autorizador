package br.com.vr.miniautorizador.feature.cartao;

import br.com.vr.miniautorizador.shared.persistence.Cartao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartaoServiceTest {

    private Cartao cartao;

    @InjectMocks
    private CartaoService service;

    @Mock
    private CartaoRepository repository;

    @BeforeEach
    public void setup() {
        cartao = new Cartao();
        cartao.setId(1L);
        cartao.setNumero("12345");
        cartao.setSenha("senha");
    }

    @Test
    @DisplayName("Teste para verificar método saveCartao")
    public void whenSaveCartao_thenReturnCartao() {

        lenient().when(repository.findByNumero(cartao.getNumero()))
                .thenReturn(Optional.empty());

        when(repository.save(cartao))
                .thenReturn(cartao);

        Optional<Cartao> savedCartao = service.saveCartao(cartao);

        assertNotNull(savedCartao);
        assertTrue(savedCartao.isPresent());
        assertEquals(new BigDecimal("500.00"), savedCartao.get().getSaldo());
    }

    @Test
    @DisplayName("Teste para verificar método getCartaoByNumero")
    public void whenGetCartaoByNumero_thenReturnCartao() {
        lenient().when(repository.findByNumero("12345"))
                .thenReturn(Optional.of(cartao));

        Optional<Cartao> savedCartao = service.getCartaoByNumero(cartao.getNumero());

        assertTrue(savedCartao.isPresent());
    }
}
