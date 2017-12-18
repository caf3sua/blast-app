package com.blast.web.rest;

import com.blast.domain.Authority;
import com.blast.domain.SocialUserConnection;
import com.blast.domain.User;
import com.blast.repository.AuthorityRepository;
import com.blast.repository.SocialUserConnectionRepository;
import com.blast.repository.UserRepository;
import com.blast.security.jwt.JWTConfigurer;
import com.blast.security.jwt.TokenProvider;
import com.blast.web.rest.vm.LoginVM;
import com.blast.web.rest.vm.SocialVM;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;
    
    private final SocialUserConnectionRepository socialUserConnectionRepository;

    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthorityRepository authorityRepository;
    
    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager
    		, SocialUserConnectionRepository socialUserConnectionRepository
    		, UserRepository userRepository
    		, PasswordEncoder passwordEncoder
    		, AuthorityRepository authorityRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.socialUserConnectionRepository = socialUserConnectionRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity authorize(@Valid @RequestBody LoginVM loginVM, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
            String jwt = tokenProvider.createToken(authentication, rememberMe);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return ResponseEntity.ok(new JWTToken(jwt));
        } catch (AuthenticationException ae) {
            log.trace("Authentication exception trace: {}", ae);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/social-auth")
    @Timed
    public ResponseEntity socialAuthorize(@RequestBody SocialVM loginVM, HttpServletResponse response) {
    	boolean isNew = false;
    	
    	// Check social by email
    	// facebook, 1793854933961610
    	List<SocialUserConnection> lstConnection = socialUserConnectionRepository.findAllByProviderIdAndProviderUserId(loginVM.getProviderId(), loginVM.getProviderUserId());
    	
    	// Register if have no one
    	if (lstConnection == null || lstConnection.size() == 0) {
    		isNew = true;
    		// Register
    		createSocialUser(loginVM);
    	} else {
    		// Update
    		for (SocialUserConnection con : lstConnection) {
    			con.setDisplayName(loginVM.getDisplayName());
    			con.setAccessToken(loginVM.getAccessToken());
    			con.setImageURL(loginVM.getImageUrl());
    			con.setProfileURL(loginVM.getProfileUrl());
    			socialUserConnectionRepository.save(con);
    		}
    	}
    	
    	// Get user
    	// Login
        UsernamePasswordAuthenticationToken authenticationToken = 
            new UsernamePasswordAuthenticationToken(loginVM.getEmail(), "12345678");

        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = true;
            String jwt = tokenProvider.createToken(authentication, rememberMe);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return ResponseEntity.ok(new JWTToken(jwt, isNew));
        } catch (AuthenticationException ae) {
            log.trace("Authentication exception trace: {}", ae);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
    
    private void updateSocialUser(SocialVM loginVM) {
	    	SocialUserConnection sUser = new SocialUserConnection();
	    	sUser.setUserId(loginVM.getEmail());
	    	sUser.setProviderId(loginVM.getProviderId());
	    	sUser.setProviderUserId(loginVM.getProviderUserId());
	    	sUser.setDisplayName(loginVM.getDisplayName());
	    	sUser.setProfileURL(loginVM.getProfileUrl());
	    	sUser.setImageURL(loginVM.getImageUrl());
	    	sUser.setAccessToken(loginVM.getAccessToken());
	    	sUser.setRank(1l);
	    	socialUserConnectionRepository.save(sUser);
		
		
	    String encryptedPassword = passwordEncoder.encode("12345678");
	    Set<Authority> authorities = new HashSet<>(1);
	    authorities.add(authorityRepository.findOne("ROLE_USER"));
	
	    User newUser = new User();
	    newUser.setLogin(loginVM.getEmail());
	    newUser.setPassword(encryptedPassword);
	    newUser.setEmail(loginVM.getEmail());
	    newUser.setActivated(true);
	    newUser.setAuthorities(authorities);
	    newUser.setLangKey("en");
	    newUser.setImageUrl(loginVM.getImageUrl());
	
	    userRepository.save(newUser);
	}
	    
    private void createSocialUser(SocialVM loginVM) {
	    	SocialUserConnection sUser = new SocialUserConnection();
	    	sUser.setUserId(loginVM.getEmail());
	    	sUser.setProviderId(loginVM.getProviderId());
	    	sUser.setProviderUserId(loginVM.getProviderUserId());
	    	sUser.setDisplayName(loginVM.getDisplayName());
	    	sUser.setProfileURL(loginVM.getProfileUrl());
	    	sUser.setImageURL(loginVM.getImageUrl());
	    	sUser.setAccessToken(loginVM.getAccessToken());
	    	sUser.setRank(1l);
	    	socialUserConnectionRepository.save(sUser);
		
    	
        String encryptedPassword = passwordEncoder.encode("12345678");
        Set<Authority> authorities = new HashSet<>(1);
        authorities.add(authorityRepository.findOne("ROLE_USER"));

        User newUser = new User();
        newUser.setLogin(loginVM.getEmail());
        newUser.setPassword(encryptedPassword);
        newUser.setEmail(loginVM.getEmail());
        newUser.setActivated(true);
        newUser.setAuthorities(authorities);
        newUser.setLangKey("en");
        newUser.setImageUrl(loginVM.getImageUrl());

        userRepository.save(newUser);
    }
    
    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;
        
        private boolean isNew;

        JWTToken(String idToken, boolean isNew) {
            this.idToken = idToken;
            this.isNew = isNew;
        }
        
        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
        
        @JsonProperty("is_new")
        boolean getIsNew() {
            return isNew;
        }

        void setIsNew(boolean isNew) {
            this.isNew = isNew;
        }
    }
}

