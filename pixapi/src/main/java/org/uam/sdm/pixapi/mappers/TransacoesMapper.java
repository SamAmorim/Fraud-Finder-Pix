package org.uam.sdm.pixapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixRequestDto;
import org.uam.sdm.pixapi.domain.dto.transacoes.EnviarPixResponseDto;
import org.uam.sdm.pixapi.domain.entities.Transacao;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransacoesMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contaDestino", ignore = true)
    @Mapping(target = "contaOrigem", ignore = true)
    @Mapping(target = "data", ignore = true)
    @Mapping(target = "finalidadePix", ignore = true)
    @Mapping(target = "tipoIniciacaoPix", ignore = true)
    Transacao enviarPixRequestDtoToTransacao(EnviarPixRequestDto enviarPixRequestDto);

    @Mapping(target = "nomeContaDestinoCliente", source = "contaDestino.cliente.nome")
    @Mapping(target = "registroNacionalContaDestinoCliente", source = "contaDestino.cliente.registroNacional")
    @Mapping(target = "nomeContaDestinoInstituicao", source = "contaDestino.instituicao.nome")
    @Mapping(target = "idFinalidadePix", source = "finalidadePix.id")
    @Mapping(target = "nomeFinalidadePix", source = "finalidadePix.nome")
    @Mapping(target = "idTipoIniciacaoPix", source = "tipoIniciacaoPix.id")
    @Mapping(target = "nomeTipoIniciacaoPix", source = "tipoIniciacaoPix.nome")
    EnviarPixResponseDto transacaoToEnviarPixResponseDto(Transacao transacao);
}
