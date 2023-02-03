package com.example.enchere.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.enchere.retour.ErrorRetour;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RessourceException extends RuntimeException{
    
    private ErrorRetour error;

    public void setError(ErrorRetour error){
        this.error = error;
    }

    public ErrorRetour getError(){
        return this.error;
    }
    
    public RessourceException (ErrorRetour error){
        this.error = error;
    }
}
