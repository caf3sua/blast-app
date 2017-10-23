package com.blast.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blast.service.dto.WikiInfoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AdviceService {

    private final Logger log = LoggerFactory.getLogger(AdviceService.class);

    @PostConstruct
    private void init() {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public AdviceService() {
    }

    public List<WikiInfoDTO> getWikiInfo(String keyword) {
    	List<WikiInfoDTO> result = new ArrayList<>();

    	if (StringUtils.isNotEmpty(keyword)) {
    		String[] keywords = keyword.split(",");
    		for (String key : keywords) {
    			List<WikiInfoDTO> data = getWikiInfoByKeyword(key);
    			result.addAll(data);
			}
    	}
    	
    	return result;
    } 
    
    private List<WikiInfoDTO> getWikiInfoByKeyword(String keyword) {
    	List<WikiInfoDTO> result = new ArrayList<>();
    	RestTemplate rest = new RestTemplate();
    	
    	String url = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts|info&exintro&titles=" + keyword
    			+ "&format=json&explaintext&redirects&inprop=url&indexpageids";
    	String data = rest.getForObject(url, String.class);
    	
    	JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(data);
            //String pages = (String) jsonObj.get("pages");
            JSONObject query = (JSONObject) jsonObj.get("query");
            
            JSONObject pages = (JSONObject) query.get("pages");
            
            Iterator<String> keysItr = pages.keys();
            while(keysItr.hasNext()) {
            	WikiInfoDTO item = new WikiInfoDTO();
            	
                String key = keysItr.next();
                Object value = pages.get(key);

                if(value instanceof JSONArray) {
                    //value = toList((JSONArray) value);
                }

                else if(value instanceof JSONObject) {
                	JSONObject tmp = (JSONObject) value;
                	item.setCanonicalurl((String)tmp.get("canonicalurl"));
                	item.setContentmodel((String)tmp.get("contentmodel"));
                	item.setExtract((String)tmp.get("extract"));
                	item.setFullurl((String)tmp.get("fullurl"));
                	item.setPageId(Long.parseLong(key));
                	item.setTitle((String)tmp.get("title"));
                	result.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    	
    	return result;
    }

}
