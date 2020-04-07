/*
* Double hashing, double indexing, and quadratic probing in this HashTable
* Author: Cormac Dacker (cxd289)
*  */
import java.io.*;
import java.util.*;

public class HashTable {
    private int capacity =10;
    private int seed = 42;
    private int size = 0;
    private int resize = 2;
    private int rehashed = 0;
    private int doubleHashed = 0;
    private int maxVals = 10;
    private int keyIndex = 0;
    private int valueIndex = 1;
    private Random rand = new Random(seed);
    private String[] keys = new String[capacity];
    private String[] values = new String[capacity];
    private String[][][] table = new String[capacity][2][maxVals];

    private HashTable(){
        this.capacity = (1000);
        this.table = new String[capacity][2][maxVals]; // redo dec
    }

    public static void main(String[] args){
        HashTable ht = new HashTable();

        // add 10 people
        ht.put("Cormac", "Quentin");
        ht.put("Amulya", "Olivia");
        ht.put("Olivia", "Marilyn");
        ht.put("Marilyn", "Shanti");
        ht.put("Shanti", "Mac");
        ht.put("Mac", "Lucia");
        ht.put("Lucia", "Joaquin");
        ht.put("Joaquin", "Sophie");
        ht.put("Sophie", "Zack");
        ht.put("Zack", "Catlin");
        ht.put("Catlin", "Yutong");

        // add one person, plus many colaborators
        ht.put("Quentin", "Olivia");
        ht.put("Quentin", "Quentin");
        ht.put("Quentin", "Yuton");
        ht.put("Quentin", "Caitlin");
        ht.put("Quentin", "Shanti");
        ht.put("Quentin", "Joaquin");
        ht.put("Quentin", "Lucia");
        ht.put("Quentin", "Marilyn");
        ht.put("Quentin", "Mac");
        ht.put("Quentin", "Zack");
        ht.put("Quentin", "Sophie");
        ht.put("Quentin", "Cormac");
        ht.put("Quentin", "Cormac"); // dublicate check



        System.out.print(ht.toString());

    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder(new String(""));
        for (int i = 0; i < capacity -1; i++){
            if (this.table[i][keyIndex][keyIndex] != null){ // if index not null
                out.append("{").append(this.table[i][keyIndex][keyIndex]).append("\n\t")
                        .append(Arrays.toString(this.table[i][valueIndex])).append("}\n");
            }
        }
        return out.toString();
    }

    // the core of the hash function
    private int h0(String key){
        int out = 0;
        for(int i = 0; i<key.length();i++){
            out += Character.getNumericValue(key.charAt(i));
        }
        return out;
    }

    // primary hash, used before h2
    private int h1(String key){
        return h0(key) % (this.capacity-1);
    }

    // primary hash exhausted, use double hashing
    private int h2(String key){
        return h0(key) % rand.nextInt();
    }

    // tells the program when to double hash and when to rehash
    private boolean cutoff(){
        return this.size >= (int)(this.capacity*.8);
    }

    // finds location for key using quadratic probing
    private int quadProbe(String key, int i){
        return (i * h1(key) + h2(key)) % capacity;
    }

    // resolve collision by quadratic probing method
    private int doubleHashing(String key){
        int posFound = -1;
        // sets limit slightly less then capacity
        int limit = (int) (this.capacity * .8);
        int i = 2;
        int newPos = 0;

        while (i <= limit){
            newPos = this.quadProbe(key,i);
            if (this.table[newPos][keyIndex] != null){
                posFound = newPos;
                break;
            } else {
                i++;
            }
        }
        if (posFound!= -1){
            this.doubleHashed++;
        }
        return posFound;
    }

