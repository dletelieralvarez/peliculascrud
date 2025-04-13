package com.microserviciosemana5.peliculascruds5;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microserviciosemana5.peliculascruds5.model.ApiResult;

//import java.util.HashMap;
//import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResult<List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getBindingResult()
                                 .getFieldErrors()
                                 .stream()
                                 .map(error -> error.getDefaultMessage())
                                 .collect(Collectors.toList());

        ApiResult<List<String>> respuesta = new ApiResult<>(
            "Error de validación de campos",
            errores,
            HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.badRequest().body(respuesta);
    }
    // public ResponseEntity<Map<String, String>> handleJsonParseError(HttpMessageNotReadableException ex) {
    //     Map<String, String> errorResponse = new HashMap<>();
    //     errorResponse.put("error", "Error en el formato del JSON");
    //     errorResponse.put("detalle", "Verifica que todos los campos tengan valores válidos y estén bien formateados.");
    //     return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    // }
}
