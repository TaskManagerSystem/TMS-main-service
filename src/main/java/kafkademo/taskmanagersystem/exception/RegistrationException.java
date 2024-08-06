package kafkademo.taskmanagersystem.exception;

public class RegistrationException extends RuntimeException {
    public RegistrationException() {
        super();
    }

    public RegistrationException(String message) {
        super(message);
    }
}
