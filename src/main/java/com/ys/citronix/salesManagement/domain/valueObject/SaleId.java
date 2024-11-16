package com.ys.citronix.salesManagement.domain.valueObject;

import jakarta.persistence.GeneratedValue;

import java.io.Serializable;
import java.util.UUID;

public record SaleId(@GeneratedValue UUID value) implements Serializable {
}
