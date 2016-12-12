package net.nju.ise.gitmining.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
	private TableDetail tableDetail;
	private List<Row> rows;
	
	public Table(){
		tableDetail = new TableDetail();
		rows = new ArrayList<Row>();
	}
	
	public Table(TableDetail tableDetail){
		this.tableDetail = tableDetail;
		rows = new ArrayList<Row>();
	}
	
	/***
	 * group the rows according a specific row
	 * @param column
	 * @return
	 */
	public <T> Map<T, List<Integer>> columnToMap(String column){
		Map<T, List<Integer>> mapping = new HashMap<T, List<Integer>>();
		int index = tableDetail.getColumnIndex(column);
		if(index < 0){
			return mapping;
		}
		for(int i=0; i<rows.size(); i++){
			T key = rows.get(i).getIndexValue(index);
			if(!mapping.containsKey(key)){
				mapping.put(key, new ArrayList<Integer>());
			}
			
			List<Integer> indexes = mapping.get(key);
			indexes.add(i);
		}
		
		return mapping;
	}
	
	/**
	 * add a data row
	 * @param row
	 * @return
	 */
	public boolean addRow(Row row){
		if(row.size() != tableDetail.size()){
			return false;
		}else{
			rows.add(row);
			return true;
		}
	}
	
	public boolean addNewColumn(String column){
		return tableDetail.addColumn(column);
	}
	
	

	public TableDetail getTableDetail() {
		return tableDetail;
	}

	public void setTableDetail(TableDetail tableDetail) {
		this.tableDetail = tableDetail;
	}

	
	
	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
	
	
}
