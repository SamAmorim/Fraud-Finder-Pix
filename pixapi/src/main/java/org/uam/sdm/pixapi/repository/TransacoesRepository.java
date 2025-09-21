package org.uam.sdm.pixapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uam.sdm.pixapi.domain.entities.Transacao;

public interface TransacoesRepository extends JpaRepository<Transacao, UUID> {

}
