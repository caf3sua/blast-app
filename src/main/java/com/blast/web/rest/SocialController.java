package com.blast.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.support.URIBuilder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import com.blast.domain.SocialUserConnection;
import com.blast.repository.SocialUserConnectionRepository;
import com.blast.service.SocialService;
import com.blast.service.dto.SocialAccountDTO;
import com.blast.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;

@RestController
@RequestMapping("/social")
public class SocialController {

    private final Logger log = LoggerFactory.getLogger(SocialController.class);

    private final SocialService socialService;

    private final ProviderSignInUtils providerSignInUtils;
    
    private final SocialUserConnectionRepository socialUserConnectionRepository;
    
    private final String ENTITY_NAME = "social";

    public SocialController(SocialService socialService, ProviderSignInUtils providerSignInUtils
    		, SocialUserConnectionRepository socialUserConnectionRepository) {
        this.socialService = socialService;
        this.providerSignInUtils = providerSignInUtils;
        this.socialUserConnectionRepository = socialUserConnectionRepository;
    }

    @GetMapping("/signup")
    public RedirectView signUp(WebRequest webRequest, @CookieValue(name = "NG_TRANSLATE_LANG_KEY", required = false, defaultValue = "\"en\"") String langKey) {
        try {
            Connection<?> connection = providerSignInUtils.getConnectionFromSession(webRequest);
            socialService.createSocialUser(connection, langKey.replace("\"", ""));
            return new RedirectView(URIBuilder.fromUri("/#/social-register/" + connection.getKey().getProviderId())
                .queryParam("success", "true")
                .build().toString(), true);
        } catch (Exception e) {
            log.error("Exception creating social user: ", e);
            return new RedirectView(URIBuilder.fromUri("/#/social-register/no-provider")
                .queryParam("success", "false")
                .build().toString(), true);
        }
    }
    
    @PostMapping("/account-info")
    @Timed
    public ResponseEntity<SocialAccountDTO> getSocialAccountInfo(@Valid @RequestBody SocialAccountDTO socialAccountDTO) throws URISyntaxException {
    	SocialAccountDTO data = new SocialAccountDTO();
        log.debug("REST request to getSocialAccountInfo : {}", socialAccountDTO);
        if (StringUtils.isEmpty(socialAccountDTO.getProviderId())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new socialFollow cannot already have an ID")).body(null);
        }
        List<SocialUserConnection> result = socialUserConnectionRepository.findAllByProviderIdAndProviderUserId(socialAccountDTO.getProviderId()
        		, socialAccountDTO.getProviderUserId());
        if (result != null) {
        	SocialUserConnection tmp = result.get(0);
        	data.setDisplayName(tmp.getDisplayName());
        	data.setImageUrl(tmp.getImageURL());
        }
        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, socialAccountDTO.getProviderId()))
                .body(data);
    }
    
    @PostMapping("/check-use-app")
    @Timed
    public ResponseEntity<SocialAccountDTO> checkUseApp(@Valid @RequestBody SocialAccountDTO socialAccountDTO) throws URISyntaxException {
    	SocialAccountDTO data = new SocialAccountDTO();
    	List<String> lstUserUseApp = new ArrayList<>();
    	data.setLstProviderUserId(lstUserUseApp);
    	
        log.debug("REST request to checkUseApp : {}", socialAccountDTO);
        if (StringUtils.isEmpty(socialAccountDTO.getProviderId()) || socialAccountDTO.getLstProviderUserId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new socialFollow cannot already have an ID")).body(null);
        }
        
        for (String pUserId : socialAccountDTO.getLstProviderUserId()) {
        	List<SocialUserConnection> result = socialUserConnectionRepository.findAllByProviderIdAndProviderUserId(socialAccountDTO.getProviderId()
            		, pUserId);
            if (result != null) {
            	lstUserUseApp.add(pUserId);
            }
		}
        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, socialAccountDTO.getProviderId()))
                .body(data);
    }
    
}
