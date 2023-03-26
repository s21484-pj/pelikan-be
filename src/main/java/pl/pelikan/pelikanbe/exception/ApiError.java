package pl.pelikan.pelikanbe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Getter
@Setter
public class ApiError {

    private final HttpStatusCode httpStatus;

    private final String cause;
}