package net.nju.ise.gitmining.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row {
	private List<Object> values;
	
	public Row(){
		values = new ArrayList<Object>();
	}
	public Row(List<Object> values) {
		super();
		this.values = values;
	}

	public <T> T getIndexValue(int index){
		if(index >= values.size()){
			return null;
		}
		return (T) values.get(index);
	}
	
	public boolean setValue(int index, Object value){
		if(values.size() <= index){
			return false;
		}else if(values.size() == index){
			values.add(value);
		}else{
			values.set(index, value);
		}
		return true;		
	}
	
	public int size(){
		return values.size();
	}
	
	public static Row arrayToRow(Object[] array){
		List<Object> list = Arrays.asList(array);
		List<Object> result = new ArrayList<Object>();
		result.addAll(list);
		return new Row(result);
	}
}
