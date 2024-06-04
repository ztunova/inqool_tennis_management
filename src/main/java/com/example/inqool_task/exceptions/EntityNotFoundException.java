package com.example.inqool_task.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {
        super("Entity not found");
    }

    public EntityNotFoundException(Long id) {
        super("Steward with id " + id.toString() + " not found");
    }

    public EntityNotFoundException(String type, Long id) {
        super(type + " with id " + id.toString() + " not found");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

}
