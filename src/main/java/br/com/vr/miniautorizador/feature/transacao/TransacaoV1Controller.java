package br.com.vr.miniautorizador.feature.transacao;

import br.com.vr.miniautorizador.feature.transacao.dto.TransacaoDTO;
import br.com.vr.miniautorizador.shared.enuns.TransacaoStatusEnum;
import br.com.vr.miniautorizador.shared.persistence.Cartao;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@AllArgsConstructor

@RestController
@RequestMapping("/transacoes")
public class TransacaoV1Controller implements TransacaoV1Doc {

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<String> createdTransacao(@Valid @RequestBody TransacaoDTO dto) {
        Optional<TransacaoStatusEnum> transacaoStatus = Optional.ofNullable(transacaoService.validateTransacao(dto));

        Cartao cartao = transacaoStatus.filter(e -> e.equals(TransacaoStatusEnum.OK))
                .map(e -> transacaoService.updateSaldoCartao(dto)).orElse(null);

        Integer code = transacaoStatus
                .filter(e -> e.equals(TransacaoStatusEnum.OK) && cartao != null)
                .map(e -> 201).orElse(422);

        return ResponseEntity.status(code).body(transacaoStatus.get().getId());
    }
}
