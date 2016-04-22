import static org.junit.Assert.*;

import java.util.HashMap;

import org.gitmining.monitor.util.FormulaUtil;
import org.junit.Test;


public class FormulaUtilTest {

	@Test
	public void testFormula() {
		String fomula = "(1+2)^4%20/3*5";
		assertEquals(1.0/3*5, FormulaUtil.calFormula(fomula,new HashMap<String, Double>()), 0.00001);
	}

}
