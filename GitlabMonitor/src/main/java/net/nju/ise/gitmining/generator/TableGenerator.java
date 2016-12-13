package net.nju.ise.gitmining.generator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import net.nju.ise.gitmining.model.Row;
import net.nju.ise.gitmining.model.Table;
import net.nju.ise.gitmining.model.TableDetail;

public class TableGenerator {
	public static Table csvToTable(String csvFilePath){
		Table table = new Table();
		//adding csv content to table
		try(BufferedReader br = new BufferedReader(new FileReader(csvFilePath))){
			int index = 0;
			String line ="";
			while((line = br.readLine()) != null){
				String[] items = line.split(",");
				
				if(index == 0){
					TableDetail detail = TableDetail.listToTableDetail(items);
					table.setTableDetail(detail);
				}else{
					Row row = Row.arrayToRow(items);
					table.addRow(row);
				}
				
				index++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return table;
	}
	
	
	public void TableToCSV(String csvFilePath, Table table){
		
	}
	
	public void TableToARFF(String arffFilePath, Table table){
		
	}
}
