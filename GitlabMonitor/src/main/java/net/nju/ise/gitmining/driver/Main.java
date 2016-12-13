package net.nju.ise.gitmining.driver;

import net.nju.ise.gitmining.enricher.Enricher;
import net.nju.ise.gitmining.enricher.RankEnricher;
import net.nju.ise.gitmining.enricher.StringAppendEnricher;
import net.nju.ise.gitmining.generator.TableGenerator;
import net.nju.ise.gitmining.model.Table;

public class Main {

	public static void main(String[] args) {
		Table table = TableGenerator.csvToTable("aaa.csv");
		System.out.println(table.getTableDetail().size());
		System.out.println(table.getRows().get(0).size());
		
		
		Enricher enricher = new StringAppendEnricher();
		enricher.enrich(table);
		System.out.println(table.getTableDetail().size());
		System.out.println(table.getRows().get(0).size());
		
		enricher = new RankEnricher();
		enricher.enrich(table, 2, "cseiii_rank");
		enricher.enrich(table, 3, "cseii_rank");
		enricher.enrich(table, 4, "csei_rank");
		
		System.out.println(table.getTableDetail().size());
		System.out.println(table.getRows().get(0).size());
		
		TableGenerator.TableToCSV("b.csv", table);
	}

}
