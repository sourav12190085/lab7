import java.util.*;

public class Solution<Key extends Comparable<Key>, Value> {
    private Node root;             // root of BST

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public Solution() {

    }

    /**
     * Returns true if this symbol table is empty.
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
        
       
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
      return size(root);
       
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null){
         return 0;
        }
        return 1 + size(x.left) + size(x.right);
        
       
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    // public boolean contains(Key key) {
       
    // }

    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if(key==null){ 
            throw new IllegalArgumentException("argument to contain is empty");
        }
        return get(key) != null;
    }
    public Value get(Key key) {
        return get(root, key);
    }
    

    private Value get(Node x, Key key) {
      
        if (key == null){ 
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (x == null){  
            return null;
        }
        while(x!= null){
            int cmp = key.compareTo(x.key);

            if (cmp < 0) 
                return get(x.left, key);

            else if (cmp > 0) 
                return get(x.right, key);

            else              
                return x.val;
        }
        return null;

       
        
        
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        //assert check();
       
    }
    
    private Node put(Node x, Key key, Value val) {
        if (x == null) 
            return new Node(key, val, 1);

        int cmp = key.compareTo(x.key);

        if      (cmp < 0) 
            x.left  = put(x.left,  key, val);

        else if (cmp > 0) 
            x.right = put(x.right, key, val);

        else             
            x.val   = val;

        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
        //assert check();
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = delete(x.left,  key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else { 
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        } 
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    } 
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
        //assert check();
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }
   
     

    // private Node put(Node x, Key key, Value val) {
        
    // }

    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    } 

    private Node min(Node x) { 
        if (x.left == null) 
            return x; 
        else               
            return min(x.left); 
    } 
   

   

    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param  key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        Node x = floor(root, key);

        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else 
            return x.key;
    } 



    // private Node floor(Node x, Key key) {
    //     return null;
       
    // } 
    private Node floor(Node x, Key key) {
        if (x == null) 
            return null;

        int cmp = key.compareTo(x.key);
        if (cmp == 0) 
            return x;

        if (cmp <  0) 
            return floor(x.left, key);

        Node t = floor(x.right, key); 
        if (t != null) 
            return t;

        else 
            return x; 
    } 
    
    

    /**
     * Return the key in the symbol table whose rank is {@code k}.
     * This is the (k+1)st smallest key in the symbol table.
     *
     * @param  k the order statistic
     * @return the key in the symbol table of rank {@code k}
     * @throws IllegalArgumentException unless {@code k} is between 0 and
     *        <em>n</em>–1
     */
    public Key select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(root, rank);
    }
    private Key select(Node x, int rank) {
        if (x == null) return null;
        int leftSize = size(x.left);
        if (leftSize > rank) 
            return select(x.left,  rank);

        else if (leftSize < rank) 
            return select(x.right, rank - leftSize - 1); 

        else                      
            return x.key;
    }
    

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        ArrayList<Key> arr = new ArrayList<Key>();
        keys(root, arr, lo, hi);
        return arr;
    } 

    private void keys(Node x, ArrayList<Key> arr, Key lo, Key hi) { 
        if (x == null) return; 
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) 
            keys(x.left, arr, lo, hi);

        if (cmplo <= 0 && cmphi >= 0) 
            arr.add(x.key);

        if (cmphi > 0) 
            keys(x.right, arr, lo, hi); 
    } 
  
    public static void main(String[] args) { 

        Solution<String, Integer> BST = new Solution<String,Integer>();
        assert(BST.isEmpty()==true);
        BST.put("sourav",1);
        BST.put("upsana",2);
        BST.put("sonam",3);
        BST.put("hozier",4);
        BST.put("blackpink",5);
        BST.put("edsheeran",6);
        BST.put("zayn",7);
        System.out.println(BST.get("upsana"));
        System.out.println(BST.get("zayn"));
        System.out.println(BST.size());
        System.out.println(BST.min());
        System.out.println(BST.floor("zayn"));
        System.out.println(BST.floor("upsana"));
        System.out.println(BST.select(2));
        System.out.println(BST.keys("sourav","upsana"));
        System.out.println("All test case pass");
        
       
    }
}