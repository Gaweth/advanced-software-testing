package mockito.util;

import domain.Person;
import dto.PersonDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PersonUtil {

    private static final String FIRSTNAME = "Jan";
    private static final String LASTNAME = "Nowak";
    private static final Integer phoneNumber = 111111111;

    public static PersonDto getPersonDto() {
        return PersonDto.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .phoneNumber(phoneNumber)
                .build();
    }

    public static Person getPerson(long id) {
        return new Person(id, FIRSTNAME, LASTNAME, phoneNumber, true);
    }
}
