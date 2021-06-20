package mockito;

import domain.Person;
import dto.PersonDto;
import mapper.PersonMapper;
import mockito.util.PersonUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.PersonRepository;
import service.PersonService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static mockito.util.PersonUtil.getPerson;
import static mockito.util.PersonUtil.getPersonDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MockitoTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonService personService;

    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @Test
    void shouldFindPersonById_ExampleOne() {
        //given
        when(personRepository.findById(anyLong()))
                .thenReturn(
                        Optional.of(getPerson(1L))
                );

        when(personMapper.mapToPersonDto(any()))
                .thenReturn(
                        getPersonDto()
                );

        //when
        final var result = personService.getPersonById(1L);

        //then
        assertPersonDto(result);


    }

    private void assertPersonDto(PersonDto result) {
        assertAll(() -> {
            assertNotNull(result);
            assertEquals("Jan", result.getFirstName());
            assertEquals("Nowak", result.getLastName());
            assertEquals(111111111, result.getPhoneNumber());
        });
    }

    @Test
    void shouldNotFindPersonById_ExampleTwo() {

        //given
        when(personRepository.findById(anyLong()))
                .thenThrow(new NullPointerException("There is no person in db"));

        //then
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> personService.getPersonById(1L));
    }

    @Test
    void shouldDelete() {

        //given
        Mockito.when(personRepository.deletePerson(anyLong()))
                .thenReturn(getPerson(1L));

        Mockito.when(personMapper.mapToPersonDto(any()))
                .thenReturn(getPersonDto());

        //when
        final var result = personService.deletePerson(anyLong());

        //then
        assertPersonDto(result);
    }

    @Test
    void shouldDeletePerson() {
        //given
        Mockito.when(personRepository.deletePerson(anyLong()))
                .thenReturn(getPerson(1L));

        Mockito.when(personMapper.mapToPersonDto(any()))
                .thenReturn(getPersonDto());

        //when
        final var result = personService.deletePerson(anyLong());

        //then
        Mockito.verify(personRepository).deletePerson(1L);
        Mockito.verify(personMapper).mapToPerson(any());
        assertPersonDto(result);
    }

    @Test
    void shouldFindPersonById_ExampleTwo(){

        //given
        var exampleId = 1;
        when(personRepository.findById(anyLong()))
                .thenAnswer(invocation ->
                {
                    if(exampleId == 1){
                        return Optional.of(getPerson(1L));
                    } else {
                        return Optional.of(getPerson(Long.parseLong(UUID.randomUUID().toString())));
                    }
                });
        when(personMapper.mapToPersonDto(any()))
                .thenReturn(getPersonDto());

        //when
        final var result = personService.getPersonById(1L);

        //then
        assertPersonDto(result);
    }

    @Test
    void shouldCreatePerson_ExampleOne(){

        //given
        var inOrder = inOrder(personMapper);
        when(personRepository.addPerson(any()))
                .thenReturn(getPerson(1L));
        when(personMapper.mapToPerson(any()))
                .thenReturn(getPerson(1L));
        when(personMapper.mapToPersonDto(any()))
                .thenReturn(getPersonDto());

        //when
        final var result = personService.createPerson(getPersonDto());

        //then
        inOrder.verify(personMapper, times(1)).mapToPerson(any());
        inOrder.verify(personMapper, times(1)).mapToPersonDto(any());

        Mockito.verify(personRepository).addPerson(personCaptor.capture());
        List<Person> allValues = personCaptor.getAllValues();
        assertPerson(allValues.get(0));



        assertPersonDto(result);
    }

    private void assertPerson(Person result) {
        assertAll(() -> {
            assertNotNull(result);
            assertEquals("Jan", result.getFirstName());
            assertEquals("Nowak", result.getLastName());
            assertEquals(111111111, result.getPhoneNumber());
        });
    }
}


