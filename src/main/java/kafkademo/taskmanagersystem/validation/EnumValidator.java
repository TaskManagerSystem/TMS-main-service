package kafkademo.taskmanagersystem.validation;

import java.util.Arrays;
import java.util.Optional;

public class EnumValidator {
    public static <E extends Enum<E>> Optional<E> findConstantIfValid(Class<E> eClass, String value) {
        return Arrays.stream(eClass.getEnumConstants())
                .filter(e -> e.name().equals(value))
                .findFirst();
    }
}
