package inovationbank.inovationbank.service;

import inovationbank.inovationbank.exception.ClienteJaPossuiContaException;
import inovationbank.inovationbank.exception.SaldoInsuficienteException;
import inovationbank.inovationbank.model.ContaBancaria;
import inovationbank.inovationbank.model.OperacaoModel;
import inovationbank.inovationbank.repository.ContaBancariaRepository;
import inovationbank.inovationbank.repository.OperacaoRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaBancariaService {

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private OperacaoRepository operacaoRepository;

    private  static  final Logger logger = LoggerFactory.getLogger(ContaBancariaService.class);

public  BigDecimal consultarSaldo(Long idConta) {
    ContaBancaria conta = contaBancariaRepository.findById(idConta).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
logger.info("Consulta para a conta realizada {}", idConta);
return  conta.getSaldo();
}


public  List<OperacaoModel> listaOperacoes(Long idConta) {
    logger.info("Consulta de extrato para a conta {}", idConta);
    return  operacaoRepository.findByContaIdOrderByDataHoraDesc(idConta);
}

    public void transferir(Long idContaOrigem, Long idContaDestino, BigDecimal valor) {
        ContaBancaria origem = contaBancariaRepository.findById(idContaOrigem).orElseThrow(() -> new RuntimeException("Conta não foi encontrada"));
        ContaBancaria destino = contaBancariaRepository.findById(idContaDestino).orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (origem.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para fazer a transferência");
        }

        origem.setSaldo(origem.getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));
        contaBancariaRepository.save(origem);
        contaBancariaRepository.save(destino);
    }

    public ContaBancaria depositar(Long id, double valor) {
        return contaBancariaRepository.findById(id).map(conta -> {
            if (valor <= 0) {
                throw new IllegalArgumentException("O valor do depósito precisa ser maior que 0");
            }
            conta.setSaldo(conta.getSaldo().add(BigDecimal.valueOf(valor)));
            return contaBancariaRepository.save(conta);
        }).orElseThrow(() -> new RuntimeException("A conta não existe"));
    }

    public ContaBancaria sacar(Long id, double valor) {
        return contaBancariaRepository.findById(id).map(conta -> {
            if (valor <= 0) {
                throw new IllegalArgumentException("O valor precisa ser maior que 0");
            }
            if (conta.getSaldo().compareTo(BigDecimal.valueOf(valor)) < 0) {
                throw new IllegalArgumentException("Saldo insuficiente para realizar o saque");
            }
            conta.setSaldo(conta.getSaldo().subtract(BigDecimal.valueOf(valor)));
            return contaBancariaRepository.save(conta);
        }).orElseThrow(() -> new RuntimeException("Conta bancária não encontrada"));
    }

    public List<ContaBancaria> listarTodas() {
        return contaBancariaRepository.findAll();
    }

    public Optional<ContaBancaria> buscarPorId(Long id) {
        return contaBancariaRepository.findById(id);
    }

    public ContaBancaria cadastrar(ContaBancaria contaBancaria) {
        if (contaBancariaRepository.findByClienteId(contaBancaria.getCliente().getId()).isPresent()) {
            throw new ClienteJaPossuiContaException("Cliente já está ativo");
        }
        return contaBancariaRepository.save(contaBancaria);
    }

    public ContaBancaria atualizar(Long id, ContaBancaria atualizarConta) {
        return contaBancariaRepository.findById(id).map(conta -> {
            conta.setNumeroConta(atualizarConta.getNumeroConta());
            conta.setAgencia(atualizarConta.getAgencia());
            conta.setTipoConta(atualizarConta.getTipoConta());
            conta.setSaldo(atualizarConta.getSaldo());
            conta.setSaqueDiario(atualizarConta.getSaqueDiario());
            conta.setCliente(atualizarConta.getCliente());
            conta.setAtiva(atualizarConta.isAtiva());
            return contaBancariaRepository.save(atualizarConta);
        }).orElseThrow(() -> new RuntimeException("Conta bancária não existe"));
    }

    public void deletar(Long id) {
        contaBancariaRepository.deleteById(id);
    }
}
