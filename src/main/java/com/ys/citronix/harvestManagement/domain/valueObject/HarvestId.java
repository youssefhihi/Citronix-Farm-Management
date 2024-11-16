package com.ys.citronix.harvestManagement.domain.valueObject;

import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.UUID;

public record HarvestId(@UuidGenerator UUID id) implements Serializable {
}
