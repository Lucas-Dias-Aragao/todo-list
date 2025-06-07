package com.estudos.todolistservice.service;

import com.estudos.todolistservice.dto.TarefaDTO;
import com.estudos.todolistservice.entity.Tarefa;
import com.estudos.todolistservice.enums.PrioridadeEnum;
import com.estudos.todolistservice.enums.StatusEnum;
import com.estudos.todolistservice.exception.BusinessException;
import com.estudos.todolistservice.repository.TarefaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository repository;

    @Transactional
    public TarefaDTO create(@Valid TarefaDTO dados) {
        Tarefa tarefa = new Tarefa(dados);
        repository.save(tarefa);
        return new TarefaDTO(tarefa);
    }

    public List<TarefaDTO> list() {
        Sort sort = Sort.by("prioridade").descending().and(Sort.by("nome").ascending());
        return repository.findAll(sort)
                .stream()
                .map(TarefaDTO::new)
                .toList();
    }


    @Transactional
    public TarefaDTO update(Long id, TarefaDTO dados) {
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Tarefa não encontrada"));

        tarefa.setNome(dados.nome());
        tarefa.setDescricao(dados.descricao());
        tarefa.setStatus(StatusEnum.valueOf(dados.status()));
        tarefa.setPrioridade(PrioridadeEnum.valueOf(dados.prioridade()));

        repository.save(tarefa);
        return new TarefaDTO(tarefa);
    }

    @Transactional
    public void delete(Long id) {
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Tarefa não encontrada"));

        repository.delete(tarefa);
    }
}
