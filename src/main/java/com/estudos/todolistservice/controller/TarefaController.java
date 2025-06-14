package com.estudos.todolistservice.controller;

import com.estudos.todolistservice.dto.TarefaDTO;
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
    public ResponseEntity<TarefaDTO> create(@Valid @RequestBody TarefaDTO dados) {
        TarefaDTO tarefa = service.create(dados);
        return ResponseEntity.ok().body(tarefa);
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> list() {
        List<TarefaDTO> tarefas = service.list();
        return ResponseEntity.ok(tarefas);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TarefaDTO> update(@PathVariable Long id, @Valid @RequestBody TarefaDTO dados) {
        TarefaDTO dto = service.update(id, dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> findTarefaById(@PathVariable Long id) {
        TarefaDTO tarefa = service.findById(id);
        return ResponseEntity.ok(tarefa);
    }

}
