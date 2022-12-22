package se.lexicon;

import se.lexicon.dao.PeopleDao;
import se.lexicon.dao.PeopleDaoImp;
import se.lexicon.model.Person;

import java.util.Collection;

public class App {



    public static void main(String[] args) {

        PeopleDao pd = new PeopleDaoImp();

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
        Collection<Person> names= pd.findByName("test");
        names.forEach(System.out::println);

    }
}
