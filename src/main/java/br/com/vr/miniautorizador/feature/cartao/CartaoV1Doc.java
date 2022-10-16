package br.com.vr.miniautorizador.feature.cartao;

import br.com.vr.miniautorizador.feature.cartao.dto.CartaoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

@Api(value = "Cartões", tags = {"Cartões"})
public interface CartaoV1Doc {

    @ApiOperation(value = "Criar novo cartão", protocols = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CartaoDTO> createCartao(
            @ApiParam(value = "Dados cartão para criação", required = true) CartaoDTO cartaoDTO);

    @ApiOperation(value = "Consultar saldo do cartão pelo número", protocols = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BigDecimal> getSaldoByNumeroCartao(
            @ApiParam(value = "Número do cartão para consultar", required = true, example = "6549873025634501") String numeroCartao);

}
