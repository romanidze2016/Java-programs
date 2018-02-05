import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainQuestion2 {

    // Main part of your code - actually find the hulls. TODO.
    public static Hull findHull(Hull segments) {
    	if (segments.size() < 1) {
    		return null;
    	}
    	else if (segments.size() == 1) {
    		return segments;
    	}
    	else if (segments.size() == 2) {
    		Hull a = new Hull();
    		Hull b = new Hull();
    		
    		a.addSegment(segments.getSegment(0));
    		b.addSegment(segments.getSegment(1));
    		return mergeHulls(a, b);
    	}
    	else {
    		Hull a = new Hull();
    		Hull b = new Hull();
    		
    		for (int i = 0; i < segments.size()/2; i++) {
    			a.addSegment(segments.getSegment(i));
    		}
    		
    		for (int i = segments.size()/2; i < segments.size(); i++) {
    			b.addSegment(segments.getSegment(i));
    		}
    		
    		return mergeHulls(findHull(a), findHull(b));
    	}
    }

    // Main part of your code - actually merge the hulls. TODO.
    public static Hull mergeHulls(Hull a, Hull b) {
    	Hull mergedHull = new Hull();
    	int indexA = 0;
    	int indexB = 0;
    	
    	List<Integer> current;
    	if (a.getSegment(0).get(0) < b.getSegment(0).get(0)) {
    		current = a.getSegment(0);
    		indexA++;
    	}
    	else if (a.getSegment(0).get(0) > b.getSegment(0).get(0)) {
    		current = b.getSegment(0);
    		indexB++;
    	}
    	else {
    		List<List<Integer>> result = mergeSegments(a.getSegment(0), b.getSegment(0));
    		for (int i = 0; i < result.size() - 1; i++) {
    			mergedHull.addSegment(result.get(i));
    		}
    		current = result.get(result.size() - 1);
    		indexA++;
    		indexB++;
    	}
    	
    	while(indexA < a.size() && indexB < b.size()) {
    		List<Integer> currentA = a.getSegment(indexA);
    		List<Integer> currentB = b.getSegment(indexB);
    		
    		if (currentA.get(0) < currentB.get(0)) {
    			List<List<Integer>> result = mergeSegments(current, currentA);
    			for (int i = 0; i < result.size() - 1; i++) {
        			mergedHull.addSegment(result.get(i));
        		}
        		current = result.get(result.size() - 1);
        		indexA++;
    		}
    		else if (currentA.get(0) > currentB.get(0)) {
    			List<List<Integer>> result = mergeSegments(current, currentB);
    			for (int i = 0; i < result.size() - 1; i++) {
        			mergedHull.addSegment(result.get(i));
        		}
        		current = result.get(result.size() - 1);
        		indexB++;
    		}
    		else {
    			List<List<Integer>> result = mergeSegments(currentA, currentB);
        		for (int i = 0; i < result.size(); i++) {
        			List<List<Integer>> other = mergeSegments(current, result.get(i));
        			for (int j = 0; j < other.size() - 1; j++) {
            			mergedHull.addSegment(other.get(j));
            		}
            		current = other.get(other.size() - 1);
        		}
        		indexA++;
        		indexB++;
    		}
    	}
    	
    	while (indexA < a.size()) {
			List<List<Integer>> result = mergeSegments(current, a.getSegment(indexA));
			for (int i = 0; i < result.size() - 1; i++) {
    			mergedHull.addSegment(result.get(i));
    		}
    		current = result.get(result.size() - 1);
    		indexA++;
		}
		
		while (indexB < b.size()) {
			List<List<Integer>> result = mergeSegments(current, b.getSegment(indexB));
			for (int i = 0; i < result.size() - 1; i++) {
    			mergedHull.addSegment(result.get(i));
    		}
    		current = result.get(result.size() - 1);
    		indexB++;
		}
		
		mergedHull.addSegment(current);
    	
        return mergedHull;
    }
    
    //this function returns a top hull produced from the two line segments passed to it
    public static List<List<Integer>> mergeSegments(List<Integer> a, List<Integer> b) {
    	List<List<Integer>> returnList = new ArrayList<List<Integer>>();
    	List<Integer> newA = new ArrayList<Integer>();
    	List<Integer> newB = new ArrayList<Integer>();
    	
    	//segments start at the same x point
    	if (a.get(0) == b.get(0)) {
    		//segments are on the same y level
    		if (a.get(2) == b.get(2)) {
    			if (a.get(1) > b.get(1)) {
    				newA.add(a.get(0));
    				newA.add(a.get(1));
    				newA.add(a.get(2));
    				returnList.add(newA);
    				return returnList;
    			}
    			else {
    				newB.add(b.get(0));
    				newB.add(b.get(1));
    				newB.add(b.get(2));
    				returnList.add(newB);
    				return returnList;
    			}
    		}
    		//a is higher than b
    		else if (a.get(2) > b.get(2)) {
    			newA.add(a.get(0));
				newA.add(a.get(1));
				newA.add(a.get(2));
				returnList.add(newA);
				
				if (b.get(1) > a.get(1)) {
					newB.add(a.get(1));
					newB.add(b.get(1));
					newB.add(b.get(2));
					returnList.add(newB);
				}
				
				return returnList;
    		}
    		//b is higher than a
    		else {
    			newB.add(b.get(0));
				newB.add(b.get(1));
				newB.add(b.get(2));
				returnList.add(newB);
				
				if (a.get(1) > b.get(1)) {
					newA.add(b.get(1));
					newA.add(a.get(1));
					newA.add(a.get(2));
					returnList.add(newA);
				}
				
				return returnList;
    		}
    	}
    	//a and b start at different x points
    	else {
    		
    		if (a.get(0) > b.get(0)) {
    			List<Integer> temp = a;
    			a = b;
    			b = temp;
    		}
    		
    		//a and b do not overlap
    		if (a.get(1) <= b.get(0)) {
    			//a and b need to be merged into one segment
    			if (a.get(2) == b.get(2) && a.get(1) == b.get(0)) {
    				newA.add(a.get(0));
    				newA.add(b.get(1));
    				newA.add(a.get(2));
    				returnList.add(newA);
    				
    				return returnList;
    			}
    			//a and b are separate
    			else {
    				newA.add(a.get(0));
    				newA.add(a.get(1));
    				newA.add(a.get(2));
    				returnList.add(newA);
    				
    				newB.add(b.get(0));
    				newB.add(b.get(1));
    				newB.add(b.get(2));
    				returnList.add(newB);
    				
    				return returnList;
    			}
    		}
    		//a and b overlap
    		else {
    			//they are on the same y level
    			if (a.get(2) == b.get(2)) {
    				newA.add(a.get(0));
    				if (b.get(1) > a.get(1)) {
    					newA.add(b.get(1));
    				}
    				else {
    					newA.add(a.get(1));
    				}
    				newA.add(a.get(2));
    				returnList.add(newA);
    				
    				return returnList;
    			}
    			//a is higher than b
    			else if (a.get(2) > b.get(2)) {
    				newA.add(a.get(0));
    				newA.add(a.get(1));
    				newA.add(a.get(2));
    				returnList.add(newA);
    				
    				if (b.get(1) > a.get(1)) {
    					newB.add(a.get(1));
    					newB.add(b.get(1));
    					newB.add(b.get(2));
    					returnList.add(newB);
    				}
    				
    				return returnList;
    			}
    			//b is higher than a
    			else {
    				newA.add(a.get(0));
    				newA.add(b.get(0));
    				newA.add(a.get(2));
    				returnList.add(newA);
    				
    				newB.add(b.get(0));
    				newB.add(b.get(1));
    				newB.add(b.get(2));
    				returnList.add(newB);
    				
    				if (a.get(1) > b.get(1)) {
    					newA = new ArrayList<Integer>();
    					newA.add(b.get(1));
    					newA.add(a.get(1));
    					newA.add(a.get(2));
    					returnList.add(newA);
    				}
    				
    				return returnList;
    			}
    		}
    	}
    }
    
    // Formats the merged hull for output. TODO.
    public static String fmt(Hull h) {
    	String output = "";
    	
    	for (int i = 0; i < h.size() - 1; i++) {
    		output += h.getSegment(i).get(0) + " " + h.getSegment(i).get(1) + " " + h.getSegment(i).get(2) + "\n";
    	}
    	output += h.getSegment(h.size() - 1).get(0) + " " + h.getSegment(h.size() - 1).get(1) + " " + h.getSegment(h.size() - 1).get(2);
    	
        return output;
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        // Read hull 2 from stdin
        Hull segments = new Hull();
        while (scan.hasNextLine()) {
        	String a = scan.nextLine();
        	if (a.equals("")) {
        		break;
        	}
            segments.update(a);
        }

        scan.close();

        // Merge and output the hulls.
        Hull out = findHull(segments);
        String output = fmt(out);
        System.out.println(output);

    }
}