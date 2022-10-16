package br.com.vr.miniautorizador.feature.cartao;

import br.com.vr.miniautorizador.feature.cartao.mapper.CartaoMapper;
import br.com.vr.miniautorizador.shared.persistence.Cartao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartaoV1Controller.class)
public class CartaoV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CartaoService cartaoService;

    @MockBean
    CartaoMapper cartaoMapper;

    @Test
    @DisplayName("Verificar saldo do cartão")
    void shouldReturnCartao() throws Exception {
        Cartao  cartao = new Cartao();
        cartao.setNumero("teste");

        when(cartaoService.getCartaoByNumero("teste"))
                .thenReturn(Optional.of(cartao));

        MockHttpServletResponse response = mockMvc.perform(get("/cartoes/{numeroCartao}", "teste"))
                .andDo(print())
                .andReturn()
                .getResponse();

         assertEquals(response.getStatus(),200);
         assertEquals("500.00", response.getContentAsString());

    }

    @Test
    @DisplayName("Verificar caso cartão não exista")
    void shouldReturnNotFoundCartao() throws Exception {
        when(cartaoService.getCartaoByNumero("7777"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/cartoes/{numeroCartao}", "7777"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}
