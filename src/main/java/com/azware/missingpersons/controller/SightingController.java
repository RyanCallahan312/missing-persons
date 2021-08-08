package com.azware.missingpersons.controller;

import java.util.List;
import java.util.Arrays;

import javax.validation.Valid;

import com.azware.missingpersons.dto.BadRequestResponse;
import com.azware.missingpersons.dto.CreateSightingRequest;
import com.azware.missingpersons.dto.GetSightingResponse;
import com.azware.missingpersons.dto.SpecificationRequest;
import com.azware.missingpersons.dto.UpdateSightingRequest;
import com.azware.missingpersons.model.SightingEntity;
import com.azware.missingpersons.service.SightingService;
import com.azware.missingpersons.util.ModelMapperExt;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("api/v1/sighting")
public class SightingController {
    private final ModelMapper modelMapper;
    private final SightingService sightingService;

    @Autowired
    public SightingController(ModelMapper modelMapper, SightingService sightingService) {
        this.modelMapper = modelMapper;
        this.sightingService = sightingService;
    }

    @GetMapping("sightings/{reportId}")
    @ResponseStatus(HttpStatus.OK)
    public List<GetSightingResponse> getAllSightingsForReport(@PathVariable long reportId) {
        List<SightingEntity> sightingEntities = sightingService.getSightings(reportId);
        List<GetSightingResponse> sightingDTOs = ModelMapperExt.mapList(modelMapper, sightingEntities,
                GetSightingResponse.class);
        return sightingDTOs;
    }

    @GetMapping("sightings")
    @ResponseStatus(HttpStatus.OK)
    public List<GetSightingResponse> getAllSightings(
            @RequestBody(required = false) SpecificationRequest specificationRequest) {
        List<SightingEntity> sightingEntities;
        if (specificationRequest == null) {
            sightingEntities = sightingService.getMostRecentSightings();
        } else {
            sightingEntities = sightingService.getSightings(specificationRequest);
        }
        List<GetSightingResponse> sightingDTOs = ModelMapperExt.mapList(modelMapper, sightingEntities,
                GetSightingResponse.class);
        return sightingDTOs;
    }

    @GetMapping("{sightingId}")
    @ResponseStatus(HttpStatus.OK)
    public GetSightingResponse getSighting(@PathVariable long sightingId) {
        SightingEntity sightingEntity = sightingService.getSighting(sightingId);
        GetSightingResponse sightingDTO = modelMapper.map(sightingEntity, GetSightingResponse.class);
        return sightingDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetSightingResponse createSighting(@RequestBody @Valid CreateSightingRequest createSightingRequest) {
        SightingEntity sightingEntity = sightingService.createSighting(createSightingRequest);
        GetSightingResponse sightingDTO = modelMapper.map(sightingEntity, GetSightingResponse.class);
        return sightingDTO;
    }

    @PutMapping("{sightingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSighting(@PathVariable long sightingId,
            @RequestBody @Valid UpdateSightingRequest updateSightingRequest) {
        sightingService.updateSighting(sightingId, updateSightingRequest);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BadRequestResponse handleInvalidArgumentsExceptions(RuntimeException e) {
        String message = "Bad Request";
        return new BadRequestResponse(message, Arrays.toString(e.getStackTrace()));
    }

}
