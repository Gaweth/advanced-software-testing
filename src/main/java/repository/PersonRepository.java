package repository;

import domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {


    Person addPerson(Person person);
    Person deletePerson(Long id);
    Person updatePerson(Person person);
    Optional<Person>findById(Long id);
    Optional<List<Person>> findByIsPaidTrue();


}
