package GenericLinearProbing;

@SuppressWarnings("unchecked")
public class HashTableGeneric<Key,Value> {

    private final int TABLE_SIZE=10;

    private Key keys[];
    private Value values[];
    private int capacity; //this is the max els hastable can hold
    private int numOfitems;

    public HashTableGeneric(){
        keys=(Key[])new Object[TABLE_SIZE]; //we cannot instantiate a generic array so we have to create Object array & cast it
        values=(Value[])new Object[TABLE_SIZE];
        capacity=TABLE_SIZE;
        numOfitems=0;
    }

    public HashTableGeneric(int newCapacity){
        //this will get used if we want to resize
        keys=(Key[])new Object[newCapacity]; //we cannot instantiate a generic array so we have to create Object array & cast it
        values=(Value[])new Object[newCapacity];
        capacity=newCapacity;
        numOfitems=0;
    }

    public int size(){
        return this.numOfitems;
    }
    private boolean isEmpty(){
        return numOfitems==0;
    }


    private int hash(Key key){
        return Math.abs(key.hashCode())% capacity;
    }

    public Value get(Key key){
        if(key==null)return null;

        int index=hash(key);

        while(keys[index]!=null){
            if(keys[index].equals(key)){
                return values[index]; //key found at index so return value at same index
            }
            index=(index+1)%capacity; //linear probing so go for next slot and check
        }

        return null;
    }

    public void put(Key key,Value val){

        if(key==null || val==null)return;

        //0.75 is load factor which means that if table is 3/4th full then we have to resize it and make it twice its size
        if(numOfitems>=capacity*0.75){
            System.out.println("Doubling the size of table...");
            resize(2*capacity); //this will make complexity O(n)
        }

        int index=hash(key);
        while(keys[index]!=null){
            //update scenario where key is already present
            if(keys[index].equals(key)){
                values[index]=val; //update value
                return;
            }
            index=(index+1)%capacity;
        }
        //we have found a slot
        keys[index]=key;
        values[index]=val;

        numOfitems++;
    }

    public void remove(Key key) {
        if (key == null) return;

        int index = hash(key);
        while (!keys[index].equals(key)) {
            index = (index + 1) % capacity;
        }
        keys[index] = null;
        values[index] = null;
        numOfitems--;
        //after removing an item there will be a whole in that cluster ..so we need to shift item up
        //get method won't function properly as it checks for consecutive slots after getting the index

        //reconstruct-moving up the item
        index=(index+1)%capacity;
        while (keys[index] != null) {
            //eg. stored keys 1 11 121 then they will be store at consecutive index after 1 ...if we delete 11..it will create a
            //whole ...so next time when we try to find 121..it won't work
            //so we move 121 up...to maintain the cluster
            Key tempkey = keys[index];
            Value tempval = values[index];

            keys[index] = null;
            values[index] = null;

            numOfitems--; //we reduce item count ...because we have set that index null..put will reincrement it
            put(tempkey, tempval);

            index = (index + 1) % capacity;
        }

        if(numOfitems<=capacity/3){
            System.out.println("Halving the size of the table..");
            //if 33% or less than that percent of table is full..then we resize..to save space
            resize(capacity/2); //eg say we have 100 size and only 20 elements are present..so make table of size 50
        }
    }

    //O(N)
   private void resize(int newcapacity){
        //create a new hashtable and re-hash old table's values & put it in new

        HashTableGeneric<Key,Value>newTable=new HashTableGeneric<>(newcapacity);
        //here capacity is orignal table's capacity
        for(int i=0;i<capacity;i++){
            if(keys[i]!=null){
                newTable.put(keys[i],values[i]); //put key-val pairs in newtable
            }
        }
        //now newtable is prepared so change orig table's values to new table's
        keys=newTable.keys;      //change keys values and capacity of orignal table to newtable's
        values=newTable.values;
        capacity=newTable.capacity;

    }

}
