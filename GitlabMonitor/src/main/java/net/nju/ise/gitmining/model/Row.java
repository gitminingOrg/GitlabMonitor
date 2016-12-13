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
		this.values = new ArrayList<Object>();
		this.values.addAll(values);
	}

	public <T> T getIndexValue(int index){
		if(index >= values.size()){
			return null;
		}
		return (T) values.get(index);
	}
	
	public boolean setValue(int index, Object value){
		System.out.println(index + "  " + value);
		if(values.size() < index){
			System.out.println(index + " false " + value  +"  " + values.size());
			return false;
		}else if(values.size() == index){
			System.out.println(index + " add " + value);
			values.add(value);
		}else{
			System.out.println(index + " insert " + value);
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
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<values.size(); i++){
			sb.append(values.get(i));
			if(i < values.size() -1){
				sb.append(Constant.COMMA);
			}else{
				sb.append(Constant.NEW_LINE);
			}
		}
		return sb.toString();
	}
}
