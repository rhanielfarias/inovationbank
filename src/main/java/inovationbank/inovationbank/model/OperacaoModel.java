package inovationbank.inovationbank.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class OperacaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    private BigDecimal valor;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private ContaBancaria conta;

    public OperacaoModel() {
        this.dataHora = LocalDateTime.now();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public ContaBancaria getConta() {
        return conta;
    }

    public void setConta(ContaBancaria conta) {
        this.conta = conta;
    }
}
