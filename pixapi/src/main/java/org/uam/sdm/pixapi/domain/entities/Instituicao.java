package org.uam.sdm.pixapi.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Instituicao {
    
    @Id
    @Column(nullable = false, length = 8)
    private String ispb;

    @Column(nullable = false)
    private String nome;

    public String getIspb() {
        return ispb;
    }

    public void setIspb(String ispb) {
        this.ispb = ispb;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