    // searches for an entry in the table
    // returns position of entry if found
    private int search(String key){
        List<Integer> ls = new ArrayList<Integer>();

        if(!Arrays.asList(this.keys).contains(key)){
            return -1;
        }
        ls.add(this.h1(key));
        for(int i =2; i< (int) ((this.capacity*80)/100); i++){
            int idx = this.quadProbe(key,i);
            if (!ls.contains(idx)){
                ls.add(idx);
            }
        }
        for (int i : ls) {
            String[] entry = this.table[i][keyIndex];
            if (entry[keyIndex].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    private String[] get(String key){
        int pos = this.search(key);
        if (pos != -1){
            return this.table[pos][keyIndex];
        } else {
            return null;
        }
    }

    // increase the size of the table
    private void rehash(){
        this.capacity *= this.resize;
        this.rehashed ++;
        this.table = new String[this.capacity][2][this.maxVals];
        String[] vals = this.values.clone();
        String[] kys = this.keys.clone();
        this.keys = new String[this.capacity];
        this.values = new String[this.capacity];
        this.size= 0;
        for(int i =0; i<vals.length - 1 && kys[i] != null; i ++){
            this.put(kys[i],vals[i]);
        }
        System.out.print("Rehash complete\n");
    }

    // put an item in the hash table
    private void put(String key, String value) {
        if (this.needsResizing(this.keys)) {
            String[] k = this.keys.clone();
            String[] v = this.values.clone();
            this.keys = new String[k.length * 2];
            System.arraycopy(k, 0, this.keys, 0, k.length - 1);;
            this.values = new String[v.length * 2];
            System.arraycopy(v, 0, this.values, 0, v.length - 1);;
        }
        int location = this.h1(key);
        if (this.cutoff()) { // confirm there is room in table
            this.rehash();
            this.put(key, value);
        } else if (this.table[location][this.keyIndex][this.keyIndex] == null) { // location is empty
            this.table[location][this.keyIndex][this.keyIndex] = key;
            int valLoc = getEmptySpot(this.table[location][this.valueIndex]);
            this.table[location][this.valueIndex][valLoc] = value;
            this.values[this.size] = value; // this is prob wrong
            this.keys[this.size] = key;
            this.size++;
        } else if (this.table[location][this.keyIndex][this.keyIndex].equals(key)) {
            if(getSpotOf(this.table[location][valueIndex],value)!= -1){ // ensures no duplicate values
                return;
            }
            if (needsResizing(this.table[location][this.valueIndex])){
                this.table =  resize(this.table).clone(); // just value range, no rehash needed
            }
            int valLoc = getEmptySpot(this.table[location][this.valueIndex]);
            this.table[location][this.valueIndex][valLoc] = value;
            this.keys[this.size] = key;
            this.values[this.size] = value; // this is prob wrong
            this.size++;
        } else { // there needs to be a second try (aka double hash it, baby!)
            int pos = this.doubleHashing(key);
            if (pos != -1) { // double hashing worked!
                int valLoc = getEmptySpot(this.table[pos][this.valueIndex]);
                this.table[pos][keyIndex][keyIndex] = key;
                this.table[pos][this.valueIndex][valLoc] = value; // add key? TEMP
                this.values[this.size] = value;
                this.keys[this.size] = key;
                this.size++;
            } else { //failsafe
                this.rehash();
                this.put(key, value);
            }
        }

    }

    private boolean needsResizing(String[] ar){
        for(int i = 0; i < ar.length -1; i++){
            if (i > ((maxVals-1) * .8) && ar[i] != null){

                return true;
            }
        }
        return false;
    }

    // increase size of table to store more values (not keys)
    private String[][][] resize(String[][][] ar){
        this.maxVals *= this.resize;
        String[][][] out = new String[this.capacity][2][this.maxVals];
        if (ar.length - 1 >= 0) System.arraycopy(ar, 0, out, 0, ar.length - 1);
        System.out.print("Resizing complete\n");
        return out;
    }

    private int getEmptySpot(String[] ar){
        int i = 0;
        while (i < ar.length -1){
            if (ar[i] == null){
                return i;
            }
            i++;
        }
        return i;
    }

    private int getSpotOf(String[] ar, String search) {
        int i = 0;
        while (i < ar.length - 1) {
            if(ar[i] != null){
                if (ar[i].equals(search)) {
                    return i;
                }
            } else{
                break;
            }
            i++;
        }
        return -1;
    }

    private String[] removeVal(String[] vals, String toRemove){
        int loc = getSpotOf(vals, toRemove);
        if (loc == -1) {
            return vals;
        }
        String[] out = new String[vals.length];
        for (int i = 0; i < vals.length; i++) {
            if(i != loc){
                int pos = getEmptySpot(out);
                out[pos] = vals[i];
            }
        }
        return out;
    }

    private boolean remove(String key, String value){
        int pos = this.search(key);
        if (pos == -1){ // if key does not exist
            return false;
        }
        if (value != null){ // if it's a val is whats removed
            String[] vals = this.get(key);
            this.table[pos][valueIndex] = this.removeVal(vals, value);
            this.values = this.removeVal(this.values, value);
            this.size--;
        } else { // else remove the key
            // remove vals related to the key
//            for(int i = 0; i < this.table[pos][valueIndex].length; i++){
//                this.table[pos][valueIndex] = new String[maxVals];
//            }
            this.table[pos][valueIndex] = new String[maxVals];
            this.table[pos][keyIndex] = new String[2]; // IDK if this works TEMP
            this.size --;
            if (this.size != 0){
                rehash();
            }
        }
        return true;
    }

    private boolean has(String key){
        return (this.search(key) != -1);
    }
}