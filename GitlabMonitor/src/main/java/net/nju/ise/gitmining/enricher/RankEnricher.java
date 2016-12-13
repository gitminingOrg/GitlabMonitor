package net.nju.ise.gitmining.enricher;

import java.util.PriorityQueue;

import net.nju.ise.gitmining.model.Row;
import net.nju.ise.gitmining.model.Table;

public class RankEnricher implements Enricher {
	public static final double EPS = 0.0000001;
	@Override
	public Table enrich(Table origin, Object... objects) {
		String columnName = (String) objects[1];
		int rownum = (int) objects[0];
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
		int index = 0;
		for(Row row: origin.getRows()){
			double value = Double.parseDouble(row.getIndexValue(rownum).toString());
			Pair pair = new Pair(index, value);
			pq.offer(pair);
			index++;
		}
		System.err.println(pq.size());
		origin.addNewColumn(columnName);
		int colIndex = origin.getTableDetail().getColumnIndex(columnName);
		int rank = 0;
		double lastValue = -1;
		for(int i=0; i<pq.size(); i++){
			Pair pair = pq.poll();
			if(Math.abs(pair.getValue() - lastValue) > EPS){
				rank = i+1;
			}
			origin.getRows().get(pair.getIndex()).setValue(colIndex, rank);
			lastValue = pair.getValue();
		}
		return origin;
	}
}

class Pair implements Comparable<Pair>{
	private int index;
	private double value;
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Pair(int index, double value) {
		super();
		this.index = index;
		this.value = value;
	}

	@Override
	public int compareTo(Pair o) {
		return (o.value - this.value) > RankEnricher.EPS ? 1 : -1;
	}
}
