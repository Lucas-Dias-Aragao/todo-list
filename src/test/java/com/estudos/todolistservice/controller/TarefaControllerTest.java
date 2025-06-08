package com.estudos.todolistservice.controller;

import com.estudos.todolistservice.dto.TarefaDTO;
import com.estudos.todolistservice.entity.Tarefa;
import com.estudos.todolistservice.enums.PrioridadeEnum;
import com.estudos.todolistservice.enums.StatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TarefaControllerTest extends BaseControllerTest {

    private final String TAREFA_SEM_NOME = "O nome da tarefa deve ser informado";
    private final String STATUS_INVALIDO = "Status inválido";
    private final String PRIORIDADE_INVALIDA = "Prioridade inválida";

    @Nested
    @DisplayName("/tarefas")
    class Post {
        @Nested
        @DisplayName("Deve retornar status 200")
        class Success {
            @Test
            @DisplayName("Deve criar tarefa com todas as informações com sucesso")
            void deveCriarTarefaComSucesso() {
                Tarefa tarefa = createTarefa();

                TarefaDTO dto = new TarefaDTO(tarefa);

                HttpEntity<TarefaDTO> request = new HttpEntity<>(dto);

                ResponseEntity<TarefaDTO> response = restTemplate.exchange(
                        BASE_URL,
                        HttpMethod.POST,
                        request,
                        TarefaDTO.class
                );

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(dto.nome(), response.getBody().nome());
            }

            @Test
            @DisplayName("Deve criar tarefa sem descrição com sucesso")
            void deveCriarTarefaSemDescricaoComSucesso() {
                Tarefa tarefa = createTarefa();
                tarefa.setDescricao(null);

                TarefaDTO dto = new TarefaDTO(tarefa);

                HttpEntity<TarefaDTO> request = new HttpEntity<>(dto);

                ResponseEntity<TarefaDTO> response = restTemplate.exchange(
                        BASE_URL,
                        HttpMethod.POST,
                        request,
                        TarefaDTO.class
                );

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(dto.nome(), response.getBody().nome());
                assertEquals(null, response.getBody().descricao());
            }
        }

        @Nested
        @DisplayName("Deve retornar status 400")
        class Fail {

            @Test
            @DisplayName("Não deve criar tarefa sem nome")
            void naoDeveCriarTarefaSemNome() {
                Tarefa tarefa = createTarefaInvalida();

                TarefaDTO dto = new TarefaDTO(tarefa);

                HttpEntity<TarefaDTO> request = new HttpEntity<>(dto);

                ResponseEntity<TarefaDTO> response = restTemplate.exchange(
                        BASE_URL,
                        HttpMethod.POST,
                        request,
                        TarefaDTO.class
                );

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertEquals(TAREFA_SEM_NOME, response.getBody().nome());
            }

            @Test
            @DisplayName("Não deve criar tarefa com status inválido")
            void naoDeveCriarTarefaSemStatus() {
                Tarefa tarefa = createTarefa();

                TarefaDTO dto = new TarefaDTO(tarefa.getId(),
                        "Tarefa invalida", "", "", PrioridadeEnum.MEDIA.getLabel());

                HttpEntity<TarefaDTO> request = new HttpEntity<>(dto);

                ResponseEntity<TarefaDTO> response = restTemplate.exchange(
                        BASE_URL,
                        HttpMethod.POST,
                        request,
                        TarefaDTO.class
                );

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertEquals(STATUS_INVALIDO, response.getBody().status());
            }

            @Test
            @DisplayName("Não deve criar tarefa com prioridade inválida")
            void naoDeveCriarTarefaSemPrioridade() {
                Tarefa tarefa = createTarefa();

                TarefaDTO dto = new TarefaDTO(tarefa.getId(),
                        "Tarefa invalida", "", StatusEnum.PENDENTE.getLabel(), "");

                HttpEntity<TarefaDTO> request = new HttpEntity<>(dto);

                ResponseEntity<TarefaDTO> response = restTemplate.exchange(
                        BASE_URL,
                        HttpMethod.POST,
                        request,
                        TarefaDTO.class
                );

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertEquals(PRIORIDADE_INVALIDA, response.getBody().prioridade());
            }

        }
    }
}
