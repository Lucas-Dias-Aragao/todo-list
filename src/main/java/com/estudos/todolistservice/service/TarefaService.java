package com.estudos.todolistservice.service;

import com.estudos.todolistservice.entity.Tarefa;
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
    public List<Tarefa> create(Tarefa tarefa) {
        repository.save(tarefa);
        return list();
    }

    public List<Tarefa> list() {
        Sort sort = Sort.by("prioridade").descending().and(Sort.by("nome").ascending());
        return repository.findAll(sort);
    }

    public List<Tarefa> update(Tarefa tarefa) {
        repository.save(tarefa);
        return list();
    }

    public List<Tarefa> delete(Long id) {
        repository.deleteById(id);
        return list();
    }
}
