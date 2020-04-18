package GrandCentral;/*
 * Double hashing, double indexing, and quadratic probing in this HashTable
 * Author: Cormac Dacker (cxd289)
 *  */

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HashTable {
    private int capacity = 500000; //500k
    private int seed = 42;
    private int size = 0;
    private int resize = 2;
    private int mostVals = 0;
    private int mostValsIndex = -1;
    private int maxCapacity = 20000000; //20 million
    private Random rand = new Random(seed);
    private ArrayList<String> keys = new ArrayList<>();
    private ArrayList<String> values = new ArrayList<>();

    public ArrayList<Pair<String, ArrayList<String>>> getTable() {
        return table;
    }

    private ArrayList<Pair<String, ArrayList<String>>> table = new ArrayList<>();

    public HashTable() {
        buildTable();
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
        int stp = this.capacity;
        if (this.size > 100) {
            stp = 100;
        }
        StringBuilder out = new StringBuilder("");
        for (int i = 0; i < stp - 1; i++) {
            if (!this.table.get(i).getKey().equals("")) { //not null
                out.append(this.table.get(i).toString()).append("\n");
            }
        }
        return out.toString();
    }


    public int getMostVals() {
        return this.mostVals;
    }

    public int getMostValsIndex() {
        return this.mostValsIndex;
    }

    public String toString(int stp) {
        if (stp > this.capacity) {
            stp = this.capacity;
        }
        StringBuilder out = new StringBuilder("");
        for (int i = 0; i < stp - 1; i++) {
            if (this.table.get(i) != null) {
                out.append(this.table.get(i).toString()).append("\n");
            }
        }
        return out.toString();
    }

    private void buildTable() {
        for (int i = 0; i < this.capacity; i++) {
            this.table.add(new Pair<String, ArrayList<String>>("", new ArrayList<String>()));
        }
    }

    // the core of the hash function
    private int h0(String key) {
        int out = 0;
        for (int i = 0; i < key.length(); i++) {
            out += Character.getNumericValue(key.charAt(i));
        }
        return out;
    }

    // primary hash, used before h2
    private int h1(String key) {
        return h0(key) % (this.capacity - 1);
    }

    // primary hash exhausted, use double hashing
    private int h2(String key) {
        return h0(key) % rand.nextInt();
    }

    // tells the program when to double hash and when to rehash
    private boolean cutoff() {
        return this.size >= (int) (this.capacity * .8);
    }

    // finds location for key using quadratic probing
    private int quadProbe(String key, int i) {
        return (i * h1(key) + h2(key)) % capacity;
    }

    // resolve collision by quadratic probing method
    private int doubleHashing(String key) {
        int posFound = -1;
        // sets limit slightly less then capacity
        int limit = (int) (this.capacity * .8);
        int i = 2;
        int newPos = 0;

        while (i <= limit) {
            newPos = this.quadProbe(key, i);
            if (table.get(i) != null)
                if (this.table.get(newPos).getKey() != null) { // if key at pos is not null
                    posFound = newPos;
                    break;
                } else {
                    i++;
                }
        }
        return posFound;
    }

    // searches for an entry in the table
    // returns position of entry if found
    private int search(String key) {
        List<Integer> ls = new ArrayList<Integer>();

        if (!Arrays.asList(this.keys).contains(key)) {
            return -1;
        }
        ls.add(this.h1(key));
        for (int i = 2; i < ((this.capacity * 80) / 100); i++) {
            int idx = this.quadProbe(key, i);
            if (!ls.contains(idx)) {
                ls.add(idx);
            }
        }
        for (int i : ls) {
            String entry = this.table.get(i).getKey();
            if (entry.equals(key)) {
                return i;
            }
        }
        return -1;
    }

    private ArrayList<String> get(String key) {
        int pos = this.search(key);
        if (pos != -1) {
            return this.table.get(pos).getValue();
        } else {
            return null;
        }
    }

    // increase the size of the table
    private void rehash() {
        //System.out.print("Rehash Start\n");

        this.capacity *= this.resize;
        this.table = new ArrayList<>(this.capacity);
        buildTable();
        this.size = 0;
        for (int i = 0; i < this.values.size() && this.keys.get(i) != null; i++) {
            this.put(this.keys.get(i), this.values.get(i));
        }
        System.out.print("Rehash complete\n");
    }

    // takes a list of authors and puts them in with the differece as the value
    public void putList(String[] authorList) {
        if (authorList != null) { //null check
            for (String author : authorList) { //put each author in as key
                int len = author.split("\\s+").length;
                if (len <= 5 && len > 1) { //cut authors with names of 1 or +5 words
                    for (int i = 0; i < authorList.length - 1; i++) { // coworkers as vals
                        if (authorList[i] == author) { // if index on the author
                            continue;
                        } else {
                            this.put(author, authorList[i]);
                        }
                    }
                }
            }
        }
    }

    // put an item in the hash table
    private void put(String key, String value) {
//        if(this.size % 1000 == 0){
//            System.out.print(this.size);
//            System.out.print('\n');
//        }
        int location = this.h1(key);
        if (this.cutoff()) { // confirm there is room in table
            this.rehash();
            this.put(key, value);
        } else if (this.table.get(location).getKey().equals("")) { // location is empty
            if(this.size >= this.maxCapacity){ // ensures not at max cap
                return;
            }
            ArrayList<String> v = new ArrayList<String>();
            v.add(value);
            Pair<String, ArrayList<String>> p = new Pair<String, ArrayList<String>>(key, v);
            table.set(location, p);
            maintencence(key, value);
            this.size++;

        } else if (this.table.get(location).getKey().equals(key)) {
            if (this.table.get(location).getValue().contains(value)) { // ensures no duplicate values
                return;
            }

            this.table.get(location).getValue().add(value);
            maintencence(key, value);

            if (this.table.get(location).getValue().size() > this.mostVals) {
                this.mostVals = this.table.get(location).getValue().size();
                this.mostValsIndex = location;

            }

        } else { // there needs to be a second try (aka double hash it, baby!)
            if(this.size >= this.maxCapacity){ // ensures not at max cap
                return;
            }
            int pos = this.doubleHashing(key);
            if (pos != -1) { // double hashing worked!
                ArrayList<String> v = new ArrayList<String>();
                v.add(value);
                Pair<String, ArrayList<String>> p = new Pair<String, ArrayList<String>>(key, v);
                table.set(pos, p);

                maintencence(key, value);
                this.size++;

                if (this.table.get(pos).getValue().size() > this.mostVals) {
                    this.mostVals = this.table.get(pos).getValue().size();
                    this.mostValsIndex = pos;
                }
            } else { //failsafe
                this.rehash();
                this.put(key, value);
            }
        }

    }

    private void maintencence(String k, String v) throws OutOfMemoryError {
        this.values.add(v);
        this.keys.add(k);
    }

//    private int getEmptySpot(ArrayList<String> ar){
//        int i = 0;
//        while (i < ar.length -1){
//            if (ar.get(i) == null){
//                return i;
//            }
//            i++;
//        }
//        return i;
//    }

    private int getSpotOf(ArrayList<String> ar, String search) {
        int i = 0;
        while (i < ar.size() - 1) {
            if (ar.get(i) != null) {
                if (ar.get(i).equals(search)) {
                    return i;
                }
            } else {
                break;
            }
            i++;
        }
        return -1;
    }

//    private String[] removeVal(String[] vals, String toRemove){
//        int loc = getSpotOf(vals, toRemove);
//        if (loc == -1) {
//            return vals;
//        }
//        String[] out = new String[vals.length];
//        for (int i = 0; i < vals.length; i++) {
//            if(i != loc){
//                int pos = getEmptySpot(out);
//                out[pos] = vals[i];
//            }
//        }
//        return out;
//    }
//
//    private boolean remove(String key, String value){
//        int pos = this.search(key);
//        if (pos == -1){ // if key does not exist
//            return false;
//        }
//        if (value != null){ // if it's a val is whats removed
//            String[] vals = this.get(key);
//            this.table[pos][valueIndex] = this.removeVal(vals, value);
//            this.values.remove(value);
//            this.size--;
//        } else { // else remove the key
//            // remove vals related to the key
////            for(int i = 0; i < this.table[pos][valueIndex].length; i++){
////                this.table[pos][valueIndex] = new String[maxVals];
////            }
//            this.table[pos][valueIndex] = new String[maxVals];
//            this.table[pos][keyIndex] = new String[2]; // IDK if this works TEMP
//            this.size --;
//            if (this.size != 0){
//                rehash();
//            }
//        }
//        return true;
//    }
//
//    private boolean has(String key){
//        return (this.search(key) != -1);
//    }
}