
public class User {
	
	private DoubleHashMap<String, Long> store;
	private String username;
	// construct a new User with given username and empty password store
	// store should have size 20, and use multiplier=1 modulus=23
	// secondaryModulus=11
	public User(String username) {
		this.username = username;
		store = new DoubleHashMap<String, Long>(20, 1, 23, 11);
	}
 // get methods
	public String getUsername() {
		return username;
	}

	public Long getPassword(String appName) {
		return store.get(appName);
	}
 // set method
	public void setPassword(String appName, Long passwordHash) {
		store.put(appName, passwordHash); 
	}
}
