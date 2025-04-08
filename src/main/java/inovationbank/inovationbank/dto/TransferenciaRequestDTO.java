package inovationbank.inovationbank.dto;

import java.math.BigDecimal;

public class TransferenciaRequestDTO {
    private  Long idContaOrigem;
    private  Long idContaDestino;
    private BigDecimal valor;


    public Long getIdContaDestino() {
        return idContaDestino;
    }

    public void setIdContaDestino(Long idContaDestino) {
        this.idContaDestino = idContaDestino;
    }

    public Long getIdContaOrigem() {
        return idContaOrigem;
    }

    public void setIdContaOrigem(Long idContaOrigem) {
        this.idContaOrigem = idContaOrigem;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
