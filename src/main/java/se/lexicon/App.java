package se.lexicon;

import se.lexicon.dao.PeopleDao;
import se.lexicon.dao.impl.PeopleDaoImp;
import se.lexicon.dao.TodoItemsDao;
import se.lexicon.dao.impl.TodoItemsDaoImp;
import se.lexicon.model.Person;

public class App {


    public static void main(String[] args) {

        PeopleDao pd = new PeopleDaoImp();
        TodoItemsDao to_do = new TodoItemsDaoImp();
// _________________ PERSON ______________//
        // CREATE
       /* Person person = new Person (7,"Mehrdad", "Javan");
        pd.create(person);*/

        // findAll
        /*Collection<Person> all = pd.findAll();
        all.forEach(System.out::println);*/

        //findById
        Person  person = pd.findById(4);
        System.out.println(person);

        //findByName
       /* Collection<Person> names= pd.findByName("test");
        names.forEach(System.out::println);*/

        //UPDATE
       /* Person update = new Person(8, "Johan", "Andersson");
        pd.update(update);*/

        //DELETE
        //boolean del = pd.deleteById(3);

        // _________________ TO DO ______________//

        //CREATE
       /* Todo todo = new Todo(3, "Design pattern", "Pattern for new app", LocalDate.of(2023,1, 28), false,2);
        todo = to_do.create(todo);
        System.out.println("todo = " + todo);*/

        //FIND BY ID
        /*Todo todo = to_do.findById(2);
        System.out.println(todo);*/

        //FIND BY ALL
        /*Collection<Todo> all = to_do.findAll();
        all.forEach(System.out::println);*/

        //FIND BY DONE
        /*Collection<Todo> done = to_do.findByDoneStatus(false);
        done.forEach(System.out::println);*/

        //FIND BY ASSIGNEE
        /*Collection<Todo> assignee = to_do.findByAssignee(1);
        assignee.forEach(System.out::println);*/

        //FIND BY ASSAGNEE (PERSON)
        /*Person person = pd.findById(7);
        Collection<Todo> users = to_do.findByAssignee(person);
        users.forEach(System.out::println);*/

        //FIND BY UNASSAGNED
        /*Collection<Todo> unassignedTodoItems = to_do.findByUnassignedTodoItems();
        unassignedTodoItems.forEach(System.out::println);*/

        //UPDATE
        //Todo Update = to_do.update(new Todo(9,"Update title", "update description test",LocalDate.of(2023,2,25),false,7));

        //DELETE
        boolean del = to_do.deleteById(5);






    }
}
