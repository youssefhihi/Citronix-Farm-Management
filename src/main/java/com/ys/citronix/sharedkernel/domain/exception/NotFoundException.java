package com.ys.citronix.sharedkernel.domain.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, UUID id) {
        super(entity + " with id " + id + " not found");
    }
}
