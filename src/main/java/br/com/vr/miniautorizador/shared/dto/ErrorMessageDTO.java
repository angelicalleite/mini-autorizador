package br.com.vr.miniautorizador.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageDTO {

    private Integer statusCode;
    private String message;
    private String description;
    private LocalDateTime date;

}
