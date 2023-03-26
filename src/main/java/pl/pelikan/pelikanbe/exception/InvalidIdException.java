package pl.pelikan.pelikanbe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidIdException extends Exception {

    private String message;
}
