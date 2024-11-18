package sk.upjs.gursky.pdb;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnclusteredBPTreeSalaryTest {

	private UnclusteredBPTreeSalary bptree;
	
	@Before
	public void setUp() throws Exception {
		bptree = UnclusteredBPTreeSalary.createByBulkLoading();
		
	}

	@After
	public void tearDown() throws Exception {
		bptree.close();
		UnclusteredBPTreeSalary.INDEX_FILE.delete();
	}

	@Test
	public void test() throws Exception {	
		long time = System.nanoTime();
		List<SalaryOffsetEntry> result = bptree.intervalQuery(new PersonSalaryKey(250), new PersonSalaryKey(2000));
		time = System.nanoTime() - time;
		
		System.out.println("Interval Unclustered: " + time/1_000_000.0 + " ms.");

		for (int i = 0; i<20; i++) {
			System.out.println(result.get(i));
		}
		
		assertTrue(result.size() > 0);
		
	}
	
	@Test
	public void test2() throws Exception {	
		long time = System.nanoTime();
		List<PersonEntry> result = bptree.unclusteredIntervalQuery(new PersonSalaryKey(250), new PersonSalaryKey(2000));
		time = System.nanoTime() - time;
		
		System.out.println("Interval Unclustered: " + time/1_000_000.0 + " ms.");

		assertTrue(result.size() > 0);
		int toPrint = 20; 
		for (PersonEntry entry : result) {
			System.out.println(entry);
			if (toPrint == 0) break;
			toPrint--;
		}
	}
}
