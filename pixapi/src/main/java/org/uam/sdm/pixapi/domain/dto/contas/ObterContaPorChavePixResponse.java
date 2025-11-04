package org.uam.sdm.pixapi.domain.dto.contas;

import java.util.UUID;

public record ObterContaPorChavePixResponse(
    UUID id,
    String nomeCliente,
    String registroNacionalCliente,
    String ispbInstituicao,
    String nomeInstituicao
) { }
