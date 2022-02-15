package ch.cern.todo.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.cern.todo.model.TodoCategory;

@Repository 
public interface TodoCategoryRepository extends JpaRepository<TodoCategory, Long> {
  List<TodoCategory> findByNameContaining(String name);
  List<TodoCategory> getAllByCategoryId(long categoryId);
  TodoCategory findByCategoryId(long categoryId);
}