import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Variable {
	private String name;
	private Map<String, String[]> transitions; //variable's parse table (key is a terminal and value is a tokenized rule)
	
	public Variable(String name) {
		this.name = name;
		transitions = new HashMap<String, String[]>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setTransition(String terminal, String[] transitionString) {
		transitions.put(terminal, transitionString);
	}
	
	public String[] getTransitionString(String terminal) {
		return transitions.get(terminal);
	}
	
	//Determines whether variable can become an epsilon
	public boolean canBeEpsilon() {
		Collection<String[]> rules = transitions.values();
		for (String[] ruleString : rules) {
			if (ruleString[0] == "e") {
				return true;
			}
		}
		
		return false;
	}
	
	public Set<String> getFirstSet() {
		return transitions.keySet();
	}
}
