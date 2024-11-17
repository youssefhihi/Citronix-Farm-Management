package com.ys.citronix.salesManagement.domain.valueObject;

import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.UUID;

public record SaleId(@UuidGenerator UUID value) implements Serializable {
}
