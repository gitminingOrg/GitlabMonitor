package net.nju.ise.gitmining.enricher;

import net.nju.ise.gitmining.model.Row;
import net.nju.ise.gitmining.model.Table;

public class StringAppendEnricher implements Enricher{

	@Override
	public Table enrich(Table origin, Object... objects) {
		origin.addNewColumn("newColumn");
		int index = origin.getTableDetail().getColumnIndex("newColumn");
		for(Row row : origin.getRows()){
			String a = row.getIndexValue(1);
			String b = row.getIndexValue(2);
			row.setValue(index, a+b);
		}
		return origin;
	}

}
