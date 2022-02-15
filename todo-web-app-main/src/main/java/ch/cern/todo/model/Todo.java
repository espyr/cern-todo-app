package ch.cern.todo.model;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "todos")
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "deadline")
    private Date deadline;
    @ManyToOne(targetEntity = TodoCategory.class)
    @JoinColumn(name="category_id")
    private TodoCategory category;

    public Todo() {
	}

    public Todo(String name, String description, Date deadline, TodoCategory category){
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.category = category;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getDeadline() {
        return this.deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public TodoCategory getCategory() {
        return this.category;
    }

    public void setCategory(TodoCategory category) {
        this.category = category;
    }

    public long getCategoryId() {
        return this.category.getId();
    }
}