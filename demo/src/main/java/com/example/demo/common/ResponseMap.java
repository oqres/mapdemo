package com.example.demo.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Component
public class ResponseMap{
	
	private ObjectMapper objectMapper;
	
	@Autowired
	public ResponseMap(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
    /**
     * 200
     * @return
     */
    public Map<String, Object> ok() throws Exception{
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("code", HttpStatus.OK.value()); resMap.put("message", "정상처리 되었습니다.");
        return responseMap(resMap);
    }
    
    /**
     * 200
     * @param message
     * @return
     */
    public Map<String, Object> ok(String message) throws Exception {
    	Map<String,Object> resMap = new HashMap<>();
        resMap.put("code", HttpStatus.OK.value());  resMap.put("message", message);
        return responseMap(resMap);
    }

    /**
     * 200
     * @param dataMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> ok(Map<String, Object> dataMap) throws Exception{
    	Map<String,Object> resMap = new HashMap<>();
        resMap.put("code", HttpStatus.OK.value());  resMap.put("message", "정상처리 되었습니다.");
        resMap.putAll(dataMap);
        return responseMap(resMap);
    }

    /**
     * 200
     * @param message
     * @param dataMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> ok(String message, Map<String, Object> dataMap) throws Exception{
    	Map<String,Object> resMap = new HashMap<>();
        resMap.put("code", HttpStatus.OK.value());  resMap.put("message", message);
        resMap.putAll(dataMap);
        return responseMap(resMap);
    }


    /**
     * 401
     * @return
     */
    public Map<String, Object> unauthorized() throws Exception{
    	Map<String,Object> resMap = new HashMap<>();
        resMap.put("code", HttpStatus.UNAUTHORIZED.value());  resMap.put("message", "잘못된 경로로 접근하였습니다. ");
        return responseMap(resMap);
    }

    /**
     * 401
     * @param message
     * @return
     */
    public Map<String, Object> unauthorized(String message) throws Exception{
    	Map<String,Object> resMap = new HashMap<>();
        resMap.put("code", HttpStatus.UNAUTHORIZED.value()); resMap.put("message", message);
        return responseMap(resMap);
    }

    /**
     * 401
     * @param message
     * @return
     */
    public Map<String, Object> unauthorized(int code, String message) throws Exception{
    	Map<String,Object> resMap = new HashMap<>();
        resMap.put("code", code);
        resMap.put("message", message);
        return responseMap(resMap);
    }

    /**
     * 500
     * @return
     */
    public Map<String, Object> internalServerError() throws Exception{
    	Map<String,Object> resMap = new HashMap<>();
        resMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        resMap.put("message", "서버에서 에러가 발생하였습니다.</br>관리자에게 문의하여 주십시오.");
        return responseMap(resMap);
    }

    /**
     * 500
     * @param code
     * @param message
     * @return
     */
    public Map<String, Object> internalServerError(int code, String message) throws Exception{
    	Map<String,Object> resMap = new HashMap<>();
        resMap.put("code", code);
        resMap.put("message", message);
        return responseMap(resMap);
    }
    
    
    @SuppressWarnings("unchecked")
	private Map<String,Object> responseMap(Map<String,Object> resMap) throws IOException{
      String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resMap);
      return this.objectMapper.readValue(json, HashMap.class);
    }
    

}
