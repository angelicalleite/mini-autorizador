package br.com.vr.miniautorizador.shared.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TransacaoStatusEnum {

    OK("OK"),
    SALDO_INSUFICIENTE("SALDO_INSUFICIENTE"),
    SENHA_INVALIDA("SENHA_INVALIDA"),
    CARTAO_INEXISTENTE("CARTAO_INEXISTENTE");

    private final String id;

    public static TransacaoStatusEnum getById(String id) {
        return Arrays.stream(values())
                .filter(value -> value.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", id)));
    }
}
