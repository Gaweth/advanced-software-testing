package service;

import dto.PersonDto;
import mapper.PersonMapper;
import repository.PersonRepository;

public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;


    public PersonService(final PersonRepository personRepository,final PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public PersonDto getPersonById(final Long id){
        return personRepository.findById(id).
                map(personMapper::mapToPersonDto)
                .orElseThrow(()-> new NullPointerException("There is no person in db"));
    }
}
