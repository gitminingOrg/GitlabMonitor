package net.nju.ise.gitmining.enricher;

import java.util.List;
import java.util.Map;

import net.nju.ise.gitmining.model.Table;

public class MaxOrMinEnricher implements Enricher {

	@Override
	public Table enrich(Table origin, Object... params) {
		// TODO Auto-generated method stub
		String columnName = (String)params[0];
		String type = (String)params[1];
		
		origin.addNewColumn(columnName + type);
		if(type.equals("Max")){
			Map<String, List<Integer>> map = origin.columnToMap("team");
			
			for(String key : map.keySet()){
				double maxScore = 0;
				for(int i = 0 ; i < map.get(key).size() ; i ++){
					if(Double.parseDouble((String)origin.getRows().get(map.get(key).get(i)).getIndexValue(origin.getTableDetail().getColumnIndex(columnName))) > maxScore){
						maxScore = Double.parseDouble((String)origin.getRows().get(map.get(key).get(i)).getIndexValue(origin.getTableDetail().getColumnIndex(columnName)));
					}
				}
				
				for(int i = 0 ; i < map.get(key).size() ; i ++){
					origin.getRows().get(map.get(key).get(i)).setValue(origin.getTableDetail().getColumnIndex(columnName + type), maxScore);
				}
			}
		}else{
			Map<String, List<Integer>> map = origin.columnToMap("team");
			
			for(String key : map.keySet()){
				double minScore = Double.MAX_VALUE;
				for(int i = 0 ; i < map.get(key).size() ; i ++){
					if(Double.parseDouble((String)origin.getRows().get(map.get(key).get(i)).getIndexValue(origin.getTableDetail().getColumnIndex(columnName))) < minScore){
						minScore = Double.parseDouble((String)origin.getRows().get(map.get(key).get(i)).getIndexValue(origin.getTableDetail().getColumnIndex(columnName)));
					}
				}
				
				for(int i = 0 ; i < map.get(key).size() ; i ++){
					origin.getRows().get(map.get(key).get(i)).setValue(origin.getTableDetail().getColumnIndex(columnName + type), minScore);
				}
			}
		}
		return origin;
	}

}