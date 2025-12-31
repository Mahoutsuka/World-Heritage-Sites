package heritage;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Auxillary data structure hashmap uses to implement seperate chaining
 */

public class LinkedList<Key, Value> implements Iterable<Key>{
	Node first;
	int numSize;

	public LinkedList(){
		numSize = 0;
	}

	public class Node{
		public Value data;
		public Key key;
		public Node next;

		public Node(Key key, Value value){
			this.data = value;
			this.key = key;

		}
	}

	public boolean isEmpty(){
		if (numSize == 0) return true;
		return false;
	}

	public int size(){
		return numSize;
	}

	public void add(Key key, Value item){
		Node newNode = new Node(key, item);
		if (isEmpty()){
			first = newNode;
		}
		else{
			Node oldFirst = first;
			first = newNode;
			first.next = oldFirst;
		}
		numSize ++;
	}

	public String toString(){
		String strrep = "";
		Node current = first;
		if (first == null){
			return strrep;
		}
		while (current.next != null){
			strrep += current.key.toString() + " " + current.data.toString() + " -> ";
			current = current.next;
		}
		strrep += current.key.toString() + " " + current.data.toString();
		return strrep;
	}

	public boolean searchKey (Key key){
		Node current = first;
		while (current != null){
			if(current.key == key) return true;
			current = current.next;
		}
		return false;
	}

	public Value getValue (Key key){
		Node current = first;
		while (current != null){
			if(current.key == key) return current.data;
			current = current.next;
		}
		return null;
	}

	public void updateKey (Key key, Value item){
		Node current = first;
		while (current != null){
			if (current.key == key){
				current.data = item;
				return;
			}
			current = current.next;
		}
	}

	public Iterator<Key> iterator() {
		return new LinkedIterator(first);
	}

	private class LinkedIterator implements Iterator<Key>{
		private Node current;

		public LinkedIterator(Node first){
			current = first;
		}
		public boolean hasNext(){
			return current != null;
		}
		public Key next(){
			if (!hasNext()) throw new NoSuchElementException();
			Key key = current.key;
			current = current.next;
			return key;
		}

	}



	public static void main(String args[]){
		LinkedList<String, Integer> lst = new LinkedList<>();
		for (String key: lst){
			//System.out.println(key);
		}
		lst.add("S",8);
		lst.add("A",7);
		// System.out.println(lst.size() + " " + lst.toString());
		lst.updateKey("A",0);
		for (String key: lst){
			System.out.println(key);
		}
		// System.out.println(lst.searchKey("A"));
		// System.out.println(lst.toString());
	}
	
}
  
