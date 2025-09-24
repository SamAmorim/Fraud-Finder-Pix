package org.uam.sdm.pixapi.domain.entities;

import java.time.LocalDateTime;
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
@Table(name = "chaves_pix")
public class ChavePix {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String chave;

    @Column(name = "id_conta", nullable = false)
    private UUID idConta;

    @ManyToOne
    @JoinColumn(name = "id_conta", insertable = false, updatable = false)
    private Conta conta;

    @Column(name = "id_tipo_chave", nullable = false)
    private int idTipoChave;

    @ManyToOne
    @JoinColumn(name = "id_tipo_chave", insertable = false, updatable = false)
    private TipoChavePix tipoChave;

    @Column(name = "cadastrada_em", nullable = false)
    private LocalDateTime cadastradaEm;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public UUID getIdConta() {
        return idConta;
    }

    public void setIdConta(UUID idConta) {
        this.idConta = idConta;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public int getIdTipoChave() {
        return idTipoChave;
    }

    public void setIdTipoChave(int idTipoChave) {
        this.idTipoChave = idTipoChave;
    }

    public TipoChavePix getTipoChave() {
        return tipoChave;
    }

    public void setTipoChave(TipoChavePix tipoChave) {
        this.tipoChave = tipoChave;
    }

    public LocalDateTime getCadastradaEm() {
        return cadastradaEm;
    }

    public void setCadastradaEm(LocalDateTime cadastradaEm) {
        this.cadastradaEm = cadastradaEm;
    }
}
