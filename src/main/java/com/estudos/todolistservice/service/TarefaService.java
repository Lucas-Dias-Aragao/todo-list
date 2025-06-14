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
    protected TarefaRepository tarefaRepository;

    @Transactional
    public TarefaDTO create(TarefaDTO dados) {
        Tarefa tarefa = TarefaMapper.toEntity(dados);
        tarefa = tarefaRepository.save(tarefa);
        return TarefaMapper.toDTO(tarefa);
    }

    public List<TarefaDTO> list() {
        Sort sort = Sort.by("prioridade").descending().and(Sort.by("nome").ascending());
        return tarefaRepository.findAll(sort)
                .stream()
                .map(TarefaMapper::toDTO)
                .toList();
    }

    @Transactional
    public TarefaDTO update(Long id, TarefaDTO dados) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tarefa não encontrada"));

        tarefa.setNome(dados.getNome());
        tarefa.setDescricao(dados.getDescricao());
        tarefa.setStatus(StatusEnum.valueOf(dados.getStatus()));
        tarefa.setPrioridade(PrioridadeEnum.valueOf(dados.getPrioridade()));

        tarefa = tarefaRepository.save(tarefa);
        return TarefaMapper.toDTO(tarefa);
    }

    @Transactional
    public void delete(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tarefa não encontrada"));

        tarefaRepository.delete(tarefa);
    }

    public TarefaDTO findById(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tarefa não encontrada"));

        return TarefaMapper.toDTO(tarefa);
    }
}
