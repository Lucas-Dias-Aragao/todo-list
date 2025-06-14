package com.estudos.todolistservice.controller;

import com.estudos.todolistservice.dto.TarefaDTO;
import com.estudos.todolistservice.entity.Tarefa;
import com.estudos.todolistservice.enums.PrioridadeEnum;
import com.estudos.todolistservice.enums.StatusEnum;
import com.estudos.todolistservice.mapper.TarefaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TarefaControllerTest extends BaseControllerTest {

    public static final String TAREFA_NAO_ENCONTRADA = "Tarefa não encontrada";
    private static final String TAREFA_SEM_NOME = "O nome da tarefa deve ser informado";
    private static final String STATUS_INVALIDO = "Status inválido";
    private static final String PRIORIDADE_INVALIDA = "Prioridade inválida";

    @Nested
    @DisplayName("POST /tarefas")
    class Post {

        @Nested
        @DisplayName("Deve retornar status 200")
        class Success {

            @Test
            @DisplayName("Deve criar tarefa com todas as informações com sucesso")
            void deveCriarTarefaComSucesso() {
                var dto = createTarefaDTO();

                HttpEntity<TarefaDTO> request = new HttpEntity<>(dto);

                ResponseEntity<TarefaDTO> response = restTemplate.exchange(
                        BASE_URL,
                        HttpMethod.POST,
                        request,
                        TarefaDTO.class
                );

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(dto.getNome(), response.getBody().getNome());
            }

            @Test
            @DisplayName("Deve criar tarefa sem descrição com sucesso")
            void deveCriarTarefaSemDescricaoComSucesso() {
                var dto = new TarefaDTO("Teste",
                        null, StatusEnum.EM_ANDAMENTO, PrioridadeEnum.ALTA);

                HttpEntity<TarefaDTO> request = new HttpEntity<>(dto);

                ResponseEntity<TarefaDTO> response = restTemplate.exchange(
                        BASE_URL,
                        HttpMethod.POST,
                        request,
                        TarefaDTO.class
                );

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(dto.getNome(), response.getBody().getNome());
                assertEquals(null, response.getBody().getDescricao());
            }
        }

        @Nested
        @DisplayName("Deve retornar status 400")
        class Fail {

            @Test
            @DisplayName("Não deve criar tarefa sem nome")
            void naoDeveCriarTarefaSemNome() {
                var dto = createTarefaDTO(null, "nao deve criar tarefa", StatusEnum.CONCLUIDO, PrioridadeEnum.ALTA);

                HttpEntity<TarefaDTO> request = new HttpEntity<>(dto);

                ResponseEntity<TarefaDTO> response = restTemplate.exchange(
                        BASE_URL,
                        HttpMethod.POST,
                        request,
                        TarefaDTO.class
                );

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertEquals(TAREFA_SEM_NOME, response.getBody().getNome());
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
                assertEquals(STATUS_INVALIDO, response.getBody().getStatus());
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
                assertEquals(PRIORIDADE_INVALIDA, response.getBody().getPrioridade());
            }
        }
    }

    @Nested
    @DisplayName("GET /tarefas")
    class Get {

        @Nested
        @DisplayName("Deve retornar status 200")
        class Success {
            @Test
            @DisplayName("Deve retornar uma lista de tarefas")
            void deveRetornarListaDeTarefas() {

                var tarefa = createTarefaDTO("tarefa 1", "descricao tarefa 1");
                var tarefa2 = createTarefaDTO("tarefa 2", "descricao tarefa 2");
                tarefaRepository.save(TarefaMapper.toEntity(tarefa));
                tarefaRepository.save(TarefaMapper.toEntity(tarefa2));

                List<TarefaDTO> tarefas = new ArrayList<>();
                tarefas.add(tarefa);
                tarefas.add(tarefa2);

                ResponseEntity<List<TarefaDTO>> response = restTemplate.exchange(
                        BASE_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<TarefaDTO>>() {
                        }
                );

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(tarefas.size(), response.getBody().size());
                assertEquals(tarefa.getNome(), response.getBody().get(0).getNome());
                assertEquals(tarefa2.getNome(), response.getBody().get(1).getNome());
            }
        }


    }

    @Nested
    @DisplayName("PUT /tarefas")
    class Put {
        @Nested
        @DisplayName("Deve retornar status 200")
        class Success {

            @Test
            @DisplayName("Deve atualizar tarefa com sucesso")
            void deveAtualizarTarefaComSucesso() {

                var tarefa = createTarefa();

                TarefaDTO tarefaComId = new TarefaDTO(tarefa);

                tarefaComId.setNome("Nome atualizado");
                var nomeAtualizado = tarefaComId.getNome();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<TarefaDTO> request = new HttpEntity<>(tarefaComId, headers);

                String url = BASE_URL + "/" + tarefa.getId();

                ResponseEntity<TarefaDTO> response = restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        request,
                        TarefaDTO.class
                );

                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(nomeAtualizado, response.getBody().getNome());
            }
        }

        @Nested
        @DisplayName("Deve retornar status 400")
        class Fail {

            @Test
            @DisplayName("Não deve atualizar tarefa com Status e Prioridade inválidos")
            void naoDeveAtualizarTarefaComStatusEPrioridadeInvalidos() {

                var tarefa = createTarefa();

                TarefaDTO dto = new TarefaDTO(tarefa);
                dto.setStatus("Inexistente");
                dto.setPrioridade("Inexistente");

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<TarefaDTO> request = new HttpEntity<>(dto, headers);

                String url = BASE_URL + "/" + tarefa.getId();

                ResponseEntity<TarefaDTO> response = restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        request,
                        TarefaDTO.class
                );

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

                assertTrue(response.getBody().getStatus().equals(STATUS_INVALIDO));
                assertTrue(response.getBody().getPrioridade().equals(PRIORIDADE_INVALIDA));
            }

            @Test
            @DisplayName("Deve retornar erro ao atualizar tarefa inexistente")
            void deveRetornarErroAoTentarAtualizarTarefaInexistente() {

                TarefaDTO dto = createTarefaDTO();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<?> request = new HttpEntity<>(dto);

                String url = BASE_URL + "/" + 99999L;

                ResponseEntity<Map> response = restTemplate.exchange(
                        url,
                        HttpMethod.PUT,
                        request,
                        Map.class
                );

                assertNotNull(response);
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertEquals(TAREFA_NAO_ENCONTRADA, response.getBody().get("mensagem"));
            }
        }

    }

    @Nested
    @DisplayName("DELETE /tarefas")
    class Delete {
        @Nested
        @DisplayName("Deve retornar status 200")
        class Success {

            @Test
            @DisplayName("Deve deletar tarefa com sucesso")
            void deveDeletarTarefaComSucesso() {

                var tarefa = createTarefa();

                String url = BASE_URL + "/" + tarefa.getId();

                ResponseEntity<Void> response = restTemplate.exchange(
                        url,
                        HttpMethod.DELETE,
                        null,
                        Void.class
                );

                assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

                Optional<Tarefa> deletada = tarefaRepository.findById(tarefa.getId());
                assertTrue(deletada.isEmpty());
            }
        }

        @Nested
        @DisplayName("Deve retornar status 400")
        class Fail {

            @Test
            @DisplayName("Deve retornar erro ao atualizar tarefa inexistente")
            void deveRetornarErroAoTentarDeletarTarefaInexistente() {

                String url = BASE_URL + "/" + 99999;

                ResponseEntity<Map> response = restTemplate.exchange(
                        url,
                        HttpMethod.DELETE,
                        null,
                        Map.class
                );

                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertEquals(TAREFA_NAO_ENCONTRADA, response.getBody().get("mensagem"));
            }
        }

    }


    @BeforeEach
    void limparBancoAntes() {
        tarefaRepository.deleteAll();
    }
}
