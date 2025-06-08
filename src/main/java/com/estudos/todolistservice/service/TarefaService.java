package com.estudos.todolistservice.service;

import com.estudos.todolistservice.dto.TarefaDTO;
import com.estudos.todolistservice.entity.Tarefa;
import com.estudos.todolistservice.enums.PrioridadeEnum;
import com.estudos.todolistservice.enums.StatusEnum;
import com.estudos.todolistservice.exception.BusinessException;
import com.estudos.todolistservice.mapper.TarefaMapper;
import com.estudos.todolistservice.repository.TarefaRepository;
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
    public TarefaDTO create(TarefaDTO dados) {
        Tarefa tarefa = TarefaMapper.toEntity(dados);
        tarefa = repository.save(tarefa);
        return TarefaMapper.toDTO(tarefa);
    }

    public List<TarefaDTO> list() {
        Sort sort = Sort.by("prioridade").descending().and(Sort.by("nome").ascending());
        return repository.findAll(sort)
                .stream()
                .map(TarefaMapper::toDTO)
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

        tarefa = repository.save(tarefa);
        return TarefaMapper.toDTO(tarefa);
    }

    @Transactional
    public void delete(Long id) {
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Tarefa não encontrada"));

        repository.delete(tarefa);
    }
}
