public class SkipListNode<K extends Comparable<K>, V> {
	
	private K key; 
	private V value;
	private SkipListNode<K, V> prev;
	private SkipListNode<K, V> next;
	private SkipListNode<K, V> above;
	private SkipListNode<K, V> below;
	
	// construction
	SkipListNode(K key, V value) {
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

	public SkipListNode<K, V> getPrev() {
		return prev;
	}

	public SkipListNode<K, V> getNext() {
		return next;
	}

	public SkipListNode<K, V> getAbove() {
		return above;
	}

	public SkipListNode<K, V> getBelow() {
		return below;
	}
 // set methods
	public void setValue(V newValue) {
		this.value = newValue;
	}

	public void setPrev(SkipListNode<K, V> prev) {
		this.prev = prev;
	}

	public void setNext(SkipListNode<K, V> next) {
		this.next = next;
	}

	public void setAbove(SkipListNode<K, V> above) {
		this.above = above;
	}

	public void setBelow(SkipListNode<K, V> below) {
		this.below = below;
	}
}
