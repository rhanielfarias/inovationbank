package inovationbank.inovationbank.repository;

import inovationbank.inovationbank.model.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {

}
