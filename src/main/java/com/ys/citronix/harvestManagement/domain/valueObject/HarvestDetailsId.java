package com.ys.citronix.harvestManagement.domain.valueObject;

import jakarta.persistence.GeneratedValue;

import java.io.Serializable;
import java.util.UUID;

public record HarvestDetailsId(@GeneratedValue UUID id) implements Serializable {
}
