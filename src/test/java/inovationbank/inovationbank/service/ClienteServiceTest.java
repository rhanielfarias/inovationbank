package inovationbank.inovationbank.service;

import inovationbank.inovationbank.model.Cliente;
import inovationbank.inovationbank.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void iniciar() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarTodosOsClientes() {
        Cliente c1 = new Cliente();
        c1.setNome("Rhaniel");
        Cliente c2 = new Cliente();
        c2.setNome("J. K. Rowling");
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(c1, c2));
        var clientes = clienteService.listarTodos();
        assertEquals(2, clientes.size());
        verify(clienteRepository).findAll();
    }

    @Test
    void deveBuscarPorId() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Fabíola");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        Optional<Cliente> resultado = clienteService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Fabíola", resultado.get().getNome());
    }

    @Test
    void deveCadastrarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Wagner");


        when(clienteRepository.save(cliente)).thenReturn(cliente);
        Cliente clienteCadastrado = clienteService.cadastrar(cliente);

        assertNotNull(clienteCadastrado);
        assertEquals("Wagner", clienteCadastrado.getNome());

    }

}

