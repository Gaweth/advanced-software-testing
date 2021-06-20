package mapper;

import domain.Person;
import dto.PersonDto;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {

    Person mapToPerson(PersonDto personDto);
    PersonDto mapToPersonDto(Person person);
    }


