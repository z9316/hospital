package com.example.demo.search.service;

import java.util.*;

public interface HerbSearchService {
	
	public String createIndex();
	
	public Map<String,Object> searchAll(Map<String,Object> map);
	
	public Map<String,Object> searchByQuery(Map<String,Object> map);
	
	public String updateIndex();
	
	public String deleteIndex();

}
