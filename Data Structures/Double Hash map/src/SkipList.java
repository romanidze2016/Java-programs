import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class SkipList<K extends Comparable<K>, V> {
	
	private int steps;
	private int size;
	private SkipListNode<K, V> topMin; //Top minimum infinity sentinel
	private SkipListNode<K, V> topMax; //Top maximum infinity sentinel
	private int height;
	
	// construction
	public SkipList() {
		steps = 0;
		size = 0;
		height = 0;
		topMin = new SkipListNode<K, V>(null, null);
		topMax = new SkipListNode<K, V>(null, null);
		topMin.setNext(topMax);
		topMax.setPrev(topMin);
	}
 // size
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		else {
			return false;
		}
	}
 // interface methods
	public List<K> keys() {
		SkipListNode<K, V> current = topMin;
		//Minimum infinity sentinel on the bottom of the skip list is located
		while (current.getBelow() != null) {
			current = current.getBelow();
		}
		List<K> keys = new ArrayList<K>();
		//Stores the key of each node on the bottom level to the keys array
		while (current.getNext().getKey() != null) {
			current = current.getNext();
			keys.add(current.getKey());
		}
		return keys;
	}

	public SkipListNode<K, V> search(K key) {
		steps = 0;
		int vertSteps = 0; 
		int horSteps = 0;
		SkipListNode<K, V> current = topMin;
		while (current.getBelow() != null) {
			current = current.getBelow();
			if (current.getNext().getKey() != null) {
				vertSteps++;
			}
			while (current.getNext().getKey() != null && key.compareTo(current.getNext().getKey()) >= 0) {
				current = current.getNext();
				if (vertSteps > horSteps) {
					horSteps = vertSteps;
				}
				else {
					horSteps++;
					vertSteps++;
				}
			}
		}
		steps = vertSteps;
		return current;
	}

	public V put(K key, V value) {
		SkipListNode<K, V> nextP = search(key); //the next node on the higher level after which the new entry will be inserted
		SkipListNode<K, V> p = search(key); //the node after which the new entry will be inserted
		//A node with the given key is already stored in the list 
		if (key.equals(nextP.getKey())) {
			V oldValue = nextP.getValue();
			nextP.setValue(value);
			//The nodes above p which also have the same key are updated as well
			while (nextP.getAbove() != null) {
				nextP = nextP.getAbove();
				nextP.setValue(value);
			}
			return oldValue;
		}
		else {
			int i = -1;
			Random randomizer = new Random();
			SkipListNode<K, V> prevEntry = null;
			do {
				i++;
				if (i >= height) {
					expand();
				}
				while (nextP.getAbove() == null) {
					nextP = nextP.getPrev();
				}
				nextP = nextP.getAbove();
				SkipListNode<K, V> entry = new SkipListNode<K, V>(key, value);
				//connect the entry below with the entry above
				if (prevEntry != null) {
					prevEntry.setAbove(entry);
					entry.setBelow(prevEntry);
				}
				//insert new entry in the list after p
				entry.setPrev(p);
				entry.setNext(p.getNext());
				p.setNext(entry);
				entry.getNext().setPrev(entry);
				p = nextP;
				prevEntry = entry;
			} while (randomizer.nextBoolean());
			size++;
			return null;
		}
	}

	public V get(K key) {
		SkipListNode<K, V> current = topMin;
		V value = null;
		//Get the minimum infinity sentinel on the bottom of the list
		while (current.getBelow() != null) {
			current = current.getBelow();
		}
		//Iterate over the nodes on the bottom until the right node is found
		while (current.getNext().getKey() != null) {
			current = current.getNext();
			if (current.getKey().equals(key)) {
				value = current.getValue();
			}
		}
		return value;
	}

	public V remove(K key) {
		SkipListNode<K, V> current = search(key);
		
		//There is no node with such a key
		if (!key.equals(current.getKey())) {
			return null;
		}
		
		V oldValue= current.getValue();
		size--;
		
		//remove all nodes with the given key from bottom to top
		do {
			current.getPrev().setNext(current.getNext());
			current.getNext().setPrev(current.getPrev());
			current = current.getAbove();
		} while (current != null);
		//remove a new empty level if necessary
		compress();
		return oldValue;
	}
	
	//This function ensures that on top of the list there is no more than one level with only two sentinel nodes
	public void compress() {
		SkipListNode<K, V> current = topMin.getBelow();
		while (current != null && current.getNext().getKey() == null) {
			current.getAbove().setBelow(current.getBelow());
			current.getNext().getAbove().setBelow(current.getNext().getBelow());
			if (current.getBelow() != null) {
				current.getBelow().setAbove(current.getAbove());
				current.getNext().getBelow().setAbove(current.getNext().getAbove());
			}
			current = current.getBelow();
		}
	}
	
	//This function adds a new empty level to the skip list
	public void expand() {
		SkipListNode<K, V> newTopMin = new SkipListNode<K, V>(null, null);
		SkipListNode<K, V> newTopMax = new SkipListNode<K, V>(null, null);
		newTopMin.setNext(newTopMax);
		newTopMax.setPrev(newTopMin);
		newTopMin.setBelow(topMin);
		newTopMax.setBelow(topMax);
		topMin.setAbove(newTopMin);
		topMax.setAbove(newTopMax);
		topMin = newTopMin;
		topMax = newTopMax; 
		height++;
	}
	
	//prints out the whole skip list for my own testing purposes
	public void print() {
		SkipListNode<K, V> vert = topMin;
		SkipListNode<K, V> hor = topMin;
		while (vert != null) {
			while (hor != null) {
				System.out.print(hor.getKey() + " ");
				hor = hor.getNext();
			}
			System.out.println();
			vert = vert.getBelow();
			hor = vert;
		}
	}
	
	public int searchSteps(K key) {
		search(key);
		return steps;
	}
}
