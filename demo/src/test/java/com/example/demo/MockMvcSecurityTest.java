package com.example.demo;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.common.ResponseMap;
import com.example.demo.controller.CommonController;
import com.example.demo.service.common.CommonService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(CommonController.class)
public class MockMvcSecurityTest {

	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	CommonService commonService; 
	
	@MockBean
	ResponseMap responseMap;
	
//	String abc = "hello world foo";
    ObjectMapper objectMapper = new ObjectMapper();
	
	 
	@Test
	public void getAllEmployeesAPI() throws Exception
	{

		String cjson = objectMapper.writeValueAsString(new HashMap<String,Object>() {{put("c","c1"); put("d","d1");}});
        Map<String,String> map = new HashMap<>();
        map.put("a", "a1"); map.put("b", "b1");map.put("c", cjson);
        String json = objectMapper.writeValueAsString(map);
	    
//      when(commonService.foo()).thenReturn(json);
      
	    //결과 적으로  urlencoded 하면 parameter 라인에 집어 넣음
	    //application/json 하면 body에 적어 넣음
	  
//        type test
//        json(json);
//        urlencode();
        multipart();
        
	}
	
	public void multipart() throws Exception {
		String json = objectMapper.writeValueAsString(new HashMap<String,Object>() {{put("c","c1"); put("d","d1");}});
		
		  MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
	      MockMultipartFile secondFile = new MockMultipartFile("data", "other-file-name.data", "text/plain", "some other type".getBytes());
	      MockMultipartFile file3 = new MockMultipartFile("data", "other-file-name.data123", "text/plain", "some other type".getBytes());
	      MockMultipartFile file4 = new MockMultipartFile("data", "filename.txt123", "text/plain", "some xml".getBytes());
	      MockMultipartFile file5 = new MockMultipartFile("data55", "filename1.txt", "text/plain", "some xml".getBytes());
	      MockMultipartFile file6 = new MockMultipartFile("data56", "filename6.txt", "text/plain", "some xml".getBytes());
//	        MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", "{\"json\": \"someValue\"}".getBytes());

//	        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	        mvc.perform(MockMvcRequestBuilders.multipart("/up")
	                        .file(firstFile)
	                        .file(secondFile)
	                        .file(file3)
	                        .file(file4)
	                        .file(file5).file(file6)
//	                        .file(jsonFile)
//	                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//	                        .param("a", "a1").param("b1", "b1").param("c", json)
	                        .content(json)
	                       )
	                    .andExpect(status().isOk());
//	                    .andExpect(content().string("success"));
	}
	
	public void json(String json) throws Exception {
		
		mvc.perform(MockMvcRequestBuilders
			      .get("/hello2")
			      .contentType(MediaType.APPLICATION_JSON_UTF8)
			      .content(json)
			      .characterEncoding("utf-8"))
			      .andExpect(status().isOk()) 
			      .andExpect(content().string(containsString(json)))
			      ;
	}
	
	public void urlencode() throws Exception {
		
		String json = objectMapper.writeValueAsString(new HashMap<String,Object>() {{put("c","c1"); put("d","d1");}});
		
		mvc.perform(MockMvcRequestBuilders
			      .get("/hello2")
			      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
			      .param("a", "a1").param("b1", "b1").param("c", json)
			      .characterEncoding("utf-8"))
			      .andExpect(status().isOk())
			      ;
	}
	 
	
	
	
}
