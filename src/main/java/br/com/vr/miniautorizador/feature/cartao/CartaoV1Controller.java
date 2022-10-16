package br.com.vr.miniautorizador.feature.cartao;

import br.com.vr.miniautorizador.feature.cartao.dto.CartaoDTO;
import br.com.vr.miniautorizador.feature.cartao.mapper.CartaoMapper;
import br.com.vr.miniautorizador.shared.persistence.Cartao;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor

@RestController
@RequestMapping("/cartoes")
public class CartaoV1Controller implements CartaoV1Doc {

    private final CartaoService cartaoService;
    private final CartaoMapper cartaoMapper;

    @PostMapping
    public ResponseEntity<CartaoDTO> createCartao(@Valid @RequestBody CartaoDTO cartaoDTO) {
        Optional<Cartao> cartao = cartaoService.saveCartao(cartaoMapper.toEntity(cartaoDTO));

        return cartao.map(e -> ResponseEntity.status(201).body(cartaoMapper.toDTO(e)))
                .orElseGet(() -> ResponseEntity.status(402).body(cartaoDTO));
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> getSaldoByNumeroCartao(@PathVariable("numeroCartao") String numeroCartao) {
        Optional<Cartao> cartao = cartaoService.getCartaoByNumero(numeroCartao);

        return cartao.map(e -> ResponseEntity.status(200).body(e.getSaldo()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
