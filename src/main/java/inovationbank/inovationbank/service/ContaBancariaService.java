package inovationbank.inovationbank.service;

import inovationbank.inovationbank.model.ContaBancaria;
import inovationbank.inovationbank.repository.ContaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaBancariaService {

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    public List<ContaBancaria> listarTodas() {
        return contaBancariaRepository.findAll();
    }

    public Optional<ContaBancaria> buscarPorId(Long id) {
        return contaBancariaRepository.findById(id);
    }

    public ContaBancaria cadastrar(ContaBancaria contaBancaria) {
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
