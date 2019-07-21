package com.alevel.spring.todolist.controller;

import com.alevel.spring.todolist.persistence.Todo;
import com.alevel.spring.todolist.service.TodoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoOperations todoOperation; //Инъекция зависимости

    public TodoController(TodoOperations todoOperation) {
        this.todoOperation = todoOperation;
    }

    @GetMapping
    public List<Todo> findAll(@RequestParam(value = "is_done", required = false) Boolean done) {
        List<Todo> items;
        if (done != null) {
            items = todoOperation.findAll(done);
        } else {
            items = todoOperation.findAll();
        }
        return items;
    }

    @GetMapping("/{id}")
    public Todo find(@PathVariable Long id) {
        return todoOperation.find(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo save(@RequestBody Todo todo) {
        Long id = todoOperation.save(todo);
        todo.setId(id);
        return todo;
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Todo todo) {

        todoOperation.update(id, todo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        todoOperation.delete(id);
    }
}
