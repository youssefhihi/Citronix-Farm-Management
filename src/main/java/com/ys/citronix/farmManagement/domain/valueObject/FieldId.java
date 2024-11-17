package com.ys.citronix.farmManagement.domain.valueObject;

import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.UUID;

public record FieldId(@UuidGenerator UUID value) implements Serializable {
}
