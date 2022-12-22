package se.lexicon.dao;

import se.lexicon.dao.PeopleDao;
import se.lexicon.dao.dataBase.DbConnection;
import se.lexicon.exeptions.DBConnectionException;
import se.lexicon.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PeopleDaoImp implements PeopleDao {


    public Person create(Person person) {
        String query = "INSERT INTO person (person_id, first_name, last_name) VALUES (?,?,?)";
        Person addedPerson = null; //creating new person with personId

        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setInt(1, person.getPersonId());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getLastName());
            int result = preparedStatement.executeUpdate();
            System.out.println((result == 1) ? "Added successfully" : "Not added!");
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {

                if (resultSet.next()) {
                    addedPerson = new Person(resultSet.getInt(1), person.getFirstName(), person.getLastName());
                }
                person = addedPerson;
            }

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return person;
    }

    public Collection<Person> findAll() {
        String query = "SELECT * FROM person";
        Collection<Person> persons = new ArrayList<>();

        try {
            Connection connection = DbConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                persons.add(new Person(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            }
        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return persons;
    }

    public Person findById(int personId) {
        String query = "SELECT * FROM person WHERE person_id = ?";
        Person person = null;
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                person = new Person(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return person;
    }

    public Collection<Person> findByName(String name) {
        String query = "SELECT * FROM person WHERE first_name = ?";
        Collection<Person> persons = new ArrayList<>();
        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                persons.add(new Person(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            }

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return persons;
    }

    public Person update(Person person) {
        return null;
    }

    public boolean deleteById(int personId) {
        return false;
    }
}
