import java.util.List;

public class SkipListPasswordManager {

	private SkipList<String, User> manager;

	// construct a PasswordManager with 4000 places
	// hash parameters should be multiplier=1 modulus=4271 secondaryModulus=647
	public SkipListPasswordManager() {
		manager = new SkipList<String, User>();
	}

	// hash representation of password to be stored on the User object
	public Long hash(String password) {
		long hash = 5381;
		for (int i = 0; i < password.length(); i++) {
			char c = password.charAt(i);
			hash = ((hash << 5) + hash) + c;
		}
		return hash;
	}

	// userbase methods
	// return an array of the usernames of the users currently stored
	public List<String> listUsers() {
		return manager.keys();
	}

	public int numberUsers() {
		return manager.size();
	}

	public String addNewUser(String username, String password) {
		if (manager.keys().contains(username)) {
			return "User already exists.";
		} else {
			User newUser = new User(username);
			newUser.setPassword("manager", hash(password));
			manager.put(username, newUser);
			return username;
		}
	}

	public String deleteUser(String username, String password) {
		String response = authenticate(username, password);
		if (!response.equals(username)) {
			return response;
		} else {
			manager.remove(username);
			return username;
		}
	}

	// interface methods
	public String authenticate(String username, String password) {
		if (!manager.keys().contains(username)) {
			return "No such user exists.";
		} else if (!hash(password).equals(manager.get(username).getPassword("manager"))) {
			return "Failed to authenticate user.";
		} else {
			return username;
		}
	}

	public String authenticate(String username, String password, String appName) {
		if (!manager.keys().contains(username)) {
			return "No such user exists.";
		} else if (manager.get(username).getPassword(appName) == null) {
			return "No password found.";
		} else if (manager.get(username).getPassword(appName).equals(hash(password))) {
			return username;
		} else {
			return "Failed to authenticate user.";
		}
	}

	public String resetPassword(String username, String oldPassword, String newPassword) {
		String response = authenticate(username, oldPassword);
		if (!response.equals(username)) {
			return response;
		} else {
			manager.get(username).setPassword("manager", hash(newPassword));
			return username;
		}
	}

	public String resetPassword(String username, String oldPassword, String newPassword, String appName) {
		String response = authenticate(username, oldPassword, appName);
		if (!response.equals(username)) {
			return response;
		} else {
			manager.get(username).setPassword(appName, hash(newPassword));
			return username;
		}
	}

	public String newAppPassword(String username, String thisPassword, String appPassword, String appName) {
		String response = authenticate(username, thisPassword);
		if (!response.equals(username)) {
			return response;
		} else if (manager.get(username).getPassword(appName) != null) {
			return "Password already set up.";
		} else {
			manager.get(username).setPassword(appName, hash(appPassword));
			return username;
		}
	}
	
	public int searchSteps(String username) {
		return manager.searchSteps(username);
	}
	
}
