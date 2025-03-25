package inovationbank.inovationbank.service;

import inovationbank.inovationbank.model.Cliente;
import inovationbank.inovationbank.model.ContaBancaria;
import inovationbank.inovationbank.repository.ContaBancariaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContaBancariaServiceTest {
    @Mock
    private ContaBancariaRepository contaBancariaRepository;

    @InjectMocks
    private ContaBancariaService contaBancariaService;

    @BeforeEach
    void iniciar() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarContaSeNaoTiverCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setCliente(cliente);
        contaBancaria.setNumeroConta("123456");
        contaBancaria.setSaldo(BigDecimal.valueOf(1000));
        when(contaBancariaRepository.findByClienteId(cliente.getId())).thenReturn(Optional.empty());
        when(contaBancariaRepository.save(any(ContaBancaria.class))).thenReturn(contaBancaria);
        ContaBancaria contaCadastrada = contaBancariaService.cadastrar(contaBancaria);
        assertNotNull(contaCadastrada);
        assertEquals("123456", contaCadastrada.getNumeroConta());
        verify(contaBancariaRepository).save(contaBancaria);

    }

    @Test
    void deveLancarExcecaoSeContaExistir() {
        Cliente cliente = new Cliente();
        cliente.setId(2L);
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setCliente(cliente);
        when(contaBancariaRepository.findByClienteId(cliente.getId())).thenReturn(Optional.of(new ContaBancaria()));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            contaBancariaService.cadastrar(contaBancaria);
        });
        assertEquals("Cliente já está ativo", exception.getMessage());
        verify(contaBancariaRepository, never()).save(any());
    }

}