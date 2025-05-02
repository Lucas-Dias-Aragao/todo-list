package com.estudos.todolistservice.controller;

import com.estudos.todolistservice.entity.Tarefa;
import com.estudos.todolistservice.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {
    @Autowired
    private TarefaService service;

    @PostMapping
    List<Tarefa> create(@RequestBody Tarefa tarefa) {
        return service.create(tarefa);
    }

    @GetMapping
    List<Tarefa> list() {
        return service.list();
    }

    @PutMapping
    List<Tarefa> update(@RequestBody Tarefa tarefa) {
        return service.update(tarefa);
    }

    @DeleteMapping("/{id}")
    List<Tarefa> delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }

}
