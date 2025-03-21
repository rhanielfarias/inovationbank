package inovationbank.inovationbank.service;

import inovationbank.inovationbank.repository.ContaBancariaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoBeans;

import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaServiceTest {
    @Mock
    private ContaBancariaRepository contaBancariaRepository;

    @InjectMocks
    private  ContaBancariaService contaBancariaService;

    @BeforeEach
    void  iniciar() {
Mock}

}