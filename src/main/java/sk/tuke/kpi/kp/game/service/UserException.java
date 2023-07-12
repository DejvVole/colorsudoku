package sk.tuke.kpi.kp.game.service;

public class UserException extends RuntimeException{
    public UserException(String message) {
        super(message);
    }
    public UserException(String message, Throwable cause) {
        super(message, cause);
    }


}
