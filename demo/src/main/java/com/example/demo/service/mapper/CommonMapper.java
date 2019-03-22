package com.example.demo.service.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("commonMapper")
public interface CommonMapper {
	List<Map<String, Object>> selectTest();
}
