package se.lexicon.dao;

import se.lexicon.dao.dataBase.DbConnection;
import se.lexicon.exeptions.DBConnectionException;
import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.sql.*;
import java.util.Collection;

public class TodoItemsDaoImp implements TodoItemsDao {
    public Todo create(Todo todo) {
        String query = "INSERT INTO todo_item (todo_id, title, description, deadline, done, assignee_id) VALUES (?,?,?,?,?,?)";
        Todo addedItem = null;

        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setInt(1, todo.getTodoId());
            preparedStatement.setString(2, todo.getTitle());
            preparedStatement.setString(3, todo.getDescription());
            preparedStatement.setString(4, String.valueOf(todo.getDeadLine()));
            preparedStatement.setBoolean(5, todo.isDone());
            preparedStatement.setInt(6, todo.getAssigneeId());

            int result = preparedStatement.executeUpdate();
            System.out.println((result == 1) ? "Added successfully" : "Not added!");
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {

                if (resultSet.next()) {
                    addedItem = new Todo(resultSet.getInt(1),
                            todo.getTitle(), todo.getDescription(), todo.getDeadLine(), todo.isDone(), todo.getAssigneeId());
                }
                todo = addedItem;
            }

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return todo;
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
