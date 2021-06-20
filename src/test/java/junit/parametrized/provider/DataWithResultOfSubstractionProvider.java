package junit.parametrized.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class DataWithResultOfSubstractionProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(1, 0d),
                Arguments.of(3, 2d),
                Arguments.of(5, 4d),
                Arguments.of(7, 6d),
                Arguments.of(9, 8d)
        );
    }
}
