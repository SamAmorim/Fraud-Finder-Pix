package org.uam.sdm.pixapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.uam.sdm.pixapi.domain.dto.contas.ObterContaPorChavePixResponse;
import org.uam.sdm.pixapi.domain.entities.Conta;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContasMapper {

    @Mapping(source = "cliente.nome", target = "nomeCliente")
    @Mapping(source = "cliente.registroNacional", target = "registroNacionalCliente")
    @Mapping(source = "instituicao.nome", target = "nomeInstituicao")
    ObterContaPorChavePixResponse contaToObterContaPorChavePixResponse(Conta conta);
}
