package com.stylefeng.guns.rest.modular.film.util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * Created by cute coder
 * 2019/5/27 15:07
 */
public class Json2Map {
    public static Map<String, Object> json2Map(String jsonStr) {
      Map<String, Object> map = new HashMap<>();
      if(jsonStr != null && !"".equals(jsonStr)){
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for (Object k : json.keySet()) {
          Object v = json.get(k);
          //如果内层还是数组的话，继续解析
          if (v instanceof JSONArray) {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Iterator<JSONObject> it = ((JSONArray) v).iterator();
            while (it.hasNext()) {
              JSONObject json2 = it.next();
              list.add(json2Map(json2.toString()));
            }
            map.put(k.toString(), list);
          } else {
            map.put(k.toString(), v);
          }
        }
        return map;
      }else{
        return null;
      }
    }
}
