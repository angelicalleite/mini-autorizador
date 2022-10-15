package br.com.vr.miniautorizador.feature.cartao.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CartaoDTO {

    @NotBlank(message = "Número do cartão é obrigatório")
    private String numeroCartao;

    @NotBlank(message = "Senha do cartão é obrigatório")
    private String senha;

}
