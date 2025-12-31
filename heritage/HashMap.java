package heritage;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * A Hashmap that stores the coordinate and data of each site
 * The coordinate being the key and the data being the value
 */

public class HashMap <Key,Value> implements Iterable<Key>, SymbolTable<Key,Value>{
	private static int M = 113;
	private static final int INIT_CAPACITY = 150;
	private LinkedList<Key,Value> [] table;
	private int numPairs = 0;

	// initializes an array with each index storing a linked list
	public HashMap(){
		LinkedList<Key, Value>[] temp = (heritage.LinkedList<Key, Value>[]) new heritage.LinkedList[INIT_CAPACITY];
		table = temp;
		for (int i = 0; i < INIT_CAPACITY; i ++){
			table[i] = new LinkedList <Key, Value> ();
		}
	}


	public int size(){
		return numPairs;
	}

	public boolean isEmpty(){
		return numPairs == 0;
	}

	// uses the hashcode of the key to find the linked-list the key-value pair would be
	public Value get(Key key){
		int hashcode = key.hashCode();
		hashcode = Math.abs(hashcode % INIT_CAPACITY);
		LinkedList<Key,Value> bucket = table[hashcode];
		return bucket.getValue(key);
	}

	// uses the hashcode of the key to place the key-value pair in a linked-list
	public void put (Key key, Value value){
		int hashcode = key.hashCode();
		hashcode = Math.abs(hashcode % INIT_CAPACITY);
		LinkedList<Key,Value> bucket = table[hashcode];
		if (bucket.searchKey(key)){
			bucket.updateKey(key, value);
		}
		else{
			bucket.add(key, value);
			numPairs ++;
		}
	}

	public Iterator<Key> iterator(){
		return new TableIterator(0);
	}
	private class TableIterator implements Iterator<Key>{
		private int indexBucket;
		private Iterator<Key> LinkedIterator;
		private int first = 0;
		private boolean flag = true;

		public TableIterator(int first){
			indexBucket = first;
			nonEmptyBucket();
		}
		public boolean hasNext(){
			return flag;
		}

		//skips empty linkedlists and finds the next non-empty linkedlist
		public void nonEmptyBucket(){
			while (indexBucket < table.length){
				LinkedIterator = table[indexBucket].iterator();
				if (LinkedIterator.hasNext()) return;
				indexBucket++;

			}
			flag = false;
		}


		public Key next(){
			if(!hasNext()) throw new NoSuchElementException();
			Key key = LinkedIterator.next();
			if (!LinkedIterator.hasNext()){
				indexBucket ++;
				nonEmptyBucket();
			}
			return key;
		}	

	}



	public static void main(String args[]){
		HashMap<String, Integer> x = new HashMap<String, Integer>();
		x.put("A", 2);
		x.put("B", 3);
		x.put("A", 3);
		System.out.println(x.get("B"));
		for(String key: x){
			System.out.println(key);
		}
	}

}
