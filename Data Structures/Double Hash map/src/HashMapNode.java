
public class HashMapNode<K extends Comparable<K>, V> {
	
	private K key;
	private V value;
	
	// construction
	public HashMapNode(K key, V value) {
		this.key = key;
		this.value = value;
	}

 // get methods
	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}
	
 // set method
	public void setValue(V newValue) {
		value = newValue;
	}
}
