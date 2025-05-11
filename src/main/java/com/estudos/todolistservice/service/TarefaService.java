package com.estudos.todolistservice.service;

import com.estudos.todolistservice.dto.TarefaResponseDTO;
import com.estudos.todolistservice.entity.Tarefa;
import com.estudos.todolistservice.exception.BusinessException;
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
    public TarefaResponseDTO create(Tarefa tarefa) throws BusinessException {
            Tarefa salva = repository.save(tarefa);
            return TarefaResponseDTO.success(salva);
    }

    public List<Tarefa> list() {
        Sort sort = Sort.by("prioridade").descending().and(Sort.by("nome").ascending());
        return repository.findAll(sort);
    }

    public TarefaResponseDTO update(Tarefa tarefa) {
        var tarefaAtualizada = repository.save(tarefa);
        return TarefaResponseDTO.success(tarefaAtualizada);
    }

    public List<Tarefa> delete(Long id) {
        repository.deleteById(id);
        return list();
    }
}
