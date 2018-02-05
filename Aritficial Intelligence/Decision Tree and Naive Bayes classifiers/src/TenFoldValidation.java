import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TenFoldValidation {
	ArrayList<String> data;
	ArrayList<String> values;
	ArrayList<String> classes;
	
	public TenFoldValidation(String training) {
		data = new ArrayList<String>();
		values = new ArrayList<String>();
		classes = new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(training))) {
			String sCurrentLine;
	
			while ((sCurrentLine = br.readLine()) != null) {
				data.add(sCurrentLine);
				
				String[] p = sCurrentLine.split(",");
				classes.add(p[p.length-1]);
				p[p.length-1] = "";
				
				String value = String.join(",", p);
				values.add((String) value.subSequence(0, value.length() - 1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		stratifyData();
	}
	
	public void test(String classifier) {
		try{
			PrintWriter writer = new PrintWriter("pima-folds.csv", "UTF-8");
			int interval = data.size()/10;
			int remainder = data.size()%10;
			double totalAccuracy = 0.0;
			
			
			
			for (int i = 0; i < 10; i++) {
				int startIndex;
				int endIndex;
				
				//determine boundaries of training data in the current fold
				if (remainder == 0) {
					startIndex = i*interval;
					endIndex = (i+1)*interval;
				}
				else {
					if (i < remainder) {
						startIndex = (i)*(interval+1);
						endIndex = (i+1)*(interval+1);
					}
					else {
						startIndex = (i)*interval + remainder;
						endIndex = (i+1)*interval + remainder;
					}
				}
				
				//filter the data into two sets
				ArrayList<String> trainingData = new ArrayList<String>(data.subList(startIndex, endIndex));
				ArrayList<String> testingData = new ArrayList<String>();
				for (int j = 0; j < data.size(); j++) {
					if (j < startIndex || j >= endIndex) {
						testingData.add(data.get(j));
					}
				}
				
				//classify data
				ArrayList<String> result;
				if (classifier.equals("DT")) {
					//DecisionTree d = new DecisionTree(trainingData);
					DTCFS d = new DTCFS(trainingData);

					result = d.classifyData(testingData);
				}
				else {
					//NaiveBayes n = new NaiveBayes(trainingData);
					//use this one for CFS
					NBCFS n = new NBCFS(trainingData);
					result = n.classifyData(testingData);
				}
				
				//print the fold into the file
				writer.println("fold" + (i+1));
				for (String line: trainingData) {
					writer.println(line);
				}
				writer.println();
			//	writer.println();
				
				//update overall accuracy
				ArrayList<String> compare = new ArrayList<String>();
				for (int j = 0; j < classes.size(); j++) {
					if (j < startIndex || j >= endIndex) {
						compare.add(classes.get(j));
					}
				}
				double accuracy = getAccuracy(result, compare);
				System.out.println(accuracy);
				totalAccuracy += accuracy;
			}
			
			writer.println("Total accuracy of the classifier is " + (int) (totalAccuracy*10) + "%");
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public double getAccuracy(ArrayList<String> actualOutput, ArrayList<String> expected) {
		double counter = 0.0;
		
		for (int i = 0; i < expected.size(); i++) {
			if (actualOutput.get(i).equals(expected.get(i))) {
				counter++;
			}
		}
		//System.out.println("expected size is " + expected.size());
		return counter/(double) expected.size(); 
	}
	
	public void stratifyData() {
		ArrayList<String> yesData = new ArrayList<String>();
		ArrayList<String> noData = new ArrayList<String>();
		ArrayList<String> yesValues = new ArrayList<String>();
		ArrayList<String> noValues = new ArrayList<String>();
		ArrayList<String> yesClasses = new ArrayList<String>();
		ArrayList<String> noClasses = new ArrayList<String>();
		
		//separate the data into two groups yes and no
		for (int i = 0; i < classes.size(); i++) {
			if (classes.get(i).equals("yes")) {
				yesData.add(data.get(i));
				yesValues.add(values.get(i));
				yesClasses.add(classes.get(i));
			}
			else {
				noData.add(data.get(i));
				noValues.add(values.get(i));
				noClasses.add(classes.get(i));
			}
		}
		
		data = new ArrayList<String>();
		values = new ArrayList<String>();
		classes = new ArrayList<String>();
		
		int yesInterval = yesData.size()/10;
		int yesRemainder = yesData.size()%10;
		int noInterval = noData.size()/10;
		int noRemainder = noData.size()%10;
		for (int i = 0; i < 10; i++) {
			int startIndex;
			int endIndex;
			
			//add 1/10th of the yes group to the common data set
			if (yesRemainder == 0) {
				startIndex = i*yesInterval;
				endIndex = (i+1)*yesInterval;
			}
			else {
				if (i < yesRemainder) {
					startIndex = (i)*(yesInterval+1);
					endIndex = (i+1)*(yesInterval+1);
				}
				else {
					startIndex = (i)*yesInterval + yesRemainder;
					endIndex = (i+1)*yesInterval + yesRemainder;
				}
			}
			
			data.addAll(yesData.subList(startIndex, endIndex));
			values.addAll(yesValues.subList(startIndex, endIndex));
			classes.addAll(yesClasses.subList(startIndex, endIndex));
			
			//add 1/10th of the no group to the common data set
			if (noRemainder == 0) {
				startIndex = i*noInterval;
				endIndex = (i+1)*noInterval;
			}
			else {
				if (i < noRemainder) {
					startIndex = (i)*(noInterval+1);
					endIndex = (i+1)*(noInterval+1);
				}
				else {
					startIndex = (i)*noInterval + noRemainder;
					endIndex = (i+1)*noInterval + noRemainder;
				}
			}
			
			data.addAll(noData.subList(startIndex, endIndex));
			values.addAll(noValues.subList(startIndex, endIndex));
			classes.addAll(noClasses.subList(startIndex, endIndex));
		}
	}
	
	public void printData() {
		for (String s: classes) {
			System.out.println(s);
		}
	}
}
