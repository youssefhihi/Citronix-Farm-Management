package com.ys.citronix.harvestManagement.application.dto.request;


import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.harvestManagement.application.dto.response.HarvestResponseDto;

import java.util.List;

public record HarvestDetailsRequestDto(
        HarvestResponseDto harvest,
        List<TreeResponseDto> trees
) {
}
