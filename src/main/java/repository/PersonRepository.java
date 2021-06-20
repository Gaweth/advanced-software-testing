package repository;

import domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {


    Person add(Person person);
    Person delete(Long id);
    Person update(Person person);
    Optional<Person>findById(Long id);
    Optional<List<Person>> findByIsPaidTrue();


}
