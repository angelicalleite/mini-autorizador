package br.com.vr.miniautorizador.feature.transacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO {

    @NotBlank(message = "Número do cartão é obrigatório")
    private String numeroCartao;

    @NotBlank(message = "Senha do cartão é obrigatório")
    private String senhaCartao;

    @Digits(integer = 3, fraction = 2)
    @NotNull(message = "Valor a ser debitado é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor a ser debitado deve ser maior que zero")
    private BigDecimal valor;

}
