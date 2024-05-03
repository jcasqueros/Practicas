package com.viewnext.practica.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents the body of an error response in the presentation layer.
 *
 * <p>This class includes information such as the timestamp and error message.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * Body errorBody = new Body("Resource not found");
 * }
 * </pre>
 *
 * <p>The {@code date} field is annotated with {@link JsonFormat} to specify the format in which the timestamp
 * should be serialized when converting the object to JSON.</p>
 *
 * <p>The class provides both a parameterized constructor for creating an instance with a specific message.</p>
 *
 * @author Franciosco Balonero Olivera
 */
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Body {

    /**
     * Timestamp of when the error occurred.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime date = LocalDateTime.now();

    /**
     * Error message.
     */
    @NonNull
    private String message;
}

