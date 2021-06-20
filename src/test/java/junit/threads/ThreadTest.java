package junit.threads;

import domain.Calculator;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThreadTest {

    private final Calculator calculator = new Calculator();
    private final List<BigDecimal> bigDecimals = List.of(BigDecimal.ONE, BigDecimal.ZERO);

    //JUNIT
    @Test
    void shouldThrowNumberFormatException() {

        //given
        var word = "One";

        //when
        final var result = Assertions.assertThrows(NumberFormatException.class, () -> Integer.parseInt(word));

        //then
        Assertions.assertEquals(NumberFormatException.class, result.getCause());
        Assertions.assertEquals("For input string: \"One\"", result.getMessage());

    }

    @Test
    void shouldThrowIndexOutOfBoundsExceptionExampleOne() {

        //given
        var listOfNumber = IntStream.rangeClosed(1,10)
                .boxed()
                .collect(Collectors.toList());

        //when
        final var result = Assertions.assertThrows(IndexOutOfBoundsException.class, ()->listOfNumber.get(10));

        //then
        Assertions.assertEquals(IndexOutOfBoundsException.class, result.getCause());
        Assertions.assertEquals("Index 10 out of bounds for length 10", result.getMessage());

    }

    //ASSERTj
    @Test
    void shouldThrowArithmeticExceptionExampleOne() {

        //when
        final var exception = Assertions.assertThrows(ArithmeticException.class, ()-> calculator.divide(10,0));

        //then
        org.assertj.core.api.Assertions.assertThat(exception)
                .hasMessage("/ by zero")
                .hasNoCause();
    }

    @Test
    void shouldThrowArithmeticExceptionExampleTwo() {
        //do uzupelnienia
    }

    @Test
    void shouldThrowIndexOutOfBoundsExceptionExampleTwo() {
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> bigDecimals.get(2))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Index: 2 Size: 2");
    }

    @Test
    void shouldThrowIndexOutOfBoundsExceptionExampleThree() {

        org.assertj.core.api.Assertions.assertThatExceptionOfType(IndexOutOfBoundsException.class)
                .isThrownBy(()->bigDecimals.get(2))
                .withMessage("Index: 2 Size: 2")
                .withNoCause();
    }



}
