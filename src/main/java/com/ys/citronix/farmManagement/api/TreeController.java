package com.ys.citronix.farmManagement.api;

import com.ys.citronix.farmManagement.application.dto.request.TreeRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.application.dto.response.TreeResponseDto;
import com.ys.citronix.farmManagement.application.service.FieldApplicationService;
import com.ys.citronix.farmManagement.domain.service.TreeService;
import com.ys.citronix.sharedkernel.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/trees")
@RequiredArgsConstructor
@Validated
public class TreeController {
    private final TreeService service;
    private final FieldApplicationService fieldService;

    @GetMapping("/{fieldId}")
    public ResponseEntity<List<TreeResponseDto>>  getTreesByFieldId(@PathVariable() String fieldId) {
        FieldResponseDto field = fieldService.findFieldById(UUID.fromString(fieldId));
        List<TreeResponseDto> trees = service.getAllTreesByField(field);
        return new ResponseEntity<>(trees, HttpStatus.OK);
    }

    @PostMapping("/create/{fieldId}")
    public ResponseEntity<ApiResponse<List<TreeResponseDto>>> createTrees(
            @PathVariable String fieldId,
            @RequestBody @Valid List<TreeRequestDto> dto) {

        FieldResponseDto field = fieldService.findFieldById(UUID.fromString(fieldId));
        List<TreeResponseDto> responseDto = service.createTrees(field, dto);
        ApiResponse<List<TreeResponseDto>> response = new ApiResponse<>(
                responseDto,
                "Trees Created successfully",
                HttpStatus.CREATED
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
