package inovationbank.inovationbank.repository;

import inovationbank.inovationbank.model.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {
    Optional<ContaBancaria> findByClienteId(Long clienteId);
}
