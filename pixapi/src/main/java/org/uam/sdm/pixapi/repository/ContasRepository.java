package org.uam.sdm.pixapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uam.sdm.pixapi.domain.entities.Conta;

public interface ContasRepository extends JpaRepository<Conta, UUID> {
    Conta findByChavePix(String chavePix);
}
