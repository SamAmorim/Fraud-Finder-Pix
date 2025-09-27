package org.uam.sdm.pixapi.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime data;

    @Column(name = "id_conta_origem", nullable = false)
    private UUID idContaOrigem;

    @ManyToOne
    @JoinColumn(name = "id_conta_origem", insertable = false, updatable = false)
    private Conta contaOrigem;

    @Column(name = "id_conta_destino", nullable = false)
    private UUID idContaDestino;

    @ManyToOne
    @JoinColumn(name = "id_conta_destino", insertable = false, updatable = false)
    private Conta contaDestino;

    @Column(name = "id_tipo_iniciacao_pix", nullable = false)
    private int idTipoIniciacaoPix;

    @OneToOne
    @JoinColumn(name = "id_tipo_iniciacao_pix", insertable = false, updatable = false)
    private TipoIniciacaoPix tipoIniciacaoPix;

    @Column(name = "id_finalidade_pix", nullable = false)
    private int idFinalidadePix;

    @OneToOne
    @JoinColumn(name = "id_finalidade_pix", insertable = false, updatable = false)
    private FinalidadePix finalidadePix;

    @Column(length = 100)
    private String mensagem;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public UUID getIdContaOrigem() {
        return idContaOrigem;
    }

    public void setIdContaOrigem(UUID idContaOrigem) {
        this.idContaOrigem = idContaOrigem;
    }

    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public UUID getIdContaDestino() {
        return idContaDestino;
    }

    public void setIdContaDestino(UUID idContaDestino) {
        this.idContaDestino = idContaDestino;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

    public int getIdTipoIniciacaoPix() {
        return idTipoIniciacaoPix;
    }

    public void setIdTipoIniciacaoPix(int idTipoIniciacaoPix) {
        this.idTipoIniciacaoPix = idTipoIniciacaoPix;
    }

    public TipoIniciacaoPix getTipoIniciacaoPix() {
        return tipoIniciacaoPix;
    }

    public void setTipoIniciacaoPix(TipoIniciacaoPix tipoIniciacaoPix) {
        this.tipoIniciacaoPix = tipoIniciacaoPix;
    }

    public int getIdFinalidadePix() {
        return idFinalidadePix;
    }

    public void setIdFinalidadePix(int idFinalidadePix) {
        this.idFinalidadePix = idFinalidadePix;
    }

    public FinalidadePix getFinalidadePix() {
        return finalidadePix;
    }

    public void setFinalidadePix(FinalidadePix finalidadePix) {
        this.finalidadePix = finalidadePix;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
