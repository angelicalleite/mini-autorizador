package br.com.vr.miniautorizador.feature.transacao;

import br.com.vr.miniautorizador.feature.cartao.CartaoService;
import br.com.vr.miniautorizador.feature.transacao.dto.TransacaoDTO;
import br.com.vr.miniautorizador.shared.enuns.TransacaoStatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransacaoV1Controller.class)
public class TransacaoV1ControllerTest {

    private TransacaoDTO transacaoDTO;

    @Autowired
    private MockMvc mock;

    @MockBean
    private TransacaoService transacaoService;

    @MockBean
    private CartaoService cartaoService;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        transacaoDTO = TransacaoDTO.builder()
                .numeroCartao("teste")
                .senhaCartao("teste")
                .valor(new BigDecimal("250.55"))
                .build();
    }

    @Test
    @DisplayName("Verificar execução transação com sucesso")
    public void shouldReturnOK_savedTransacao() throws Exception {
        lenient().when(transacaoService.validateTransacao(transacaoDTO))
                .thenReturn(null);

        when(transacaoService.updateSaldoCartao(transacaoDTO))
                .thenReturn(TransacaoStatusEnum.OK);

        mock.perform(post("/transacoes")
                        .content(mapper.writeValueAsString(transacaoDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("OK"));
    }

    @Test
    @DisplayName("Verificar execução transação com saldo insuficiente")
    public void shouldReturnSALDO_INSUFICIENTE_savedTransacao() throws Exception {
        lenient().when(transacaoService.validateTransacao(transacaoDTO))
                .thenReturn(TransacaoStatusEnum.SALDO_INSUFICIENTE);

        when(transacaoService.updateSaldoCartao(transacaoDTO))
                .thenReturn(TransacaoStatusEnum.SALDO_INSUFICIENTE);

        mock.perform(post("/transacoes")
                        .content(mapper.writeValueAsString(transacaoDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("SALDO_INSUFICIENTE"));
    }

    @Test
    @DisplayName("Verificar se senha é válida")
    public void shouldReturnSENHA_INVALIDA_savedTransacao() throws Exception {
        lenient().when(transacaoService.validateTransacao(transacaoDTO))
                .thenReturn(TransacaoStatusEnum.SENHA_INVALIDA);

        when(transacaoService.updateSaldoCartao(transacaoDTO))
                .thenReturn(TransacaoStatusEnum.SENHA_INVALIDA);

        mock.perform(post("/transacoes")
                        .content(mapper.writeValueAsString(transacaoDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("SENHA_INVALIDA"));
    }

    @Test
    @DisplayName("Verificar se número cartão existe")
    public void shouldReturnCARTAO_INEXISTENTE_savedTransacao() throws Exception {
        lenient().when(cartaoService.getCartaoByNumero(transacaoDTO.getNumeroCartao()))
                .thenReturn(Optional.empty());

        when(transacaoService.validateTransacao(transacaoDTO))
                .thenReturn(TransacaoStatusEnum.CARTAO_INEXISTENTE);

        when(transacaoService.updateSaldoCartao(transacaoDTO))
                .thenReturn(TransacaoStatusEnum.CARTAO_INEXISTENTE);

        mock.perform(post("/transacoes")
                        .content(mapper.writeValueAsString(transacaoDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("CARTAO_INEXISTENTE"));
    }
}
