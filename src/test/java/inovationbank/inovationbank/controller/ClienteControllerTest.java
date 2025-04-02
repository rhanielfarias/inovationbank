package inovationbank.inovationbank.controller;

import inovationbank.inovationbank.model.Cliente;
import inovationbank.inovationbank.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(ClienteController.class)
class ClienteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;


    @Test
    void deveRetornarListaDeClientes() throws Exception {
        Cliente c1 = new Cliente();
        c1.setNome("Paulo");
        Cliente c2 = new Cliente();
        c2.setNome("Harry");
        when(clienteService.listarTodos()).thenReturn(Arrays.asList(c1, c2));
        mockMvc.perform(get("/clientes")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveRetornarClientePorId() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Morgana");
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/clientes/1")).andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Morgana"));
    }

    @Test
    void deveRetornar404SeClienteNaoEncontrado() throws Exception {
        when(clienteService.buscarPorId(31L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/clientes/1")).andExpect(status().isNotFound());
    }

    @Test
    void deveCadastrarCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Rhaniel");
        when(clienteService.cadastrar(any())).thenReturn(cliente);

        mockMvc.perform(post("/clientes").contentType(MediaType.APPLICATION_JSON).content("{\"nome\":\"Rhaniel\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Rhaniel"));

    }

    @Test
    void deveRetornar400AoCadastrarClienteComErro() throws Exception {
        when(clienteService.cadastrar(any())).thenThrow(new RuntimeException());
        mockMvc.perform(post("/clientes").contentType(MediaType.APPLICATION_JSON).content("{\"nome\":\"Rhaniel\"}")).andExpect(status().isBadRequest());
    }

    @Test
    void deveAtualizarCliente() throws Exception {
        Cliente atualizado = new Cliente();
        atualizado.setNome("Hermione");
        when(clienteService.atualizar(eq(1L), any())).thenReturn(Optional.of(atualizado));
        mockMvc.perform(put("/clientes/1").contentType(MediaType.APPLICATION_JSON).content("{\"nome\":\"Hermione\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Hermione"));
    }

    @Test
    void deveRetornar404AoAtualizarClienteInexistente() throws Exception {
        when(clienteService.atualizar(eq(1L), any())).thenReturn(Optional.empty());
        mockMvc.perform(put("/clientes/1").contentType(MediaType.APPLICATION_JSON).content("{\"nome\":\"Rhaniel\"}")).andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarCliente() throws Exception {
        doNothing().when(clienteService).deletar(1L);
        mockMvc.perform(delete("/clientes/1")).andExpect(status().isNoContent());
    }

    @Test
    void deveRetornar404AoDeletarClienteInxistente() throws Exception {
        doThrow(new RuntimeException("Cliente não encontrado")).when(clienteService).deletar(831L);

        mockMvc.perform(delete("/clientes/831")).andExpect(status().isNotFound());
    }

}