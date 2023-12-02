// Name: 
// Student number: V00

public class MaxFrequencyHeap implements PriorityQueue {
	
	private static final int DEFAULT_CAPACITY = 10;
	private Entry[] data;
	private int size;
	
	public MaxFrequencyHeap() {
		data = new Entry[DEFAULT_CAPACITY];
		size = 0;
	}
	
	public MaxFrequencyHeap(int size) {
		data = new Entry[size];
		size = 0;
	}
	
	/* 	                  root at 0       root at 1
	Left child        index*2 + 1     index*2
	Right child       index*2 + 2     index*2 + 1
	Parent            (index-1)/2     index/2 
	*/
	public int leftChild(int index) {
		return index*2;
	}
	
	public int rightChild(int index) {
		return index*2 + 1;
	}
	
	public int parentNode(int index) {
		return index/2;
	}
	
	public void insert(Entry element) {
		// TODO: Complete this method
		size++;
		data[size] = element;
		int current = size; 
		insertHelper(current);			
		
	}
	
	public void insertHelper(int index) {
		if((index == 1) || (data[index].getFrequency() < data[parentNode(index)].getFrequency())){
			return;
		}else{
			Entry temp = data[index];
			data[index] = data[parentNode(index)];
			data[parentNode(index)] = temp; 
			insertHelper(parentNode(index));
		}
	}

	public Entry removeMax() { 
		// TODO: Complete this method
		if(size<2){
			int current = size;
			size--;
			return data[current];
		}
		Entry toRemove = data[1];
		data[1] = data[size];
		size--;
		removeHelper(1);
		return toRemove; 
	}
	
	public void removeHelper(int index) {
		if(index >= size/2 && index <= size){
			return;
		}
		
		if(data[index].getFrequency() < data[rightChild(index)].getFrequency() || data[index].getFrequency() < data[leftChild(index)].getFrequency()){
			if(data[rightChild(index)].getFrequency() < data[leftChild(index)].getFrequency()){
				Entry temp = data[index];
				data[index] = data[leftChild(index)];
				data[leftChild(index)] = temp;
				removeHelper(leftChild(index));
			}else{
				Entry temp = data[index];
				data[index] = data[rightChild(index)];
				data[rightChild(index)] = temp;
				removeHelper(rightChild(index));
			}
		}
	}
	
	public boolean isEmpty() {
		if(size == 0){
			return true;
		}
		return false;
	}
	
	public int size() {
		// TODO: Complete this method
		return size;
	}

}
 
