package net.nju.ise.gitmining.enricher;

import net.nju.ise.gitmining.model.Table;

public class RankingAnalysisEnricher implements Enricher {

	@Override
	public Table enrich(Table origin, Object... objects) {
		if(!((origin.getTableDetail().containsColumn("rankSE1"))
				&&(origin.getTableDetail().containsColumn("rankSE2"))
				&&(origin.getTableDetail().containsColumn("rankSE3"))))
		{
			System.out.println("两项都没有就想玩！！！");
			return null;
		}
		
		String colName = "MeanRankingSE2_3";
		String colAnalysis = "coefficient";
		origin.addNewColumn(colName);
		origin.addNewColumn(colAnalysis);
		int meanindex = origin.getTableDetail().getColumnIndex(colName);
		int analysisidx= origin.getTableDetail().getColumnIndex(colAnalysis);
		int rank2idx = origin.getTableDetail().getColumnIndex("rankSE2");
		int rank3idx = origin.getTableDetail().getColumnIndex("rankSE3");
		
		setMeanRankSE2(origin,rank2idx,meanindex);
		
		rankingChangeAnalyze(origin,rank2idx,rank3idx,analysisidx);
		
		return origin;
	}
	
	/**
	 * 添加SE2课程小组平均排名，用以未来分析
	 * @param origin 原始表单
	 * @param rankidx 
	 * @param index 输出列 */
	private void setMeanRankSE2(Table origin, int rankidx, int index){
		int number = 0;
		double meanRank = 0;
		String groupId = origin.getRows().get(1).getIndexValue(rankidx).toString();
		
		for(int i = 1; i < origin.getRows().size(); i++){
			if(groupId.equals(origin.getRows().get(i).getIndexValue(rankidx).toString())){
				
				while(groupId.equals(origin.getRows().get(i+number).getIndexValue(rankidx).toString())){
					meanRank += Double.parseDouble(origin.getRows().get(i).getIndexValue(rankidx).toString());
					number++;
					if(i+number>=origin.getRows().size()){	
						number--;
						break;
					}
				}
				meanRank /= (number+1);
				number = 0;
			}
			origin.getRows().get(i).setValue(index, new Double(meanRank));
			
		}
	}

	private void rankingChangeAnalyze(Table origin, int rank2idx, int rank3idx, int index){
		int number = 0;
		int maxRank;
		int minRank;
		int range = 0;
		int cumchange = 0;
		Double cfft = null;
		String groupId = origin.getRows().get(1).getIndexValue(0).toString();
		
		for(int i = 1; i < origin.getRows().size(); i++){
			if(groupId.equals(origin.getRows().get(i).getIndexValue(0).toString())){
				maxRank = Integer.parseInt(origin.getRows().get(i).getIndexValue(rank2idx).toString());
				minRank = Integer.parseInt(origin.getRows().get(i).getIndexValue(rank2idx).toString());
				while(groupId.equals(origin.getRows().get(i+number).getIndexValue(0).toString())){
					//计算出每组的极差和累计排名变化
					if(maxRank < Integer.parseInt(origin.getRows().get(i+number).getIndexValue(rank2idx).toString()))
						maxRank = Integer.parseInt(origin.getRows().get(i+number).getIndexValue(rank2idx).toString());
					if(minRank > Integer.parseInt(origin.getRows().get(i+number).getIndexValue(rank2idx).toString()))
						minRank = Integer.parseInt(origin.getRows().get(i+number).getIndexValue(rank2idx).toString());
					//累积排名变化
					cumchange += Integer.parseInt(origin.getRows().get(i).getIndexValue(rank3idx).toString())
							- Integer.parseInt(origin.getRows().get(i).getIndexValue(rank2idx).toString());
					
					number++;
					if(i+number>=origin.getRows().size()){	
						number--;
						break;
					}
				}
				//获得极差
				range = maxRank - minRank;
				number = 0;
				
			}
			//计算排名变化系数，未来推导出具有一个怎样的变化系数的小组才是合理的分组
			cfft = (double)cumchange/range;
			origin.getRows().get(i).setValue(index, cfft);
			
		}
	}
}