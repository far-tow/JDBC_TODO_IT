package se.lexicon.dao;

import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.util.Collection;

public class TodoItemsDaoImp implements TodoItemsDao{
    public Todo create(Todo todo) {

        return null;
    }

    public Collection<Todo> findAll() {
        return null;
    }

    public Todo findById(int todoId) {
        return null;
    }

    public Collection<Todo> findByDoneStatus(boolean done) {
        return null;
    }

    public Collection<Todo> findByAssignee(int assignee) {
        return null;
    }

    public Collection<Todo> findByAssignee(Person person) {
        return null;
    }

    public Collection<Todo> findByUnassignedTodoItems() {
        return null;
    }

    public Todo update(Todo todo) {
        return null;
    }

    public boolean deleteById(int id) {
        return false;
    }
}
