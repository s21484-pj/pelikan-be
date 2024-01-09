package pl.pelikan.pelikanbe.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException exception) {
        var entityNameWithId = exception.getMessage().split(" ");
        var message = entityNameWithId[0] + " with id = " + entityNameWithId[1] + " does not exist.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(HttpStatus.NOT_FOUND, message));
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ApiError> handleInvalidId(InvalidIdException exception) {
        var ids = exception.getMessage().split(" ");
        var message = "Provided identifiers must be the same. Actual: " + ids[0] + ", " + ids[1];
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(HashtagAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleHashtagAlreadyExists(HashtagAlreadyExistsException exception) {
        String message = "Hashtag " + exception.getMessage() + " already exists.";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(HttpStatus.CONFLICT, message));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(UserAlreadyExistsException exception) {
        String message = "User with email '" + exception.getMessage() + "' already exists.";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiError(HttpStatus.CONFLICT, message));
    }
}
