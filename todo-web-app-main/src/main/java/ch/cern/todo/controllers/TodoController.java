package ch.cern.todo.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.cern.todo.model.Todo;
import ch.cern.todo.model.TodoCategory;
import ch.cern.todo.repositories.TodoCategoryRepository;
import ch.cern.todo.repositories.TodoRepository;

@CrossOrigin(origins = "http://localhost:8080")

//@RestController itself consists @Controller and @ResponseBody
@RestController
// This annotation is used to provide the routing information 
// and tells to Spring that any HTTP request must be mapped to the respective method.
@RequestMapping("/api")
public class TodoController {
	@Autowired
	TodoRepository todoRepository;
	@Autowired
	TodoCategoryRepository todoCategoryRepository;
	@GetMapping("/todos")
	@JsonIgnore
	public ResponseEntity<List<Todo>> getAllTodo(@RequestParam(required = false) String name) {
		try {
			List<Todo> todos = new ArrayList<Todo>();
			if (name == null)
            todoRepository.findAll().forEach(todos::add);
			else
            todoRepository.findByNameContaining(name).forEach(todos::add);
			if (todos.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(todos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/todos/{id}")
	public ResponseEntity<Todo> getTodoById(@PathVariable("id") long id) {
		Optional<Todo> todoData = todoRepository.findById(id);
		if (todoData.isPresent()) {
			return new ResponseEntity<>(todoData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/todos/{categoryId}")
	public ResponseEntity<Todo> createTodo(@PathVariable("categoryId") long categoryId, @RequestBody Todo todoRequest) {
		try {
			TodoCategory todoCategory = todoCategoryRepository.findByCategoryId(categoryId);		
			Todo newTodo = todoRepository
					.save(new Todo(todoRequest.getName(), todoRequest.getDescription(), todoRequest.getDeadline(), 
                    todoCategory));

			return new ResponseEntity<>(newTodo, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable("id") long id, @RequestBody Todo todo) {
		Optional<Todo> todoData = todoRepository.findById(id);
		if (todoData.isPresent()) {
			Todo newTodo = todoData.get();
			newTodo.setName(todo.getName());
			newTodo.setDescription(todo.getDescription());
			newTodo.setDeadline(todo.getDeadline());
			newTodo.setCategory(todo.getCategory());
			return new ResponseEntity<>(todoRepository.save(newTodo), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<HttpStatus> deleteTodo(@PathVariable("id") long id) {
		try {
			todoRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/todos")
	public ResponseEntity<HttpStatus> deleteAllTodos() {
		try {
			todoRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/todos/categoryId/{id}")
	 public ResponseEntity<List<Todo>> findByCategoryId(@PathVariable("id") long categoryId) {
		 try {
             List<Todo> todos = new ArrayList<Todo>();
			 List<TodoCategory> todoCategories = todoCategoryRepository.getAllByCategoryId(categoryId);		
			for(TodoCategory category : todoCategories){
				todoRepository.findByCategory(category).forEach(todos::add);				
			}
			if (todos.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(todos, HttpStatus.OK);
			 
		 } catch (Exception e) {
			 return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	}

}
