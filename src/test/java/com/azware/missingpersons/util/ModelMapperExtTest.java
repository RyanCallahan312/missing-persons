package com.azware.missingpersons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import com.azware.missingpersons.dto.GetReportResponse;
import com.azware.missingpersons.model.ReportEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class ModelMapperExtTest {

    private ModelMapper modelMapper;

    @BeforeEach
    void setup() {
        this.modelMapper = new ModelMapper();
    }

    @Test
    void mapList_ShouldTransferFieldValues_WhenMappingEntityToDTO() {
        ReportEntity entity1 = new ReportEntity();
        entity1.setId(1L);
        entity1.setMissingPersonName("azware");
        entity1.setReporterName("azware reporter");
        entity1.setDescription("software company");
        entity1.setReportTime(Instant.now());
        entity1.setLastSeenTime(Instant.now());
        entity1.setLastKnownLocation("NY office");
        entity1.setAdditionalInfo("They be missing");
        entity1.setImageUri("https://i.imgur.com/ccXh2VQ.jpeg");
        entity1.setFound(false);

        ReportEntity entity2 = new ReportEntity();
        entity2.setId(2L);
        entity2.setMissingPersonName("howl");
        entity2.setReporterName("howl reporter");
        entity2.setDescription("howl software company");
        entity2.setReportTime(Instant.now());
        entity2.setLastSeenTime(Instant.now());
        entity2.setLastKnownLocation("howl NY office");
        entity2.setAdditionalInfo("They be howling");
        entity2.setImageUri("https://i.imgur.com/6tZPGpN.jpeg");
        entity2.setFound(true);

        List<ReportEntity> entities = Arrays.asList(entity1, entity2);

        List<GetReportResponse> dtos = ModelMapperExt.mapList(this.modelMapper, entities, GetReportResponse.class);

        assertEquals(2, dtos.size());

        for(int i = 0; i < 2; i++) {
            assertEquals(entities.get(i).getId(), dtos.get(i).getId());
            assertEquals(entities.get(i).getMissingPersonName(), dtos.get(i).getMissingPersonName());
            assertEquals(entities.get(i).getReporterName(), dtos.get(i).getReporterName());
            assertEquals(entities.get(i).getDescription(), dtos.get(i).getDescription());
            assertEquals(entities.get(i).getReportTime(), dtos.get(i).getReportTime());
            assertEquals(entities.get(i).getLastSeenTime(), dtos.get(i).getLastSeenTime());
            assertEquals(entities.get(i).getLastKnownLocation(), dtos.get(i).getLastKnownLocation());
            assertEquals(entities.get(i).getAdditionalInfo(), dtos.get(i).getAdditionalInfo());
            assertEquals(entities.get(i).getImageUri(), dtos.get(i).getImageUri());
            assertEquals(entities.get(i).isFound(), dtos.get(i).isFound());
        }
    }
}
