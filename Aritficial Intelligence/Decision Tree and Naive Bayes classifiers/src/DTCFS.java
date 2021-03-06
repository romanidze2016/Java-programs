import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DTCFS {
	ArrayList<CFSNominalData> data = new ArrayList<CFSNominalData>();
	MyNode root;

	//first dimension is each attribute 
	//second dimension is each degree from low to very high then the last is total
	public DTCFS(String training){
		try (BufferedReader br = new BufferedReader(new FileReader(training))) {
	
			String sCurrentLine;
	
			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
				String[] p = sCurrentLine.split(",");
				
				CFSNominalData d = new CFSNominalData(p[0], p[1], p[2], p[3], p[4], p[5]);
				data.add(d);					
						
				}
				//can each be low, medium, high, very high
			
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		



		//should be 0.811
		//decision tree traph
		//Node<Integer> graph = new Node<Integer>(-1);
		Integer[]array = {0,1,2,3,4};
		ArrayList<Integer> attributes = new ArrayList<Integer>(Arrays.asList(array));
		
		root = DTL(data, attributes, getMajorityClass(data), null);
		
		
	}
	
	public DTCFS(ArrayList<String> training){
		for (String sCurrentLine: training) {
			String[] p = sCurrentLine.split(",");
			
			CFSNominalData d = new CFSNominalData(p[0], p[1], p[2], p[3], p[4], p[5]);
			data.add(d);									
		}
		
		Integer[]array = {0,1,2,3,4};
		ArrayList<Integer> attributes = new ArrayList<Integer>(Arrays.asList(array));
		
		root = DTL(data, attributes, getMajorityClass(data), null);
	}
	
	
	public void printDt(MyNode currentNode){
		//for (CFSNominalData d: data) {			
			int depth = 0;
			Degree values[] = {Degree.LOW,Degree.MEDIUM,Degree.HIGH,Degree.VERY_HIGH};
			for(Degree d: values){
				int attribute = currentNode.getAttribute();

			}
//			while(!currentNode.isLeaf()) {
//				int attribute = currentNode.getAttribute();
//				Degree value = currentNode;
//				for(int i=0;i<depth;i++){
//					System.out.print("--");
//				}
//				System.out.print(attribute);
//				System.out.print(value);
//				/*if (counter == 76) {
//					System.out.println("Node at depth: " + depth);
//					System.out.println("Attribute checked: " + attribute);
//					System.out.println("Value at attribute " + attribute + " = " + value);
//					System.out.println();
//				}*/
//				
//				depth++;
//				currentNode = currentNode.getChild(value);
//				System.out.println("");
//			}
		//}
	}
	public void classifyData(String testing) {
		//read data from the file
		data = new ArrayList<CFSNominalData>();
		try (BufferedReader br = new BufferedReader(new FileReader(testing))) {
			
			String sCurrentLine;
	
			while ((sCurrentLine = br.readLine()) != null) {
				String[] p = sCurrentLine.split(",");
	
				CFSNominalData d = new CFSNominalData(p[0], p[1], p[2], p[3], p[4],"");
				data.add(d);			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int counter = 0;
		//run each example through decision tree
		for (CFSNominalData d: data) {
			MyNode currentNode = root;
			
			int depth = 0;
			
			while(!currentNode.isLeaf()) {
				int attribute = currentNode.getAttribute();
				Degree value = d.getAttributeAt(attribute);
				
				/*if (counter == 76) {
					System.out.println("Node at depth: " + depth);
					System.out.println("Attribute checked: " + attribute);
					System.out.println("Value at attribute " + attribute + " = " + value);
					System.out.println();
				}*/
				
				depth++;
				currentNode = currentNode.getChild(value);
			}
			
			/*if (counter == 76) {
				System.out.println("Node at depth: " + depth);
				System.out.println("Return class: " + currentNode.getStatus());
				System.out.println();
			}*/
			
			if (currentNode.getStatus()) {
				System.out.println("yes");
			}
			else {
				System.out.println("no");
			}
			/*System.out.println("=================");
			System.out.println();*/
			counter++;
		}
	}
	
	public ArrayList<String> classifyData(ArrayList<String> testing) {
		//read data from the file
		data = new ArrayList<CFSNominalData>();
		ArrayList<String> result = new ArrayList<String>();
	
		for (String sCurrentLine: testing) {
			String[] p = sCurrentLine.split(",");

			CFSNominalData d = new CFSNominalData(p[0], p[1], p[2], p[3], p[4], "");
			data.add(d);			
		}
		
		//run each example through decision tree
		for (CFSNominalData d: data) {
			MyNode currentNode = root;
			
			while(!currentNode.isLeaf()) {
				currentNode = currentNode.getChild(d.getAttributeAt(currentNode.getAttribute()));
			}
			
			if (currentNode.getStatus()) {
				result.add("yes");
			}
			else {
				result.add("no");
			}
		}
		
		return result;
	}
	
	public MyNode DTL(ArrayList<CFSNominalData> examples, ArrayList<Integer> attributes, boolean defaultClass, MyNode parent) {
		MyNode node = new MyNode(parent);
		ArrayList<Integer> myAttributes = (ArrayList<Integer>) attributes.clone();
		
		if (examples.isEmpty()) {
			node.setStatus(defaultClass);
			return node;
		}
		else if (sameClassification(examples)) {
			//System.out.println((examples.get(0).status));
			node.setStatus(examples.get(0).status);
			return node;
		}
		else if (myAttributes.isEmpty()) {
			node.setStatus(getMajorityClass(examples));
			return node;
		}
		else {
			//determine the best attribute to split on
			int bestAttributeIndex = 0;
			double bestInfoGain = infoGain(myAttributes.get(0), examples);
			for (int i = 1; i < myAttributes.size(); i++) {
				double infoG = infoGain(myAttributes.get(i), examples);
				if (infoG > bestInfoGain) {
					bestAttributeIndex = i;
					bestInfoGain = infoG;
				}
			}
			
			int bestAttribute = myAttributes.get(bestAttributeIndex);
			myAttributes.remove(bestAttributeIndex);
			node.setAttribute(bestAttribute);
			
			boolean majorityClass = getMajorityClass(examples);

			//Expand the node and iterate over its children
			node.addChild(Degree.LOW, DTL(filter(bestAttribute, Degree.LOW, examples), myAttributes, majorityClass, node));
			node.addChild(Degree.MEDIUM, DTL(filter(bestAttribute, Degree.MEDIUM, examples), myAttributes, majorityClass, node));
			node.addChild(Degree.HIGH, DTL(filter(bestAttribute, Degree.HIGH, examples), myAttributes, majorityClass, node));
			node.addChild(Degree.VERY_HIGH, DTL(filter(bestAttribute, Degree.VERY_HIGH, examples), myAttributes, majorityClass, node));
			
			return node;
		}
	}
	
	public boolean getMajorityClass(ArrayList<CFSNominalData> examples) {
		int countYes = 0;
		int countNo = 0;
		
		for (CFSNominalData d: examples) {
			if (d.status) {
				countYes++;
			}
			else {
				countNo++;
			}
		}
		
		if (countYes >= countNo) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	//check if its a pure attribute with all class = true or class = false
	public boolean sameClassification(ArrayList<CFSNominalData> d){
		boolean compare =d.get(0).status;
		for(CFSNominalData nd: d){
			if(nd.status!=compare){
				return false;
			}
		}
		return true;
	}
//	public double entropy(double probability){
//		return -probability*(Math.log10(probability) / Math.log10(2.0));
//	}
	public double entropy(double probability1,double probability2){
		if(probability1==0&&probability2==0){
			return 0;
		}
		if(probability1==0){
			return -probability2*(Math.log10(probability2) / Math.log10(2.0));
		}
		else if(probability2==0){
			return -probability1*(Math.log10(probability1) / Math.log10(2.0));
		}
		return -probability1*(Math.log10(probability1) / Math.log10(2.0)) 
				+ -probability2*(Math.log10(probability2) / Math.log10(2.0));
	}
	
	public ArrayList<CFSNominalData> filter(int attribute,Degree deg, ArrayList<CFSNominalData>d){
		ArrayList<CFSNominalData> filtered= new ArrayList<CFSNominalData>();
		for(CFSNominalData nd: d){
			if(nd.getAttributeAt(attribute).equals(deg)){
				filtered.add(nd);
			}
		}
		
		return filtered;
		
	}
	public double infoGain(int attribute,ArrayList<CFSNominalData> d){
		//get entropy of the total system before splitting
		//this doesnt need to be a 2d array, legacy code...
		int[] countYes = new int[5];
		int[] countNo = new int[5];
		for(CFSNominalData nd: d){
			//for(int i=0;i<8;i++){
			////System.err.println(nd.getAttributeAt(attribute));
				if(nd.status==true){
					//System.err.println("It's true");
					countYes[4]++;
					//System.out.println("attribute is " + attribute);
					if(nd.getAttributeAt(attribute).equals(Degree.LOW)){
						//System.err.println("It's low");

						countYes[0]++;
					}
					else if(nd.getAttributeAt(attribute).equals(Degree.MEDIUM)){
						countYes[1]++;
					}
					else if(nd.getAttributeAt(attribute).equals(Degree.HIGH)){
						countYes[2]++;
					}
					else if(nd.getAttributeAt(attribute).equals(Degree.VERY_HIGH)){
						countYes[3]++;
					}
				}
				else if(nd.status==false){
					countNo[4]++;
					if(nd.getAttributeAt(attribute).equals(Degree.LOW)){
						countNo[0]++;
					}
					else if(nd.getAttributeAt(attribute).equals(Degree.MEDIUM)){
						countNo[1]++;
					}
					else if(nd.getAttributeAt(attribute).equals(Degree.HIGH)){
						countNo[2]++;
					}
					else if(nd.getAttributeAt(attribute).equals(Degree.VERY_HIGH)){
						countNo[3]++;
					}
				}
				
					
			//}
		}
		double total = countYes[4]+countNo[4];
		//System.err.println("Total is " + total);
		double totals[] = new double[4];
		double s[] = new double[4];
		for(int i=0;i<4;i++){
			totals[i]= countYes[i]+countNo[i];
			//System.err.println("For degree ["+i+"]"+ "with count " + countYes[attribute][i] +" and count no " + countNo[attribute][i]);

			//System.err.println("totals ["+i+"]" + totals[i]);
			if(totals[i]==0){
				continue;
			}
			s[i]=entropy(countYes[i]/totals[i],countNo[i]/totals[i]);
			//System.err.println("entropy ["+i+"]" + s[i]);
		}
		//not sure this is strictly correct, need entropy of previous node
		double t1 = entropy(countYes[4]/total,countNo[4]/total);
		//System.err.println("count yes total is " + countYes[attribute][4]);
		//System.err.println("count no total is " + countNo[attribute][4]);
		//System.err.println("t1 is " + t1);

		double t2 =0;
		for(int i=0;i<4;i++){
			if(totals[i]==0||total==0){
				continue;
			}
		//	System.err.println("S[i] " + s[i]);
		//	System.err.println("Adding  " + s[i]*totals[i]/total);

			t2+=s[i]*totals[i]/total;
		}
		//System.err.println("T1 is "+t1+ " T2 is " + t2);
		//System.err.println(t1-t2);
//		if(t1-t2<0.000001){
//			return 0.0;
//		}
		return t1-t2;
	}
	
	
	
}
