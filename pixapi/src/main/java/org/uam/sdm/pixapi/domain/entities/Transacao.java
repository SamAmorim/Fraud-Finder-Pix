package org.uam.sdm.pixapi.domain.entities;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "valor", precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;
    
    @Column(nullable = false)
    private int data;
    
    private int mensagem;
    
    @Column(nullable = false)
    private int id_conta_origem;
    
    @Column(nullable = false)
    private int id_conta_destino;
    
    @Column(nullable = false)
    private int id_tipo_iniciacao_pix;
    
    @Column(nullable = false)
    private int id_finalidade_pix;

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getMensagem() {
        return mensagem;
    }

    public void setMensagem(int mensagem) {
        this.mensagem = mensagem;
    }

    public int getId_conta_origem() {
        return id_conta_origem;
    }

    public void setId_conta_origem(int id_conta_origem) {
        this.id_conta_origem = id_conta_origem;
    }

    public int getId_conta_destino() {
        return id_conta_destino;
    }

    public void setId_conta_destino(int id_conta_destino) {
        this.id_conta_destino = id_conta_destino;
    }

    public int getId_tipo_iniciacao_pix() {
        return id_tipo_iniciacao_pix;
    }

    public void setId_tipo_iniciacao_pix(int id_tipo_iniciacao_pix) {
        this.id_tipo_iniciacao_pix = id_tipo_iniciacao_pix;
    }

    public int getId_finalidade_pix() {
        return id_finalidade_pix;
    }

    public void setId_finalidade_pix(int id_finalidade_pix) {
        this.id_finalidade_pix = id_finalidade_pix;
    }
}
