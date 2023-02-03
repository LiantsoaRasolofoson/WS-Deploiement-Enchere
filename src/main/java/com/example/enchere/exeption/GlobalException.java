package com.example.enchere.exeption;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class GlobalException {

    @ExceptionHandler(RessourceException.class)
    public @ResponseBody Map<String, Object> handleErrorRetourException(RessourceException e) {
        Map<String, Object> errorMap = new HashMap<String, Object>();
        errorMap.put("error", e.getError());
        return errorMap;
    }
}
