package com.alevel.spring.todolist.service;

import com.alevel.spring.todolist.controller.TodoNotFoundException;
import com.alevel.spring.todolist.persistence.Todo;
import com.alevel.spring.todolist.persistence.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService implements TodoOperations {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Optional<Todo> find(Long id) {
        return todoRepository.findById(id);
    }

    @Override
    public List<Todo> findAll(boolean done) {
        return todoRepository.findAllByStatus(done);
    }

    @Override
    public void update(Long id, Todo todo) {
//        if (!find(id).isPresent()) {
//            throw new TodoNotFoundException(id);
//        }
        Todo old = find(id).orElseThrow(() -> new TodoNotFoundException(id));
        String text = todo.getText();
        if (text != null) {
            old.setText(text);
        }
        boolean status = todo.getStatus();
        if (old.getStatus() != status) {
            old.setStatus(status);
        }
        todoRepository.save(old);
    }

    @Override
    public Long save(Todo todo) {
        return todoRepository.save(todo).getId();
    }

    @Override
    public void delete(Long id) {
        todoRepository.deleteById(id);
    }
}
