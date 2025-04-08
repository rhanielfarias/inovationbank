package inovationbank.inovationbank.repository;

import inovationbank.inovationbank.model.OperacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperacaoRepository extends JpaRepository<OperacaoModel, Long> {

    List<OperacaoModel> findByContaIdOrderByDataHoraDesc(Long contaId);
}
