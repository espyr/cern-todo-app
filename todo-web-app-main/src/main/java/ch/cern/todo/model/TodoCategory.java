package ch.cern.todo.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "todoCategory")
public class TodoCategory {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long categoryId;
	@Column(name = "name", unique = true)
    private String name;
	@Column(name = "description")
	private String description;
    @OneToMany(mappedBy="category",cascade=CascadeType.ALL)
    @JsonIgnore    
    private List<Todo> todo;

    public TodoCategory() {
    }

    public TodoCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return this.categoryId;
    }

    public void setId(long id) {
        this.categoryId = id;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<Todo> getTodo() {
        return this.todo;
    }

    public void setTodo(List<Todo> todo) {
        this.todo = todo;
    }

}
