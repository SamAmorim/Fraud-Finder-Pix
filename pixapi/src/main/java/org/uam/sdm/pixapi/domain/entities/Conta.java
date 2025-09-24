package org.uam.sdm.pixapi.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "contas")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal saldo;

    @Column(name = "aberta_em", nullable = false)
    private LocalDateTime abertaEm;

    @Column(nullable = false, length = 4)
    private String agencia;

    @Column(nullable = false, length = 10)
    private String numero;

    @Column(name = "id_tipo", nullable = false)
    private int idTipo;

    @ManyToOne
    @JoinColumn(name = "id_tipo", insertable = false, updatable = false)
    private TipoConta tipoConta;

    @Column(name = "id_cliente", nullable = false)
    private UUID idCliente;

    @ManyToOne
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private Cliente cliente;

    @Column(name = "ispb_instituicao", nullable = false, length = 8)
    private String ispbInstituicao;

    @ManyToOne
    @JoinColumn(name = "ispb_instituicao", insertable = false, updatable = false)
    private Instituicao instituicao;

    @OneToMany(mappedBy = "conta")
    private List<ChavePix> chavesPix;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getAbertaEm() {
        return abertaEm;
    }

    public void setAbertaEm(LocalDateTime abertaEm) {
        this.abertaEm = abertaEm;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public UUID getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(UUID idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getIspbInstituicao() {
        return ispbInstituicao;
    }

    public void setIspbInstituicao(String ispbInstituicao) {
        this.ispbInstituicao = ispbInstituicao;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public List<ChavePix> getChavesPix() {
        return chavesPix;
    }

    public void setChavesPix(List<ChavePix> chavesPix) {
        this.chavesPix = chavesPix;
    }
}
