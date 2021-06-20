package service;

import domain.Person;
import dto.PersonDto;
import mapper.PersonMapper;
import repository.PersonRepository;
import validator.PersonValidator;

import java.util.Optional;

public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;


    public PersonService(final PersonRepository personRepository,final PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public PersonDto createPerson(final PersonDto personDto){
        return Optional.of(PersonValidator.isPersonValidated(personDto))
                .filter(result-> result)
                .map(result-> personMapper.mapToPerson(personDto))
                .map(personRepository::addPerson)
                .map(personMapper::mapToPersonDto)
                .orElseThrow(()-> new IllegalArgumentException("Person has not been created"));
    }

    public PersonDto updatePerson(final PersonDto personDto) {
        return Optional.of(PersonValidator.isPersonValidated(personDto))
                .filter(result -> result)
                .map(result -> personMapper.mapToPerson(personDto))
                .map(personRepository::updatePerson)
                .map(personMapper::mapToPersonDto)
                .orElseThrow(() -> new IllegalArgumentException("Person has not been updated"));
    }


    public PersonDto deletePerson(final Long id){
        return Optional.ofNullable(id)
                .map(result -> personRepository.deletePerson(id))
                .map(personMapper::mapToPersonDto)
                .orElseThrow(()-> new IllegalArgumentException("person has not been deleted"));
    }



    public PersonDto getPersonById(final Long id){
        return personRepository.findById(id).
                map(personMapper::mapToPersonDto)
                .orElseThrow(()-> new NullPointerException("There is no person in db"));
    }


}
