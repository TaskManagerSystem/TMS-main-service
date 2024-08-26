package kafkademo.taskmanagersystem.validation;

import java.util.Arrays;
import java.util.Optional;

public class EnumValidator {
    private EnumValidator() {
    }

    public static <E extends Enum<E>> Optional<E> findConstantIfValid(Class<E> enumClass,
                                                                      String value) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.name().equals(value))
                .findFirst();
    }
}
