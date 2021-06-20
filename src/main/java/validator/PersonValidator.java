package validator;

import dto.PersonDto;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Optional;

@NoArgsConstructor
public class PersonValidator {

    private static final int expectedPhoneNumberCount = 9;

    public static boolean isPersonValidated(PersonDto personDto) {

        return isPersonNotNull(personDto) &&
                checkNamesLength(personDto.getFirstName(), personDto.getLastName()) &&
                checkPhoneNumberCount(personDto.getPhoneNumber());
    }

    private static boolean checkPhoneNumberCount(int phoneNumber) {
        int phoneNumberCount = String.valueOf(phoneNumber).length();
        return expectedPhoneNumberCount == phoneNumberCount;
    }
    private static boolean checkNamesLength(String firstName,
                                            String lastName) {
        return ObjectUtils.allNotNull(firstName, lastName) &&
                firstName.length() < 20 &&
                lastName.length() < 20;
    }

    private static boolean isPersonNotNull(PersonDto personDto) {
        return Optional.ofNullable(personDto)
                .filter(personToCheck ->
                        ObjectUtils.allNotNull(
                                personToCheck.getFirstName(),
                                personToCheck.getLastName(),
                                personToCheck.getPhoneNumber()))
                .isPresent();
    }


}
