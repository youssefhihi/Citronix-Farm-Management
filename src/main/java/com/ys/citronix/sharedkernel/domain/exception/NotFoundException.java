package com.ys.citronix.sharedkernel.domain.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public <T>NotFoundException(String entity, T id) {
        super(entity + " with id " + id + " not found");
    }
}
