package net.nju.ise.gitmining.driver;

import net.nju.ise.gitmining.enricher.Enricher;
import net.nju.ise.gitmining.enricher.StringAppendEnricher;
import net.nju.ise.gitmining.generator.TableGenerator;
import net.nju.ise.gitmining.model.Table;

public class Main {

	public static void main(String[] args) {
		Table table = TableGenerator.csvToTable("a.csv");
		System.out.println(table.getTableDetail().size());
		System.out.println(table.getRows().size());
		
		
		Enricher enricher = new StringAppendEnricher();
		enricher.enrich(table);
		System.out.println(table.getTableDetail().size());
		System.out.println(table.getRows().size());
	}

}
