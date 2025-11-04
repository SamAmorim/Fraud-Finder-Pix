package org.uam.sdm.pixapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uam.sdm.pixapi.domain.entities.Conta;

public interface ContasRepository extends JpaRepository<Conta, UUID> {
    Optional<Conta> findByChavesPix_Chave(String chavePix);
}
