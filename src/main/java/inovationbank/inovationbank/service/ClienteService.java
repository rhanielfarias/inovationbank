package inovationbank.inovationbank.service;

import inovationbank.inovationbank.Model.Cliente;
import inovationbank.inovationbank.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return  clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return  clienteRepository.findById(id);
    }

    public Cliente cadastrar(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf()) || clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("CPF ou e-mail já cadastrados");
        }
      return   clienteRepository.save(cliente);
    }

    public  Optional<Cliente> atualizar(Long id, Cliente atualizadoCliente) {
        return  clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(atualizadoCliente.getNome());
            cliente.setEmail(atualizadoCliente.getEmail());
            cliente.setTelefone(atualizadoCliente.getTelefone());
            cliente.setEndereco(atualizadoCliente.getEndereco());
            return  clienteRepository.save(cliente);
        });
    }

    public  void  deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
throw new RuntimeException("Cliente não encontrado");
        }
         clienteRepository.deleteById(id);
    }
}
