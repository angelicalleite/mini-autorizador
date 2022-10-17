package br.com.vr.miniautorizador.feature.transacao;

import br.com.vr.miniautorizador.feature.transacao.dto.TransacaoDTO;
import br.com.vr.miniautorizador.shared.enuns.TransacaoStatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor

@RestController
@RequestMapping("/transacoes")
public class TransacaoV1Controller implements TransacaoV1Doc {

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<String> createdTransacao(@Valid @RequestBody TransacaoDTO dto) {
        TransacaoStatusEnum transacaoStatus = transacaoService.validateTransacao(dto);

        return transacaoStatus != null
                ? ResponseEntity.status(422).body(transacaoStatus.getId())
                : ResponseEntity.status(201).body(transacaoService.updateSaldoCartao(dto).getId());
    }
}
