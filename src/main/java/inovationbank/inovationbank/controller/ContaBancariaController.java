    package inovationbank.inovationbank.controller;

import inovationbank.inovationbank.model.ContaBancaria;
import inovationbank.inovationbank.service.ContaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contas-bancarias")
public class ContaBancariaController {

    @Autowired
    private ContaBancariaService contaBancariaService;

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
