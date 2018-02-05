import java.util.List;

public class State {
	
	private String name;
	private String[] transitions;
	private boolean accepted;
	
	public State(String name, String[] transitions) {
		this.name = name;
		this.transitions = transitions;
		accepted = false;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isAccepted() {
		return accepted;
	}
	
	public String getTransition(int index) {
		return transitions[index];
	}
	
	public void setAccepted() {
		accepted = true;
	}
	
}
