package br.com.vr.miniautorizador.feature.cartao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartaoDTO {

    @NotBlank(message = "Número do cartão é obrigatório")
    private String numeroCartao;

    @NotBlank(message = "Senha do cartão é obrigatório")
    private String senha;

}
