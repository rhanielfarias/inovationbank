package inovationbank.inovationbank.controller;

import inovationbank.inovationbank.dto.TransferenciaRequestDTO;
import inovationbank.inovationbank.exception.SaldoInsuficienteException;
import inovationbank.inovationbank.model.ContaBancaria;
import inovationbank.inovationbank.model.OperacaoModel;
import inovationbank.inovationbank.service.ContaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contas-bancarias")
public class ContaBancariaController {

    @Autowired
    private ContaBancariaService contaBancariaService;

    @GetMapping("/{id}/saldo")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable Long id) {
        try {
            BigDecimal saldo = contaBancariaService.consultarSaldo(id);
            return ResponseEntity.ok(saldo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<List<OperacaoModel>> extrato(@PathVariable Long id) {
        try {
            List<OperacaoModel> operacoes = contaBancariaService.listaOperacoes(id);
            return ResponseEntity.ok(operacoes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/transferencias")
    epublic ResponseEntity<String> transferir(@RequestBody TransferenciaRequestDTO dto) {
        try {
            contaBancariaService.transferir(dto.getIdContaOrigem(), dto.getIdContaDestino(), dto.getValor());
            return ResponseEntity.ok("Transferência realizada");
        } catch (SaldoInsuficienteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/deposito")
    public ResponseEntity<ContaBancaria> depositar(@PathVariable Long id, @RequestBody double valor) {
        try {
            ContaBancaria contaAtualizada = contaBancariaService.depositar(id, valor);
            return ResponseEntity.ok(contaAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/saque")
    public ResponseEntity<ContaBancaria> sacar(@PathVariable Long id, @RequestBody double valor) {
        try {
            ContaBancaria contaAtualizada = contaBancariaService.sacar(id, valor);
            return ResponseEntity.ok(contaAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<ContaBancaria> listaDeTodas() {
        return contaBancariaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaBancaria> buscarPorId(@PathVariable Long id) {
        Optional<ContaBancaria> conta = contaBancariaService.buscarPorId(id);
        return conta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContaBancaria> cadastrar(@RequestBody ContaBancaria contaBancaria) {
        return ResponseEntity.ok(contaBancariaService.cadastrar(contaBancaria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaBancaria> atualizar(@PathVariable Long id, @RequestBody ContaBancaria atualizarConta) {
        try {
            return ResponseEntity.ok(contaBancariaService.atualizar(id, atualizarConta));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contaBancariaService.deletar(id);
        return ResponseEntity.noContent().build();

    }

}
