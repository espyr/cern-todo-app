package ch.cern.todo.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import ch.cern.todo.model.TodoCategory;
import ch.cern.todo.repositories.TodoCategoryRepository;

@CrossOrigin(origins = "http://localhost:8080")

//@RestController itself consists @Controller and @ResponseBody
@RestController
// This annotation is used to provide the routing information 
// and tells to Spring that any HTTP request must be mapped to the respective method.
@RequestMapping("/api")
public class TodoCategoryController {
	@Autowired
	TodoCategoryRepository todoCategoryRepository;
	@GetMapping("/categories")
	public ResponseEntity<List<TodoCategory>> getAllTodoCategories(@RequestParam(required = false) String name) {
		try {
			List<TodoCategory> todosCategories = new ArrayList<TodoCategory>();
			if (name == null)
            todoCategoryRepository.findAll().forEach(todosCategories::add);
			else
            todoCategoryRepository.findByNameContaining(name).forEach(todosCategories::add);
			if (todosCategories.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(todosCategories, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/categories/{id}")
	public ResponseEntity<TodoCategory> getTodoCategoryById(@PathVariable("id") long id) {
		Optional<TodoCategory> todoCategoryData = todoCategoryRepository.findById(id);
		if (todoCategoryData.isPresent()) {
			return new ResponseEntity<>(todoCategoryData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/categories")
	public ResponseEntity<TodoCategory> createTodoCategory(@RequestBody TodoCategory todoCategory) {
		try {
			TodoCategory newTodoCategory = todoCategoryRepository
					.save(new TodoCategory(todoCategory.getName(), todoCategory.getDescription()));
			return new ResponseEntity<>(newTodoCategory, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/categories/{id}")
	public ResponseEntity<TodoCategory> updateTodoCategory(@PathVariable("id") long id, @RequestBody TodoCategory todoCategory) {
		Optional<TodoCategory> todoCategoryData = todoCategoryRepository.findById(id);
		if (todoCategoryData.isPresent()) {
			TodoCategory newTodoCategory = todoCategoryData.get();
			newTodoCategory.setName(todoCategory.getName());
			newTodoCategory.setDescription(todoCategory.getDescription());
			return new ResponseEntity<>(todoCategoryRepository.save(newTodoCategory), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/categories/{id}")
	public ResponseEntity<HttpStatus> deleteTodosCategory(@PathVariable("id") long id) {
		try {
			todoCategoryRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/categories")
	public ResponseEntity<HttpStatus> deleteAllTodosCategories() {
		try {
			todoCategoryRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
