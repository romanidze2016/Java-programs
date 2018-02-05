import java.util.ArrayList;
import java.util.List;

public class DoubleHashMap<K extends Comparable<K>, V> {

	private HashMapNode<K, V>[] map;
	private int multiplier;
	private int modulus;
	private int secondaryModulus;
	private int numberOfItems;
	private int numberOfPutCollisions;
	private int numberOfTotalCollisions;
	private int numberOfMaxCollisions;

	// construct a DoubleHashMap with 4000 places and given hash parameters
	public DoubleHashMap(int multiplier, int modulus, int secondaryModulus) {
		map = new HashMapNode[4000];
		this.multiplier = multiplier;
		this.modulus = modulus;
		this.secondaryModulus = secondaryModulus;
		numberOfItems = 0;
		numberOfPutCollisions = 0;
		numberOfTotalCollisions = 0;
		numberOfMaxCollisions = 0;
	}

	// construct a DoubleHashMap with given capacity and given hash parameters
	public DoubleHashMap(int hashMapSize, int multiplier, int modulus, int secondaryModulus) {
		map = new HashMapNode[hashMapSize];
		this.multiplier = multiplier;
		this.modulus = modulus;
		this.secondaryModulus = secondaryModulus;
		numberOfItems = 0;
		numberOfPutCollisions = 0;
		numberOfTotalCollisions = 0;
		numberOfMaxCollisions = 0;
	}

	// hashing
	public int hash(K key) {
		return Math.abs(multiplier * Math.abs(key.hashCode())) % modulus;
	}

	public int secondaryHash(K key) {
		return secondaryModulus - (Math.abs(key.hashCode()) % secondaryModulus);
	}

	// size (return the number of nodes currently stored in the map)
	public int size() {
		return numberOfItems;
	}

	public boolean isEmpty() {
		if (numberOfItems == 0) {
			return true;
		} else {
			return false;
		}
	}

	// interface methods
	public List<K> keys() {
		List<K> keys = new ArrayList<K>();
		for (int counter = 0; counter < map.length; counter++) {
			if (map[counter] != null && map[counter].getKey() != null) {
				keys.add(map[counter].getKey());
			}
		}
		return keys;
	}

	public V put(K key, V value) {
		int hash = hash(key);
		int secondaryHash = secondaryHash(key);
		int counter = 0;
		int index = Math.abs(hash + counter * secondaryHash) % map.length;
		boolean collision = false;
		int numberOfCollisions = 0;
		V returnValue = null;
		while (true) {
			try {
				//empty position is found
				if (map[index] == null || map[index].getKey() == null) {
					map[index] = new HashMapNode<K, V>(key, value);
					numberOfItems++;

					if (collision) {
						numberOfPutCollisions++;
					}
					if (numberOfCollisions > numberOfMaxCollisions) {
						numberOfMaxCollisions = numberOfCollisions;
					}
					numberOfTotalCollisions += numberOfCollisions;

					break;
				//The list already has a node with the same key
				} else if (map[index].getKey().equals(key)) {
					V oldValue = map[index].getValue();
					map[index].setValue(value);

					if (collision) {
						numberOfPutCollisions++;
					}
					numberOfTotalCollisions += numberOfCollisions;

					returnValue = oldValue;
					break;
				//cell is already taken, go to the next cell
				} else {
					numberOfCollisions++;
					collision = true;
					counter++;
					index = Math.abs(hash + counter * secondaryHash) % map.length;
					throw new RuntimeException("Double Hashing failed to find a free position");
				}
			} catch (RuntimeException e) {
				if (!e.getMessage().equals("Double Hashing failed to find a free position")) {
					throw e;
				}
			}
		}
		return returnValue;
	}

	public V get(K key) {
		if (!keys().contains(key)) {
			return null;
		} else {
			int hash = hash(key);
			int secondaryHash = secondaryHash(key);
			int counter = 0;
			int index = Math.abs(hash + counter * secondaryHash) % map.length;
			while (true) {
				if (map[index] != null && map[index].getKey().equals(key)) {
					return map[index].getValue();
				} else {
					counter++;
					index = Math.abs(hash + counter * secondaryHash) % map.length;
				}
			}
		}
	}

	public V remove(K key) {
		if (!keys().contains(key)) {
			return null;
		} else {
			int hash = hash(key);
			int secondaryHash = secondaryHash(key);
			int counter = 0;
			int index = Math.abs(hash + counter * secondaryHash) % map.length;
			while (true) {
				if (map[index] != null && key.equals(map[index].getKey())) {
					V oldValue = map[index].getValue();
					map[index] = new HashMapNode<K, V>(null, null);
					numberOfItems--;
					return oldValue;
				} else {
					counter++;
					index = Math.abs(hash + counter * secondaryHash) % map.length;
				}
			}
		}
	}

	// collision statistics
	public int putCollisions() {
		return numberOfPutCollisions;
	}

	public int totalCollisions() {
		return numberOfTotalCollisions;
	}

	public int maxCollisions() {
		return numberOfMaxCollisions;
	}

	public void resetStatistics() {
		numberOfPutCollisions = 0;
		numberOfTotalCollisions = 0;
		numberOfMaxCollisions = 0;
	}
}