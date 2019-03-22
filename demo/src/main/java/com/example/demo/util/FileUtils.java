package com.example.demo.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	
	private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
	
		
	public static void fileUpload(MultipartFile file, String filePath) {
		byte[] bytes;
		try {
			bytes = file.getBytes();
			Path path = Paths.get(filePath);
			Files.write(path, bytes);
			log.info("file upload end!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		} catch (IOException e) {
			log.error("upload file error : ", e);
		}
	}
	
	//response entity 맞춰야 하는 경우 
	public static ResponseEntity<Resource> fileResponse(HttpServletResponse res, File file, String fileName) throws IOException {
	    HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Content-Disposition", "attachment; filename=\""+fileName +"\"");
        headers.add("Expires", "0");
		
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		return ResponseEntity.ok()
					        .headers(headers)
					        .contentLength(file.length())
					        .contentType(MediaType.APPLICATION_OCTET_STREAM)
					        .body(resource);
	}

	//response entity 안맞춰도 되는 경우 
	public static void fileResponseVoid(HttpServletResponse res, File file, String fileName) {
		try (FileInputStream fi = new FileInputStream(file.getAbsolutePath())){
			res.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
			res.setContentType("application/octet-stream");
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, res.getOutputStream());
		}catch (Exception e){
			log.error("download file error : ", e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
