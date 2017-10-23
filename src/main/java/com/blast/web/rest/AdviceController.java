package com.blast.web.rest;

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

import com.blast.service.AdviceService;
import com.blast.service.AmazonProductApiService;
import com.blast.service.dto.ItemInfoDTO;
import com.blast.service.dto.WikiInfoDTO;
import com.blast.web.rest.vm.AdviceVM;
import com.codahale.metrics.annotation.Timed;

@RestController
@RequestMapping("/api/advice")
public class AdviceController {

    private final Logger log = LoggerFactory.getLogger(AdviceController.class);

    private final AdviceService adviceService;
    
    private final AmazonProductApiService amazonProductApiService;
    
    public AdviceController(AdviceService adviceService
    		, AmazonProductApiService amazonProductApiService) {
        this.adviceService = adviceService;
        this.amazonProductApiService = amazonProductApiService;
    }

    @PostMapping("/wiki")
    @Timed
    public ResponseEntity<List<WikiInfoDTO>> getWikiInfo(@Valid @RequestBody AdviceVM adviceVM, HttpServletResponse response) {

        List<WikiInfoDTO> data = adviceService.getWikiInfo(adviceVM.getKeyword());
        return new ResponseEntity<>(data, null, HttpStatus.OK);
    }
    
    @PostMapping("/items")
    @Timed
    public ResponseEntity<List<ItemInfoDTO>> getItemsInfo(@Valid @RequestBody AdviceVM adviceVM, HttpServletResponse response) {

    	List<ItemInfoDTO> data = amazonProductApiService.itemSearch(adviceVM);
        return new ResponseEntity<>(data, null, HttpStatus.OK);
    }
    
}
