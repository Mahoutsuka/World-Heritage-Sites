package heritage; 

public interface SymbolTable<Key, Value>{

    /* Returns true if the table is empty */
    public abstract boolean isEmpty();

    /* Returns the number of keys */
    public abstract int size();

    /* Inserts the key-value pair into the table
     * Updates if key already exists 
     */
    public abstract void put(Key key, Value val);

    /* Returns the value paired with the key
     * Returns null if the key is not in the table  */
    public abstract Value get(Key key);
}