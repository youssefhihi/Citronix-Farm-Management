package com.ys.citronix.harvestManagement.infrastructure.eventHandler;

import com.ys.citronix.harvestManagement.application.dto.request.HarvestDetailsRequestDto;
import com.ys.citronix.harvestManagement.application.service.HarvestDetailsApplicationService;
import com.ys.citronix.harvestManagement.domain.events.HarvestCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HarvestCreatedEventHandler {
    private final HarvestDetailsApplicationService service;

    @EventListener
    public void handleHarvestCreatedEvent(HarvestCreatedEvent event) {
            service.createHarvestDetails(new HarvestDetailsRequestDto(event.harvest(),event.trees()));
    }
}
