package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.ResponseMap;
import com.example.demo.conf.DefaultParams;
import com.example.demo.service.common.CommonService;
import com.example.demo.util.FileUtils;

/**
 * Created by whydda on 2017-08-10.
 */
@RestController
public class CommonController {

	private static final Logger log = LoggerFactory.getLogger(CommonController.class);
	
    private CommonService commonService;
    private ResponseMap responseMap;

    @Autowired
    public CommonController(CommonService commonService, ResponseMap responseMap){
        this.commonService = commonService;
        this.responseMap = responseMap;
    }

    @GetMapping(value="/")
    public Map<String, Object> getUser(ModelMap model) throws Exception {
        List<Map<String, Object>> resultList = commonService.selectTest();
        model.addAttribute("resultList", resultList);
        return responseMap.ok(model);
    }
    
    @GetMapping(value="/test")
    public ResponseEntity<List<Map<String,Object>>> findAll() {
    	List<Map<String, Object>>  boards = commonService.selectTest();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }
    
    @GetMapping(value="/hello")
    public ResponseEntity<Map<String,String>> hello(@RequestBody Map<String,String> map) {
    	System.out.println(map+" < <<<<<<map");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    @GetMapping(value="/hello2")
    public ResponseEntity<Map<String,Object>> hello2(DefaultParams params) {
    	System.out.println(params.getMap()+" < <<<<<<defaultparams Map<");
        return new ResponseEntity<>(params.getMap(), HttpStatus.OK);
    }
    
    @PostMapping(value="/hello3")
    public ResponseEntity<Map<String, Object>> hello3(DefaultParams params) {
    	System.out.println(params.getMap()+" < <<<<<<defaultparams Map<");
        return new ResponseEntity<>(params.getMap(), HttpStatus.OK);
    }
    
    
//    @GetMapping(value="/excel")//테스트 완료 
//    public ResponseEntity<Resource> getExcel(HttpServletResponse res) throws Exception {
//    	System.out.println("들어옴!!!!!!!!!!!!!!!");
//    	return FileUtils.fileResponse(res, new File(filepath), "test.zip");
////    	return FileUtils.fileResponse(res, new File(filepath), "test.zip");
//    }
    
    @GetMapping(value="/excel")//테스트 완료 
    public void getExcelTest(HttpServletResponse res, String type) throws Exception {
    	String filepath = "C:";
    	String gogo = "test.xls";
    	if("zip".equals(type)) {
    		gogo = "test.zip";	
    	}else if("txt".equals(type)){
    		gogo = "test.txt";
    	}else if("pdf".equals(type)){
    		gogo = "test.pdf";
    	}
    	System.out.println("들어옴!!!!!!!!!!!!!!!");
    	FileUtils.fileResponseVoid(res, new File(filepath+gogo), gogo);
    }
    
    
    //예시
    @SuppressWarnings("unchecked")
	@PostMapping(value="/up")//테스트 완료 
    public void upTest(DefaultParams params) throws Exception {
		List<Map<String,Object>> filelist =  (List<Map<String, Object>>) params.getObj("fileList");
    	filelist.stream()
    			.filter(m->m.containsKey("file"))
    			.map(m-> (List<MultipartFile>) m.get("file"))
    			.forEach( list->list.parallelStream().forEach(file->{ //파일 작성 하는 부분만 기본적인 수준의 비동기로 처리 하도록 함
					FileUtils.fileUpload(file, "C:\\dev\\shilla\\excel\\"+file.getOriginalFilename());
    			}))
				;
    }
    
    
}
