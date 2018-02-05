import java.util.ArrayList;
import java.util.List;

public class Hull {
	
	private List<List<Integer>> lines;
	
	public Hull() {
		lines = new ArrayList<List<Integer>>();
	}
	
    // Called when a new line of numbers is read from stdin.
    public void update(String s) {
        // TODO implement this
        //System.out.println("Receiving string " + s);
        
        String[] a = s.split(" ");
        
        List<Integer> temp = new ArrayList<Integer>();
        temp.add(Integer.parseInt(a[0]));
        temp.add(Integer.parseInt(a[1]));
        temp.add(Integer.parseInt(a[2]));
        
        lines.add(temp);
    }
    
    public void addSegment(List<Integer> newSeg) {
    	lines.add(newSeg);
    }
    
    public int size() {
    	return lines.size();
    }
    
    public List<Integer> getSegment(int index) {
    	return lines.get(index);
    }
    
    public void printHull() {
    	for (int i = 0; i < lines.size(); i++) {
    		System.out.println(lines.get(i));
    	}
    }

}