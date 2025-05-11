package com.estudos.todolistservice.controller;

import com.estudos.todolistservice.dto.TarefaResponseDTO;
import com.estudos.todolistservice.entity.Tarefa;
import com.estudos.todolistservice.exception.BusinessException;
import com.estudos.todolistservice.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tarefas")
public class TarefaController {
    @Autowired
    private TarefaService service;

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> create(@Valid @RequestBody Tarefa tarefa) {
        TarefaResponseDTO response = service.create(tarefa);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    List<Tarefa> list() {
        return service.list();
    }

    @PutMapping
    ResponseEntity<TarefaResponseDTO> update(@Valid @RequestBody Tarefa tarefa) {
        TarefaResponseDTO response = service.update(tarefa);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    List<Tarefa> delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }

}
