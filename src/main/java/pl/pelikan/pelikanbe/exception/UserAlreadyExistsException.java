package pl.pelikan.pelikanbe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserAlreadyExistsException extends Exception {

    private final String message;
}
