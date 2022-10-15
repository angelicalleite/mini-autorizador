package br.com.vr.miniautorizador.feature.cartao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CartaoService {

    private final CartaoRepository repository;

}
