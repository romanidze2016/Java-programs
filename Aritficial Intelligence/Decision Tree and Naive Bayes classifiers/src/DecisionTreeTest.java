import static org.junit.Assert.*;

import org.junit.Test;

public class DecisionTreeTest {
	double epsilon = 0.0001;

	
	@Test
	public void testInfoGainCol0() {
		DecisionTree dt = new DecisionTree("discreteMini.csv");
		//System.err.println(dt.infoGain(0,dt.data));
		assertEquals(0.31127, dt.infoGain(0,dt.data), epsilon);

//		System.out.println(dt.infoGain(0,dt.data));
//		System.out.println(dt.infoGain(0,dt.data));
//		System.out.println(dt.infoGain(0,dt.data));

		//dt.classifyData("testDiscreteMini.csv");
		
	}
	@Test
	public void testInfoGainCol1() {
		DecisionTree dt = new DecisionTree("discreteMini.csv");
		assertEquals(1.0, dt.infoGain(1,dt.data), epsilon);
		
	}
	@Test
	public void testInfoGainCol2() {
		DecisionTree dt = new DecisionTree("discreteMini.csv");
		assertEquals(0.31127, dt.infoGain(0,dt.data), epsilon);

		
	}
	@Test
	public void testInfoGainPimaCol0() {
		DecisionTree dt = new DecisionTree("pimaDiscreteTraining.csv");
		
		//System.err.println(dt.entropy(173/599.0, 426/599.0));
		
		assertEquals(0.86718,dt.entropy(173/599.0, 426/599.0), epsilon);
		
		assertEquals(0.86718,dt.entropy(173/599.0, 426/599.0), epsilon);

		assertEquals(0.03918, dt.infoGain(0,dt.data), epsilon);

		
	}
	@Test
	public void testClassifierMini() {
		DecisionTree dt = new DecisionTree("discreteMini.csv");
				
		assertEquals(0.03918, dt.infoGain(0,dt.data), epsilon);
		
	}
	@Test
	public void NBCFS() {
		//NBCFS nb = new NBCFS("pima-CFS.csv");
		//nb.classifyData("pima-CFS.csv");
		//nb.printMeans();
		TenFoldValidation t = new TenFoldValidation("pima-CFS.csv");
		t.test("NB");
		
		//nb.classifyData(");
		//assertEquals(0.03918, dt.infoGain(0,dt.data), epsilon);
		
	}
	@Test
	public void DTCFS() {
		//DTCFS dt = new DTCFS("pd.csv");
		//dt.classifyData("pd.csv");
		TenFoldValidation t = new TenFoldValidation("pd.csv");

		t.test("DT");
		//nb.classifyData(");
		//assertEquals(0.03918, dt.infoGain(0,dt.data), epsilon);
		
	}
	


}
