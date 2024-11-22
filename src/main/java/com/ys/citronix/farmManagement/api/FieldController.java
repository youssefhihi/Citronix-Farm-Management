package com.ys.citronix.farmManagement.api;

import com.ys.citronix.farmManagement.application.dto.request.FieldUpdateRequestDto;
import com.ys.citronix.farmManagement.application.service.FarmApplicationService;
import com.ys.citronix.farmManagement.application.dto.request.FieldRequestDto;
import com.ys.citronix.farmManagement.application.dto.response.FarmResponseDto;
import com.ys.citronix.farmManagement.application.dto.response.FieldResponseDto;
import com.ys.citronix.farmManagement.domain.service.FieldService;
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
@RequestMapping("/api/v1/field")
@RequiredArgsConstructor
@Validated
public class FieldController {
    private final FieldService service;
    private final FarmApplicationService farmService;

    @PostMapping("/create/{farmId}")
    public ResponseEntity<ApiResponse<List<FieldResponseDto>>> createFields(@RequestBody @Valid List<FieldRequestDto> fields,@PathVariable String farmId) {
        FarmResponseDto farm =  farmService.findFarmById(UUID.fromString(farmId));
        List<FieldResponseDto> responseDto = service.addFields(farm, fields);
       ApiResponse<List<FieldResponseDto>> response = new ApiResponse<>(
               responseDto,
               "Fields created successfully",
               HttpStatus.CREATED
       );
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{farmId}")
    public ResponseEntity<ApiResponse<List<FieldResponseDto>>> getFields(@PathVariable String farmId) {
        FarmResponseDto farm =  farmService.findFarmById(UUID.fromString(farmId));
        List<FieldResponseDto> responseDto =  service.getAllFieldsByFarmId(farm);
        ApiResponse<List<FieldResponseDto>> response = new ApiResponse<>(
                responseDto,
                "Fields found successfully",
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<FieldResponseDto>> updateFields(@RequestBody @Valid FieldUpdateRequestDto field) {
        FieldResponseDto fieldResponseDto = service.updateField(field);
        ApiResponse<FieldResponseDto> response = new ApiResponse<>(
                fieldResponseDto,
                "field Updated successfully",
                HttpStatus.OK
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteField(@PathVariable String id) {
        service.deleteFieldById(UUID.fromString(id));
        return new ResponseEntity<>("field deleted successfully", HttpStatus.OK);
    }
}
