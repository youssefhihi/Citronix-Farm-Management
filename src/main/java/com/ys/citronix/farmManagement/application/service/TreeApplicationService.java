package com.ys.citronix.farmManagement.application.service;


import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;

import java.util.List;
import java.util.UUID;

public interface TreeApplicationService {
    List<TreeResponseDto> findAllTreesByIds(List<UUID> ids);
}
