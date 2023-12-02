
public class IntegerArrayList implements IntegerList
{
    private int[] storage;
    private int   count;
    
    public IntegerArrayList() {
        storage = new int[100];
        count = 0;
    }

    public void add (int value) {
        addBack(value);
    }
    
	public void addBack (int value) {
	    // broken because what happens when
	    // we add the 101st element????
	    storage[count] = value;
	    count = count + 1;
	}
	
	public void addFront(int value) {
	    // broken because doesn't handle
	    // full storage array
		// fixed
		if(count<storage.length){
			for (int i = count; i > 0 ; i--) {
				storage[i] = storage[i-1];
			}
		}else if(count == storage.length){
			int[] newStorage = new int[count*2];
			
			for(int j = 0; j<storage.length; j++){
				newStorage[j] = storage[j];
			}
			
			storage = newStorage;
			for (int k = count; k > 0 ; k--) {
            storage[k] = storage[k-1];
			}
		}
	    storage[0] = value;
	    count++;
	}
	
	public int size(){
	    return count;
	}
	public void clear(){
	    count = 0;
	}
	public int get (int index) {
	    return storage[index];
	}
	
	public String toString() {
	    String s = "{";
	    for (int i = 0; i < count; i++) {
	        s+= storage[i];
	        if (i != count - 1) {
	            s+= ",";
	        }
	    }
	    s+= "}";
	    return s; 
	}
}
