package br.com.vr.miniautorizador.feature.cartao;

import br.com.vr.miniautorizador.feature.cartao.dto.CartaoDTO;
import br.com.vr.miniautorizador.feature.cartao.mapper.CartaoMapper;
import br.com.vr.miniautorizador.shared.persistence.Cartao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartaoV1Controller.class)
public class CartaoV1ControllerTest {

    private Cartao cartao;

    @Autowired
    private MockMvc mock;

    @MockBean
    private CartaoService cartaoService;

    @MockBean
    private CartaoMapper cartaoMapper;

    @BeforeEach
    public void setup() {
        cartao = new Cartao();
        cartao.setNumero("teste");
        cartao.setSenha("teste");
    }

    @Test
    public void saveCartaoSuccess() throws Exception {
        lenient().when(cartaoService.saveCartao(any()))
                .thenReturn(Optional.of(cartao));

        when(cartaoMapper.toDTO(any())).thenReturn(any());

        CartaoDTO dto = cartaoMapper.toDTO(cartao);

        mock.perform(post("/cartoes")
                        .content(new ObjectMapper().writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cartao)));
    }

    @Test
    @DisplayName("Verificar saldo do cartão")
    void shouldReturnSaldoCartao() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setNumero("teste");

        when(cartaoService.getCartaoByNumero("teste"))
                .thenReturn(Optional.of(cartao));

        MockHttpServletResponse response = mock.perform(get("/cartoes/{numeroCartao}", "teste"))
                .andDo(print())
                .andReturn()
                .getResponse();

        assertEquals(response.getStatus(), 200);
        assertEquals("500.00", response.getContentAsString());
    }

    @Test
    @DisplayName("Verificar se número do cartão não existir")
    void shouldReturnNotFoundNumeroCartao() throws Exception {
        when(cartaoService.getCartaoByNumero("7777"))
                .thenReturn(Optional.empty());

        mock.perform(get("/cartoes/{numeroCartao}", "7777"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""))
                .andDo(print());
    }

}
