package inovationbank.inovationbank.controller;

import inovationbank.inovationbank.model.ContaBancaria;
import inovationbank.inovationbank.service.ContaBancariaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContaBancariaController.class)

class ContaBancariaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContaBancariaService contaBancariaService;

    @Test
    void deveRetornarListaDeContas() throws Exception {
        ContaBancaria c1 = new ContaBancaria();
        c1.setNumeroConta("111111");
        c1.setSaldo(BigDecimal.valueOf(1000));

        ContaBancaria c2 = new ContaBancaria();
        c2.setNumeroConta("222222");
        c2.setSaldo(BigDecimal.valueOf(2000));
        when(contaBancariaService.listarTodas()).thenReturn(Arrays.asList(c1, c2));
        mockMvc.perform(get("/contas-bancarias")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    void deveRetornarContaBuscadaPorId() throws Exception {
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setNumeroConta("123");
        contaBancaria.setSaldo(BigDecimal.valueOf(100));
        when(contaBancariaService.buscarPorId(1L)).thenReturn(Optional.of(contaBancaria));
        mockMvc.perform(get("/contas-bancarias/1")).andExpect(status().isOk()).andExpect(jsonPath("$.numeroConta").value("123"));

    }

    @Test
    void contaDeveRetornar404SeNaoForEncontrada() throws Exception {
        when(contaBancariaService.buscarPorId(190L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/contas-bancarias/190")).andExpect(status().isNotFound());
    }

    @Test
    void deveCadastrarConta() throws Exception {
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setNumeroConta("123");
        contaBancaria.setSaldo(BigDecimal.valueOf(500));

        when(contaBancariaService.cadastrar(any())).thenReturn(contaBancaria);
        mockMvc.perform(post("/contas-bancarias").contentType(MediaType.APPLICATION_JSON).content("{\"numeroConta\":\"123\",\"saldo\":500}")).andExpect(status().isOk()).andExpect(jsonPath("$.numeroConta").value("123"));
    }

    @Test
    void deveAtualizarConta() throws Exception {
        ContaBancaria atualizada = new ContaBancaria();
        atualizada.setNumeroConta("321");
        atualizada.setSaldo(BigDecimal.valueOf(1000));
        when(contaBancariaService.atualizar(eq(1L), any())).thenReturn(atualizada);

        mockMvc.perform(put("/contas-bancarias/1").contentType(MediaType.APPLICATION_JSON).content("{\"numeroConta\":\"321\",\"saldo\":1000}")).andExpect(status().isOk()).andExpect(jsonPath("$.numeroConta").value("321"));
    }

    @Test
    void deveRetornar404AoAtualizarContaInexistente() throws Exception {
        when(contaBancariaService.atualizar(eq(1L), any())).thenThrow(new RuntimeException());
        mockMvc.perform(put("/contas-bancarias/1").contentType(MediaType.APPLICATION_JSON).content("{\"numeroConta\":\"321\",\"saldo\":1000}")).andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarConta() throws Exception {
        doNothing().when(contaBancariaService).deletar(1L);
        mockMvc.perform(delete("/contas-bancarias/1")).andExpect(status().isNoContent());
    }

}