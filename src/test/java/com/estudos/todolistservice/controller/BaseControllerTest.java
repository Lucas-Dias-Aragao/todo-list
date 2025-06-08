package com.estudos.todolistservice.controller;

import com.estudos.todolistservice.dto.TarefaDTO;
import com.estudos.todolistservice.entity.Tarefa;
import com.estudos.todolistservice.enums.PrioridadeEnum;
import com.estudos.todolistservice.enums.StatusEnum;
import com.estudos.todolistservice.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseControllerTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected TarefaRepository tarefaRepository;

    public final String BASE_URL = "/tarefas";

    public Tarefa createTarefa() {
        Tarefa tarefa = Tarefa.builder()
                .nome("Testar")
                .descricao("Realizar testes")
                .status(StatusEnum.EM_ANDAMENTO)
                .prioridade(PrioridadeEnum.ALTA)
                .build();
        tarefa = tarefaRepository.save(tarefa);

        assertNotNull(tarefa);

        return tarefa;
    }

    public Tarefa createTarefaInvalida() {
        Tarefa tarefa = Tarefa.builder()
                .descricao("Realizar testes")
                .status(StatusEnum.EM_ANDAMENTO)
                .prioridade(PrioridadeEnum.ALTA)
                .build();
        tarefa = tarefaRepository.save(tarefa);

        assertNotNull(tarefa);

        return tarefa;
    }
}
