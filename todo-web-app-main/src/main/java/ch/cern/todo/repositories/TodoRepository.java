package ch.cern.todo.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ch.cern.todo.model.Todo;
import ch.cern.todo.model.TodoCategory;

@Repository 
public interface TodoRepository extends JpaRepository<Todo, Long> {
  List<Todo> findByCategory(TodoCategory todoCategory);
  List<Todo> findByNameContaining(String name);
}