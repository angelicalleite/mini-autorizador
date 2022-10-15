package br.com.vr.miniautorizador.feature.cartao.mapper;

import br.com.vr.miniautorizador.core.mapper.BaseMapper;
import br.com.vr.miniautorizador.feature.cartao.dto.CartaoDTO;
import br.com.vr.miniautorizador.shared.persistence.Cartao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CartaoMapper extends BaseMapper<Cartao, CartaoDTO> {

    @Mappings({@Mapping(target = "numeroCartao", source = "numero")})
    CartaoDTO toDTO(Cartao entity);

    @Mappings({@Mapping(target = "numero", source = "numeroCartao")})
    Cartao toEntity(CartaoDTO dto);

}
