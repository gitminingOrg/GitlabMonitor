package net.nju.ise.gitmining.enricher;

import net.nju.ise.gitmining.model.Table;

public interface Enricher {
	public Table enrich(Table origin);
}
