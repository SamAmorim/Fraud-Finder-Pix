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

    @ManyToOne
    @JoinColumn(name = "id_conta_origem")
    private Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name = "id_conta_destino")
    private Conta contaDestino;

    @OneToOne
    @JoinColumn(name = "id_tipo_iniciacao_pix")
    private TipoIniciacaoPix tipoIniciacaoPix;

    @OneToOne
    @JoinColumn(name = "id_finalidade_pix")
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

    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

    public TipoIniciacaoPix getTipoIniciacaoPix() {
        return tipoIniciacaoPix;
    }

    public void setTipoIniciacaoPix(TipoIniciacaoPix tipoIniciacaoPix) {
        this.tipoIniciacaoPix = tipoIniciacaoPix;
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
