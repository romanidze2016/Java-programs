
public class MyClassifier {
	public static void main(String args[]){
		//System.out.println("This is our classifier");
		String trainingData=args[0];
		String testingData=args[1];
		String classifier = args[2];
		if(classifier.equals("NB")){
			//start naive bayes
			NaiveBayes nb = new NaiveBayes(trainingData);
			nb.classifyData(testingData);
		}
		else if(classifier.equals("DT")){
			//start decision tree
			DecisionTree dt = new DecisionTree(trainingData);
			dt.classifyData(testingData);
			dt.printDT();

		}
		else{
			System.err.println("Error: Classifier not recognised");
		}
		
		//TenFoldValidation t = new TenFoldValidation(trainingData);
		//t.test(classifier);
	}
}
