package inovationbank.inovationbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import inovationbank.inovationbank.Enum.TipoConta;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contas_bancarias")
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroConta;

    @Column(nullable = false)
    private String agencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConta tipoConta;

    @Column(nullable = false)
    private BigDecimal saldo;

    @Column(nullable = false)
    private BigDecimal saqueDiario;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private boolean ativa;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    public ContaBancaria() {
        this.saldo = BigDecimal.ZERO;
        this.saqueDiario = new BigDecimal("1000");
        this.ativa = true;
        this.dataCriacao = LocalDateTime.now();
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getSaqueDiario() {
        return saqueDiario;
    }

    public void setSaqueDiario(BigDecimal saqueDiario) {
        this.saqueDiario = saqueDiario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
