package net.nju.ise.gitmining.enricher;

import net.nju.ise.gitmining.model.Table;

public class StdDeviationEnricher implements Enricher {

	/**
	 * 计算软工1、2、3某一门课的小组标准差，并添加到TableDetail中去
	 *@author Magister_Liu
	 * @param origin 原始表单
	 * @param objects 输入1、2、3代表需要计算的某一门课*/
	@Override
	public Table enrich(Table origin,Object...objects) {
		//add new column;
		String columnName = "StdDev_SE"+objects[0].toString();
		origin.addNewColumn(columnName);
		int index = origin.getTableDetail().getColumnIndex(columnName);
		
		//calculate standard deviation
		double [] score = new double[4];//小组成绩数组
		Double stdDev = null;
		int number = 0;//小组人数
		int scoreIdx = origin.getTableDetail().getColumnIndex("SE"+ objects[0].toString() + "Grade");
		//初始化软工三分组，抱歉，我实在不知道怎么搞看起来比较好看了T_T
		String groupId = origin.getRows().get(1).getIndexValue(0).toString();
		
		//添加“标准差”列
		for(int i = 1; i < origin.getRows().size(); i++){
			if(groupId.equals(origin.getRows().get(i).getIndexValue(0).toString())){
				
				while(groupId.equals(origin.getRows().get(i+number).getIndexValue(0).toString())){
					score[number] = Double.parseDouble(origin.getRows().get(i+number).getIndexValue(scoreIdx).toString());
					number++;
					if(i+number>=origin.getRows().size()){	
						number--;
						break;
					}
				}
				
				//获取成绩标准差
				stdDev = getStdDev(score,number);
				groupId = origin.getRows().get(i+number).getIndexValue(0).toString();
				number = 0;
			}
			origin.getRows().get(i).setValue(index,stdDev);
			
		}
		return origin;
	}
	
	
	//计算成绩标准差
	private double getStdDev(double [] score, int num){
		double std = 0;
		double mean = 0;//平均成绩
		if(score.length > 0 && num > 0){
			for(int i = 0; i < score.length; i++){
				mean += score[i];
			}
			mean /= num;
			
			for(int i = 0; i< score.length; i++){
				std = Math.sqrt((score[i] - mean) * (score[i] - mean));
			}
			std /= (num-1);
		}
		return std;
		
	}
}