package com.example.demo.conf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultParamsArgumentResolver implements HandlerMethodArgumentResolver {
	
    private static final int LOG_VALUE_LIMIT = 20;

    private ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(DefaultParamsArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
    	return DefaultParams.class.isAssignableFrom(methodParameter.getParameterType()); 
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
    	if(!methodParameter.getParameterType().equals(DefaultParams.class)) {return null;}
   
//        Iterator iterator = nativeWebRequest.getParameterNames();
        //key value 핸들링 해야됨 reader 쪽에서 
    	HttpServletRequest req = (HttpServletRequest) nativeWebRequest.getNativeRequest();
    	log.info("resolveArgument들어옴!!!!!!!!!!!!!!! : {}" , req.getContentType());
    	DefaultParams defaultParams = new DefaultParams();
        defaultParams.setMap(new HashMap<>());
    	
    	//기본 json text 형식 param
    	if(req.getContentType().equals(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
    		setDefaultParamUrlencode(nativeWebRequest, defaultParams);
    		
    	}else if(req.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
    		String json =  req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    		setDefaultParamJson(json, defaultParams);
    		
    	}else if(req.getContentType().equals(MediaType.MULTIPART_FORM_DATA_VALUE)){
    		MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) nativeWebRequest.getNativeRequest();
    	
    		//multipart의 경우 어떤 타입의 데이터가 들어 올지 모르므로 json parsing 결과를 가지고 추론한다
		   String json =  req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
           if(json!=null) {
        	   setDefaultParamJson(json, defaultParams);
           }else {
        	   setDefaultParamUrlencode(nativeWebRequest, defaultParams);
           }
           
           //multipart 부분 setting
           setDefaultParamMultipart(defaultParams, multipart);
       }
    	
	   //로그 관련한 사항 필요 하면 여기작성 하면 됨
    	
       return defaultParams;
    }

	private void setDefaultParamMultipart(DefaultParams defaultParams, MultipartHttpServletRequest multipart) {
		List<Map<String, Object>> uploadFileMap = new ArrayList<>();
           for(String name : multipart.getFileMap().keySet()){
               Map<String, Object> map = new HashMap<>();
               map.put("key", name); map.put("file", multipart.getFiles(name));
               uploadFileMap.add(map);
           }
        defaultParams.put("fileList", uploadFileMap);
	}
    
    

	@SuppressWarnings("unchecked")
	private void setDefaultParamJson(String json, DefaultParams defaultParams) throws IOException, JsonParseException, JsonMappingException, JSONException {
		Map<String, String> jsonMap = objectMapper.readValue(json, Map.class);
		for (String mapKey : jsonMap.keySet()) {
			setDefaultParamByType(defaultParams, mapKey, jsonMap.get(mapKey));
		}
	}

	private void setDefaultParamUrlencode(NativeWebRequest nativeWebRequest, DefaultParams defaultParams) throws JsonParseException, JsonMappingException, JSONException, IOException {
		Iterator<String> iterator = nativeWebRequest.getParameterNames();
		while(iterator.hasNext()) {
			String key = iterator.next();
			String value = nativeWebRequest.getParameter(key);
			setDefaultParamByType(defaultParams, key, value);
		}
	}
    
    
    private void setDefaultParamByType(DefaultParams defaultParams, String key, String value) throws JSONException, JsonParseException, JsonMappingException, IOException {
		Object json = new JSONTokener(value).nextValue();
        if(json instanceof JSONObject){
            defaultParams.put((String) key, objectMapper.readValue(value, Map.class)); //이거 물어 보기  
        }else if(json instanceof JSONArray){
            defaultParams.put(key, objectMapper.readValue(value, List.class));
        }else if(json instanceof String){//String 인 경우임
//        	defaultParams.put(key, XssPreventer.escape(String.valueOf(value)));
            defaultParams.put(key, value);
        }else {
        	log.error("알수 없는 json type : {}", value);
        }
    }
    
    
    
    //로그 관련 코드 
//    if(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
//        SecureUser secureUser = ((SecureUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSecureUser();
//
//        //로그를 쌓는다.
//        HttpServletRequest request = (HttpServletRequest)nativeWebRequest.getNativeRequest();
//
//        //HttpSession 객체 가져오기
//        HttpSession session = request.getSession();
//
//        //ServletContext 객체 가져오기
//        ServletContext conext = session.getServletContext();
//
//        //Spring Context 가져오기
//        WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(conext);
//
//        //스프링 빈 가져오기 & casting
//        CommonService commonService= (CommonService)wContext.getBean("commonService");
//
//        String tempIp = request.getHeader("X-FORWARDED-FOR");
//        String ip = "";
//        if (tempIp == null) {
//            ip = request.getRemoteAddr();
//        }else{
//            String[] ipArr = tempIp.replaceAll("\\s", "").split(",");
//            ip = ipArr[0];
//        }
//        //너무 긴 value가 들어오는 경우 처리한다.
//        String json = objectMapper.writeValueAsString(defaultParams.getMap());
//        Map<String, Object> logMap =  objectMapper.readValue(json, new TypeReference<Map<String, Object>>(){});
//
//        logMap.forEach((k, v) ->
//                {
//                    String tempValue = "";
//                    if(String.valueOf(logMap.get(k)).length() > LOG_VALUE_LIMIT){
//                        tempValue = String.valueOf(logMap.get(k));
//                        tempValue = tempValue.substring(0, LOG_VALUE_LIMIT);
//                        logMap.put(k, tempValue);
//                    }
//                }
//        )
//        ;
//
//        String jsonParam = objectMapper.writeValueAsString(logMap);
//        logMap.put("REQ_IP",ip);
//        logMap.put("REQ_ID",secureUser.getUSER_ID());
//        logMap.put("REQ_URI",request.getRequestURI());
//        logMap.put("REQ_PARAM",jsonParam);
//        logMap.put("RES_DATA","");
//        commonService.insertReqLog(logMap);
//
//        if (!String.valueOf(secureUser.getUSER_ID()).equals("null")) {
//            defaultParams.put("USER_ID", secureUser.getUSER_ID());
//            defaultParams.put("AUTH_CD", secureUser.getAUTH_CD());
//            defaultParams.put("USER_NAME", secureUser.getUSER_NAME());
//            defaultParams.put("DEPT_NAME", secureUser.getDEPT_NAME());
//        }
//    }
    


}
