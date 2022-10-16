package br.com.vr.miniautorizador.feature.cartao;

import br.com.vr.miniautorizador.shared.persistence.Cartao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CartaoRepositoryTest {

    private Cartao cartao;

    @Autowired
    private CartaoRepository repository;

    @BeforeEach
    public void setup() {
        cartao = new Cartao();
        cartao.setNumero("teste");
        cartao.setSenha("teste");
    }

    @Test
    @DisplayName("Teste para verificar inserção novo cartão tem valor de saldo default de 500")
    public void whenSaveCartao_checkDefaultValueSaldo() {
        Cartao cartaoSaved = repository.save(cartao);

        assertNotNull(cartaoSaved);
        assertEquals(cartao.getId(), cartaoSaved.getId());
        assertEquals(cartao.getSenha(), cartaoSaved.getSenha());
        assertEquals(new BigDecimal("500.00"), cartaoSaved.getSaldo());
    }

    @Test
    @DisplayName("Teste verificar se cartão com número duplicado não são persistidos")
    public void whenSaveCartao_checkDuplicatedNumeroThrows() {
        Optional<Cartao> cartaoSaved = repository.findByNumero("12345");

        assertTrue(cartaoSaved.isPresent());

        Cartao duplicatedNumero = new Cartao();
        duplicatedNumero.setNumero(cartaoSaved.get().getNumero());
        duplicatedNumero.setSenha("teste2");

        assertThrows(DataIntegrityViolationException.class, () -> repository.save(duplicatedNumero));
    }
}
