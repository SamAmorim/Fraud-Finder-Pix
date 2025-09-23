package org.uam.sdm.pixapi.services.impl;

import org.uam.sdm.pixapi.domain.dto.contas.ObterContaPorChavePixResponse;
import org.uam.sdm.pixapi.mappers.ContasMapper;
import org.uam.sdm.pixapi.repository.ContasRepository;
import org.uam.sdm.pixapi.services.ContasService;

public class ContasServiceImpl implements ContasService {

    private final ContasRepository contasRepository;
    private final ContasMapper contasMapper;

    public ContasServiceImpl(ContasRepository contasRepository, ContasMapper contasMapper) {
        this.contasRepository = contasRepository;
        this.contasMapper = contasMapper;
    }

    @Override
    public ObterContaPorChavePixResponse obterPorChavePix(String chavePix) {
        var conta = contasRepository.findByChavePix(chavePix);
        var response = contasMapper.contaToObterContaPorChavePixResponse(conta);
        return response;
    }
}
