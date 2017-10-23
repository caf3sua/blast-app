package com.blast.web.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blast.service.VisionService;
import com.blast.service.vision.dto.AnnotateImageResponse;
import com.blast.service.vision.dto.EntityAnnotation;
import com.blast.service.vision.dto.VisionResponse;
import com.blast.service.vision.dto.VisionResultResponse;
import com.blast.web.rest.vm.VisionVM;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/vision")
public class VisionController {

    private final Logger log = LoggerFactory.getLogger(VisionController.class);

    private final VisionService visionService;
    
    public VisionController(VisionService visionService) {
        this.visionService = visionService;
    }

    @PostMapping("/detect")
    @Timed
    public ResponseEntity<List<EntityAnnotation>> authorize(@Valid @RequestBody VisionVM visionVM, HttpServletResponse response) throws JsonProcessingException {

    	VisionResponse data = visionService.detectLabel(visionVM);
    	
    	List<EntityAnnotation> result = new ArrayList<>();
    	// Get all from response
    	for (AnnotateImageResponse item : data.getResponses()) {
    		result.addAll(item.getLabelAnnotations());
    		result.addAll(item.getLandmarkAnnotations());
    		result.addAll(item.getLogoAnnotations());
    		result.addAll(item.getTextAnnotations());
		}
    	
    	Collections.sort(result, EntityAnnotation.EntityAnnotationComparator);
    	
        return new ResponseEntity<>(result, null, HttpStatus.OK);
    }
}
