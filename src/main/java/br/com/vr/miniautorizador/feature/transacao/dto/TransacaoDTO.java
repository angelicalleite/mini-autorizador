package br.com.vr.miniautorizador.feature.transacao.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransacaoDTO {

    @NotBlank(message = "Número do cartão é obrigatório")
    private String numeroCartao;

    @NotBlank(message = "Senha do cartão é obrigatório")
    private String senhaCartao;

    @NotNull(message = "Valor a ser debitado é obrigatório")
    @Digits(integer = 3, fraction = 2)
    @DecimalMin(value = "0.01", message = "Valor a ser debitado deve ser maior que zero")
    private BigDecimal valor;

}
