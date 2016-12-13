package net.nju.ise.gitmining.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableDetail {
	private List<String> columns;

	public TableDetail() {
		columns = new ArrayList<String>();
	}
	
	public TableDetail(List<String> columns) {
		this.columns = columns;
	}
	public int getColumnIndex(String columnName){
		return columns.indexOf(columnName);
	}
	
	/**
	 * check if a column exists
	 * @param columnName
	 * @return
	 */
	public boolean containsColumn(String columnName){
		return columns.indexOf(columnName) >= 0;
	}
	
	/**
	 * add a new column to TableDetail
	 * @param name
	 * @return
	 */
	public boolean addColumn(String name){
		if(columns.contains(name)){
			return false;
		}else{
			columns.add(name);
			return true;
		}
	}
	
	/**
	 * Generate TableDetail By Array
	 * @param array
	 * @return
	 */
	public static TableDetail listToTableDetail(String[] array){
		List<String> list = Arrays.asList(array);
		List<String> result = new ArrayList<String>();
		result.addAll(list);
		return new TableDetail(result);
	}
	
	public int size(){
		return columns.size();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<columns.size(); i++){
			sb.append(columns.get(i));
			if(i < columns.size() -1){
				sb.append(Constant.COMMA);
			}else{
				sb.append(Constant.NEW_LINE);
			}
		}
		return sb.toString();
	}
	
	
	
}
