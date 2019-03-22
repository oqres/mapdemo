package com.example.demo.conf;

import java.util.Map;

/**
 * Created by whydda on 2017-03-03.
 */
public class DefaultParams {
    private Map<String, Object> map;

    public Map getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public void put(String key, Object value){
        this.map.put(key, value);
    }

    public Object getObj(String key){
        return this.map.get(key);
    }
}