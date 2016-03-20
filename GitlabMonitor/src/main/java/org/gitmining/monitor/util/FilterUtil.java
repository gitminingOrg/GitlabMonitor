package org.gitmining.monitor.util;

import java.util.HashMap;
import java.util.Map;

public class FilterUtil {
	public static Map<String, Integer> parseFilter(String line){
		if(line == null){
			return null;
		}
		line = line.replaceAll(" ", "");
		Map<String, Integer> filter = new HashMap<String, Integer>();
		try{
			String[] items = line.split(";");
			for (String item : items) {
				String compare;
				if((compare = "commit_count<") != null && item.startsWith(compare)){
					int value = Integer.parseInt(item.substring(compare.length()));
					filter.put(compare, value);
				}else if((compare = "commit_count>") != null && item.startsWith(compare)){
					int value = Integer.parseInt(item.substring(compare.length()));
					filter.put(compare, value);
				}else if((compare = "add_line<") != null && item.startsWith(compare)){
					int value = Integer.parseInt(item.substring(compare.length()));
					filter.put(compare, value);
				}else if((compare = "add_line>") != null && item.startsWith(compare)){
					int value = Integer.parseInt(item.substring(compare.length()));
					filter.put(compare, value);
				}else if((compare = "delete_line<") != null && item.startsWith(compare)){
					int value = Integer.parseInt(item.substring(compare.length()));
					filter.put(compare, value);
				}else if((compare = "delete_line>") != null && item.startsWith(compare)){
					int value = Integer.parseInt(item.substring(compare.length()));
					filter.put(compare, value);
				}else if((compare = "java_file<") != null && item.startsWith(compare)){
					int value = Integer.parseInt(item.substring(compare.length()));
					filter.put(compare, value);
				}else if((compare = "java_file>") != null && item.startsWith(compare)){
					int value = Integer.parseInt(item.substring(compare.length()));
					filter.put(compare, value);
				}else if((compare = "total_add<") != null && item.startsWith(compare)){
					int value = Integer.parseInt(item.substring(compare.length()));
					filter.put(compare, value);
				}else if((compare = "total_add>") != null && item.startsWith(compare)){
					int value = Integer.parseInt(item.substring(compare.length()));
					filter.put(compare, value);
				}else if((compare = "total_delete<") != null && item.startsWith(compare)){
					int value = Integer.parseInt(item.substring(compare.length()));
					filter.put(compare, value);
				}else if((compare = "total_delete>") != null && item.startsWith(compare)){
					int value = Integer.parseInt(item.substring(compare.length()));
					filter.put(compare, value);
				}
			}			
		}catch(Exception e){
			return null;
		}
		return filter;
	}
}
