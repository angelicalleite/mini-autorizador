package br.com.vr.miniautorizador.core.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private Integer statusCode;
    private String message;
    private String description;
    private LocalDateTime date;

}
