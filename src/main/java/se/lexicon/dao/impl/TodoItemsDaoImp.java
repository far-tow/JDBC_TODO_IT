package se.lexicon.dao.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.lexicon.dao.TodoItemsDao;
import se.lexicon.dao.dataBase.DbConnection;
import se.lexicon.exeptions.DBConnectionException;
import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.sql.*;
import java.util.ArrayList;
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

            //Check if person already exist first!
            try (
                    PreparedStatement ps = connection.prepareStatement("select count(*) as count from todo_item where todo_id= ?");
            ) {
                ps.setInt(1, todo.getTodoId());

                ResultSet resultSet1 = ps.executeQuery();

                int isExist;
                if (resultSet1.next()) {
                    isExist = resultSet1.getInt("count");
                    if (isExist > 0)
                        throw new RuntimeException(todo.getTitle() + " " + " ,with item id " + todo.getTodoId() + " ,already exists");
                }
            }
            //if not exist then add it.

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
        String query = "SELECT * FROM todo_item";
        Collection<Todo> items = new ArrayList<>();

        try {
            Connection connection = DbConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                items.add(new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6)
                ));
            }
        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return items;
    }

    public Todo findById(int todoId) {
        Todo todo = null;
        String query = "SELECT * FROM todo_item WHERE todo_id = ?";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, todoId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                todo = new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6));
            }

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return todo;
    }

    public Collection<Todo> findByDoneStatus(boolean done) {
        String query = "SELECT * FROM todo_item WHERE done = ?";
        Collection<Todo> items = new ArrayList<>();

        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setBoolean(1, done);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                items.add(new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6)
                ));
            }

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        return items;
    }

    public Collection<Todo> findByAssignee(int assignee) {
        Collection<Todo> items = new ArrayList<>();
        String query = "SELECT todo_id, title, description, deadline, done, assignee_id FROM todo_item INNER JOIN person ON  assignee_id  = person_id WHERE assignee_id =?";
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, assignee);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                items.add(new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6)
                ));
            }

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return items;
    }

    public Collection<Todo> findByAssignee(Person person) {
        String query = "SELECT * FROM todo_item WHERE assignee_id = ?";
        Collection<Todo> items = new ArrayList<>();
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, person.getPersonId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                items.add(new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6)
                ));
            }

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }


        return items;
    }

    public Collection<Todo> findByUnassignedTodoItems() {
        String query = "select * from todo_item where assignee_id is null";
        Collection<Todo> items = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                items.add(new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6)
                ));
            }
        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return items;
    }

    public Todo update(Todo todo) {
        String query = "update todo_item set title = ?, description = ?, deadline = ?, " +
                "done = ?, assignee_id = ? where todo_id = ?";
        int rowsAffected = 0;

        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, todo.getTitle());
            preparedStatement.setString(2, todo.getDescription());
            preparedStatement.setDate(3, Date.valueOf(todo.getDeadLine()));
            preparedStatement.setBoolean(4, todo.isDone());
            preparedStatement.setInt(5, todo.getAssigneeId());
            preparedStatement.setInt(6, todo.getTodoId());


            //Check if person already exist first!
           try (
                    PreparedStatement ps = connection.prepareStatement("select count(*) as count from todo_item where todo_id= ?");
            ) {
                ps.setInt(1, todo.getTodoId());

                ResultSet resultSet1 = ps.executeQuery();

                int isExist;
                if (resultSet1.next()) {
                    isExist = resultSet1.getInt("count");
                    if (isExist == 0) throw new RuntimeException("Item " + todo.getTodoId() + " ,does Not exists");
                }
            }
            //if not exist then add it.
            rowsAffected = preparedStatement.executeUpdate();

            System.out.println((rowsAffected == 1) ?  "\n" + todo + " Updated successfully!" : todo + " did not updated!");
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {

                if (rowsAffected >= 1) {
                    System.out.println(rowsAffected + " post updated!");
                }
            }

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return todo;
    }

    public boolean deleteById(int todo_id) {
        String query = "DELETE FROM todo_item WHERE todo_id = ?";
        int rowsAffected = 0;
        boolean del = false;

        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setInt(1, todo_id);
            rowsAffected = preparedStatement.executeUpdate();

            System.out.println((rowsAffected == 1) ? "Todo id: " + todo_id + " Deleted successfully" :  "Todo id: " + todo_id + " does not exist!");
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {

                if (rowsAffected >= 1) {
                    del = true;
                    System.out.println(rowsAffected + " post deleted!");
                }
            }

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return del;
    }
}
