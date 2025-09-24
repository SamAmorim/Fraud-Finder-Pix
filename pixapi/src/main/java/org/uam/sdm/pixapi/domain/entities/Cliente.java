package org.uam.sdm.pixapi.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "id_natureza", nullable = false)
    private int idNatureza;

    @ManyToOne
    @JoinColumn(name = "id_natureza", insertable = false, updatable = false)
    private Natureza natureza;

    @Column(name = "registro_nacional", nullable = false, length = 100)
    private String registroNacional;

    @Column(name = "nascido_em", nullable = false)
    private LocalDate nascidoEm;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdNatureza() {
        return idNatureza;
    }

    public void setIdNatureza(int idNatureza) {
        this.idNatureza = idNatureza;
    }

    public Natureza getNatureza() {
        return natureza;
    }

    public void setNatureza(Natureza natureza) {
        this.natureza = natureza;
    }

    public String getRegistroNacional() {
        return registroNacional;
    }

    public void setRegistroNacional(String registroNacional) {
        this.registroNacional = registroNacional;
    }

    public LocalDate getNascidoEm() {
        return nascidoEm;
    }

    public void setNascidoEm(LocalDate nascidoEm) {
        this.nascidoEm = nascidoEm;
    }
}
