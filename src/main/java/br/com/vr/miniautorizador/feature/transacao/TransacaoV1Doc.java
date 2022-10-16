package br.com.vr.miniautorizador.feature.transacao;

import br.com.vr.miniautorizador.feature.transacao.dto.TransacaoDTO;
import br.com.vr.miniautorizador.shared.enuns.TransacaoStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Api(value = "Transações", tags = {"Transações"})
interface TransacaoV1Doc {

    @ApiOperation(value = "Registrar transação", protocols = MediaType.APPLICATION_JSON_VALUE, response = TransacaoStatusEnum.class)
    ResponseEntity<TransacaoStatusEnum> createdTransacao(@ApiParam(value = "Transação", required = true) TransacaoDTO dto);

}
