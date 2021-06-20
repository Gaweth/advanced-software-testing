package junit.parametrized;


import domain.Calculator;
import junit.parametrized.provider.DataWithResultOfSubstractionProvider;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.junit.platform.commons.util.StringUtils;
import validator.WordValidator;


import java.time.Month;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ParametrizedTest {

    private final Calculator calculator = new Calculator();

    // value source
    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 100})
    void shouldReturnTrueAfterDivisibleByFive(int number) {
        Assertions.assertEquals(0, number % 5);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void shouldReturnTrueForNullOrBlankString(String word) {
        Assertions.assertTrue(WordValidator.isBlank(word));

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 7, 9})
    void shouldReturnTrueAfterAddingValues(int number) {

        //given
        var expectedResult = Stream.of(2d, 4d, 6d, 8d, 10d)
                .collect(Collectors.toList());

        //when
        final var addResult = calculator.add(1, number);

        //then
        assertTrue(expectedResult.contains(addResult));

    }

    @ParameterizedTest
    @ValueSource(classes = {String.class, Integer.class, Double.class})
    void shouldPassClassTypeAsParam(Class<?> clazz) {

        //given
        var clazzName = clazz.getName();
        Predicate<String> emptyPredicate = word -> ObjectUtils.isEmpty(word);

        //when
        final var result = emptyPredicate.test(clazzName);

        //then
        assertFalse(result);
    }

    //NULL and/or EMPTY SOURCE
    @ParameterizedTest
    @NullSource
    void shouldReturnTrueForNullInputs(String input) {

        //when
        final var result = WordValidator.isBlank(input);

        //then
        assertTrue(result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldReturnTrueForAllTypesOfBlankStrings(String input) {

        //when
        final var result = WordValidator.isBlank(input);

        //then
        assertTrue(result);

    }


    /*
    ENUM SOURCE
    */
    @ParameterizedTest
    @EnumSource(Month.class)
    void shouldGetValueForAmonthIsBetween1And12(Month month) {

        //given
        var monthNumber = month.getValue();
        Predicate<Integer> isBetweenPredicate = number -> number >= 1 && number <= 12;

        //when //za var moze byc boolean
        final boolean result = isBetweenPredicate.test(monthNumber);

        //then
        assertTrue(result);
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "NOVEMBER"})
    void monthsShouldHave30DaysLong(Month month) {

        //given
        final boolean isALeapYear = false;

        //when
        final var result = month.length(isALeapYear);

        //then
        assertEquals(30, result);
    }

    @ParameterizedTest
    @EnumSource(
            value = Month.class,
            names = {"APRIL", "JUNE", "NOVEMBER"},
            mode = EnumSource.Mode.EXCLUDE
    )
    void shouldExcludeMonthOtherAre31DaysLong(Month month) {

        //given
        final boolean isALeapYear = false;

        //when
        final var result = month.length(isALeapYear);

        //then
        assertEquals(30, result);
    }

    /*
    CSV SOURCE
     */

    @ParameterizedTest
    @CsvSource({"course,COURSE", "couRSe,COURSE", "testInG,TESTING", "Testing,TESTING"})
    void shouldEqualBothValuesAfterUppercase(String input,
                                             String expected) {

        //when
        final var actualvalue = input.toLowerCase();

        //then
        assertEquals(expected, actualvalue);

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/file.csv", numLinesToSkip = 1)
    void shouldGenerateTheExpectedUppercaseValueFromCSVFile(String input,
                                                            String expected) {
        //when
        final var result = input.toUpperCase();

        //then
        assertEquals(expected, result);

    }


    //METHOS SOURCE

    @ParameterizedTest
    @MethodSource("dataProviders")
    void shouldReturnTrueForNullOrBlankStrings(String input,
                                               boolean expected) {
        //when
        final var result = WordValidator.isBlank(input);

        //then
        assertEquals(expected, result);

    }

    private static Stream<Arguments> dataProviders() {

        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of(" ", true),
                Arguments.of("not blank ", false)
        );
    }

    @ParameterizedTest
    @MethodSource()
    void shouldReturnTrueForNullOrBlankStringsOneArgument(String input) {

        //when
        final var result = WordValidator.isBlank(input);


        //then
        assertTrue(result);
    }

    private static Stream<String> shouldReturnTrueForNullOrBlankStringsOneArgument(){
        return Stream.of(null, "", " ");
    }


    /*
    ARGUMENT SOURCE
     */


@ParameterizedTest
@ArgumentsSource(DataWithResultOfSubstractionProvider.class)
    void shouldReturnExpectedValueUsingArgProvider(int data,
                                                   double result) {

    //when
    final var resultOfSubsraction = calculator.subtract(data, 1);

    //then
    assertEquals(result, resultOfSubsraction);



}

}





















