package se.lexicon;

import se.lexicon.dao.PeopleDao;
import se.lexicon.dao.PeopleDaoImp;
import se.lexicon.dao.TodoItemsDao;
import se.lexicon.dao.TodoItemsDaoImp;
import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.time.LocalDate;
import java.util.Collection;

public class App {


    public static void main(String[] args) {

        PeopleDao pd = new PeopleDaoImp();
        TodoItemsDao to_do = new TodoItemsDaoImp();
// _________________ PERSON ______________//
        // CREATE
        /*Person person = new Person (6,"Mehrdad", "Javan");
        pd.create(person);
        System.out.println("person = " + person);*/

        // findAll
        /*Collection<Person> all = pd.findAll();
        all.forEach(System.out::println);*/

        //findById
       /* Person  person = pd.findById(5);
        System.out.println(person);*/

        //findByName
       /* Collection<Person> names= pd.findByName("test");
        names.forEach(System.out::println);*/

        //UPDATE
       /* Person person = pd.findById(6);
        person.setFirstName("TestUpdate");
        person.setLastName("Updatesson");
        pd.update(person);*/

        //DELETE
        //boolean del = pd.deleteById(3);

        // _________________ TO DO ______________//

        //CREATE
        Todo todo = new Todo(1, "Write Code", "Write some Java code", LocalDate.of(2022,12,22), false,1);
        todo = to_do.create(todo);
        System.out.println("todo = " + todo);


    }
}
