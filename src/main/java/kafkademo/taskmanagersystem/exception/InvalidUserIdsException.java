package kafkademo.taskmanagersystem.exception;

public class InvalidUserIdsException extends RuntimeException {
    public InvalidUserIdsException(String message) {
        super(message);
    }
}
