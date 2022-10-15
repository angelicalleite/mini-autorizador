package br.com.vr.miniautorizador.feature.cartao;

import br.com.vr.miniautorizador.feature.cartao.dto.CartaoDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor

@RestController
@RequestMapping("/cartoes")
public class CartaoV1Controller implements CartaoV1Doc {

    private final CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<CartaoDTO> createdCartao(@RequestBody CartaoDTO cartaoDTO) {
        return null;
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<Double> getSaldoByNumeroCartao(@PathVariable("numeroCartao") String numeroCartao) {
        return null;
    }

}
