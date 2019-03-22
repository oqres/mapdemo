package com.example.demo.service.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.service.mapper.CommonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by whydda on 2017-08-03.
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonMapper commonMapper;

    @Transactional(readOnly = true)
    public List<Map<String, Object>> selectTest() {
        return commonMapper.selectTest();
    }

	@Override
	public String foo() {
       String abc = "hello world foo";
		return abc;
	}
}
