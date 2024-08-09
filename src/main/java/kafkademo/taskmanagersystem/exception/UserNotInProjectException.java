package kafkademo.taskmanagersystem.exception;

public class UserNotInProjectException extends RuntimeException {
    public UserNotInProjectException(String message) {
        super(message);
    }
}
