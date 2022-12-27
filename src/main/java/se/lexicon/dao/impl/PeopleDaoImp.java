package se.lexicon.dao.impl;

import se.lexicon.dao.PeopleDao;
import se.lexicon.dao.dataBase.DbConnection;
import se.lexicon.exeptions.DBConnectionException;
import se.lexicon.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class PeopleDaoImp implements PeopleDao {


    public Person create(Person person) {
        String query = "INSERT INTO person (person_id, first_name, last_name) VALUES (?,?,?)";
        Person addedPerson = null; //creating new person with personId

        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setInt(1, person.getPersonId());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getLastName());
            //Check if person already exist first!
            try (
                    PreparedStatement ps = connection.prepareStatement("select count(*) as count from person where person_id= ?")
            ) {
                ps.setInt(1, person.getPersonId());

                ResultSet resultSet1 = ps.executeQuery();

                int isExist;
                if (resultSet1.next()) {
                    isExist = resultSet1.getInt("count");
                    if (isExist > 0) throw new RuntimeException(person.getFirstName() + " " + person.getLastName() + " ,with person id " +person.getPersonId() + " ,already exists");
                }
            }
            //if not exist then add it.
            int result = preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    addedPerson = new Person(resultSet.getInt(1), person.getFirstName(), person.getLastName());
                }
                person = addedPerson;
                System.out.println((result == 1) ? person + "\n" + " Added successfully" : "Not added!");
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
                PreparedStatement preparedStatement = connection.prepareStatement(query)
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
                PreparedStatement preparedStatement = connection.prepareStatement(query)
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
        String query = "UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?";
        int rowsAffected ;

        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setInt(3, person.getPersonId());

            //Check if person already exist first!
            try (
                    PreparedStatement ps = connection.prepareStatement("select count(*) as count from person where person_id= ?")
            ) {
                ps.setInt(1, person.getPersonId());

                ResultSet resultSet1 = ps.executeQuery();

                int isExist;
                if (resultSet1.next()) {
                    isExist = resultSet1.getInt("count");
                    if (isExist == 0) throw new RuntimeException(person.getFirstName() + " " + person.getLastName() + " ,with person id " +person.getPersonId() + " ,does Not exists");
                }
            }
            //if not exist then add it.
            rowsAffected = preparedStatement.executeUpdate();

            System.out.println((rowsAffected == 1) ?  "\n" + person + " Updated successfully!" : person + " did not updated!");
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {

                if (rowsAffected >= 1) {
                    System.out.println(rowsAffected + " post updated!");
                }
            }

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return person;
    }

    public boolean deleteById(int personId) {
        String query = "DELETE FROM person WHERE person_id = ?";
        int rowsAffected;
        boolean del = false;

        try (
                Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setInt(1, personId);
            rowsAffected = preparedStatement.executeUpdate();

            System.out.println((rowsAffected == 1) ? "Person id: " + personId + " Deleted successfully" :  "Person id: " + personId + " does not exist!");
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {

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
