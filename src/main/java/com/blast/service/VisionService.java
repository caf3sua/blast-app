package com.blast.service;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blast.service.vision.dto.AnnotateImageRequest;
import com.blast.service.vision.dto.Feature;
import com.blast.service.vision.dto.Image;
import com.blast.service.vision.dto.ImageSource;
import com.blast.service.vision.dto.VisionRequest;
import com.blast.service.vision.dto.VisionResponse;
import com.blast.web.rest.vm.VisionVM;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class VisionService {

    private final Logger log = LoggerFactory.getLogger(VisionService.class);

    private final String GOOGLE_VISION_API_URL = "https://vision.googleapis.com/v1/images:annotate";
    
    @Value("${spring.google.vision.api-key}")
    private String apiKey;
    
    @PostConstruct
    private void init() {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public VisionService() {
    }

    public VisionResponse detectLabel(VisionVM visionVM) throws JsonProcessingException {
    	RestTemplate restTemplate = new RestTemplate();
    	
    	String url = GOOGLE_VISION_API_URL + "?key=" + apiKey;
    	
    	Image image = new Image();
    	if (StringUtils.isNotEmpty(visionVM.getImageUri())) {
    		ImageSource source = new ImageSource(visionVM.getImageUri());
    		image.setSource(source);
    	} else {
    		image.setContent(visionVM.getContent());
    	}
    	
    	VisionRequest visionRequest = new VisionRequest();
    	AnnotateImageRequest imgRequest = new AnnotateImageRequest(); 
    	imgRequest.setImage(image);
    	
    	// create request body
    	if (StringUtils.isNotEmpty(visionVM.getType())) {
    		Feature feature = new Feature(visionVM.getType(), visionVM.getMaxResults());
        	imgRequest.getFeatures().add(feature);
    	} else {
    		Feature featureLabel = new Feature("LABEL_DETECTION", visionVM.getMaxResults());
        	imgRequest.getFeatures().add(featureLabel);
        	
        	Feature featureText = new Feature("TEXT_DETECTION", visionVM.getMaxResults());
        	imgRequest.getFeatures().add(featureText);
        	
        	Feature featureLogo = new Feature("LOGO_DETECTION", visionVM.getMaxResults());
        	imgRequest.getFeatures().add(featureLogo);
        	
        	Feature featureLandmark = new Feature("LANDMARK_DETECTION", visionVM.getMaxResults());
        	imgRequest.getFeatures().add(featureLandmark);
    	}
    	
    	visionRequest.getRequests().add(imgRequest);

    	// set headers
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<VisionRequest> entity = new HttpEntity<VisionRequest>(visionRequest, headers);

    	ResponseEntity<VisionResponse> response = restTemplate
    			.exchange(url, HttpMethod.POST, entity, VisionResponse.class);
    	return response.getBody();
    }

}
