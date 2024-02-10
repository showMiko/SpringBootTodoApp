package com.example.in28minutes.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class TodoService {
	private static List<Todo> todos = new ArrayList<Todo>();
	private static int todosCount = 0;
	static {
		todos.add(new Todo(++todosCount, "soumik", "Learn Spring 1", LocalDate.now().plusYears(1), false));
		todos.add(new Todo(++todosCount, "soumik", "Learn DevOps 1", LocalDate.now().plusYears(2), false));
		todos.add(new Todo(++todosCount, "soumik", "Learn FullStack 1", LocalDate.now().plusYears(3), false));
	}

	public List<Todo> findByUsername(String username) {
		Predicate<? super Todo> predicate = todo -> todo.getUsername().equalsIgnoreCase(username);
		return todos.stream().filter(predicate).toList();
	}

	public void addTodo(String name, String description, LocalDate date, boolean done) {
		todos.add(new Todo(++todosCount, name, description, date, done));
	}

	public void deleteById(int id) {
		Predicate<? super Todo> predicate = todo -> todo.getId() == id;
		todos.removeIf(predicate);
	}

	public Todo findById(int id) {
		Predicate<? super Todo> predicate = todo -> todo.getId() == id;
		return todos.stream().filter(predicate).findFirst().get();
	}

	public void updateTodo(Todo todo) {
		// TODO Auto-generated method stub
		deleteById(todo.getId());
		todos.add(todo);
	}
}
