/*
public class Tree<T extends ExerciseObject> {

    private static class Node<T extends ExerciseObject> {
        T key;
        Node<T> left, middle, right;
        Node<T> p;

        Node() {
        }

        Node(T key) {
            this.key = key;
//            this.stockData = new StockData(timestamp, price);
            left = middle = right = null;
        }

        public void updateKeys(Node<T> other){
            this.key = other.key;
        }

        public boolean isLeaf() {
            return this.left == null && this.middle == null && this.right == null;
        }
    }

    private Node<T> root;

    public Tree() {
        root = new Node<T>();
        Node<T> left = new Node<T>();
        Node<T> middle = new Node<T>();

        left.key = null;
        middle.key = null;

        left.p = middle.p = root;

        root.key = null;
        root.left = left;
        root.middle = middle;
    }

    private Node<T> find(T key) {
        if (root == null) {
            return null;
        }
        else if (root.key.isEqual(key)) {
            return root;
        }
        else {
            return findRecursive(root, key);
        }
    }

    private Node<T> findRecursive(Node<T> stockDataNode, T key) {
        if (stockDataNode.isLeaf()) {
            if (stockDataNode.key.isEqual(key)) {
                return stockDataNode;
            } else {
                return null;
            }
        }

        if (timestamp <= stockDataNode.timestamp) {
            return findRecursive(stockDataNode.left, timestamp);
        }
        else if (timestamp <= stockDataNode.middle.timestamp) {
            return findRecursive(stockDataNode.middle, timestamp);
        }
        else {
            return findRecursive(stockDataNode.right, timestamp);
        }
    }

    public void insert(long timestamp, Float price) {
        Node<T> newNode = new Node<T>(timestamp, price);
        Node<T> y = root;

        while(!y.isLeaf()){
            if(timestamp < y.timestamp){
                y = y.left;
            }
            else if(timestamp < y.middle.timestamp){
                y = y.middle;
            }
            else{
                y = y.right;
            }
        }

        Node<T> x = y.p;
        newNode = insertAndSplit(x, newNode);

        while (x != root){
            x = x.p;
            if(newNode != null){
                newNode = insertAndSplit(x, newNode);
            }
            else {
                updateKey(x);
            }
        }

        // Split the root
        if(newNode != null){
            Node<T> newRoot = new Node<T>();
            setChildren(newRoot, x, newNode, null);
            root = newRoot;
        }
    }

    private Node<T> insertAndSplit(Node<T> node, Node<T> newChild){
        Node<T> left = node.left;
        Node<T> middle = node.middle;
        Node<T> right = node.right;

        if(right == null){
            if(newChild.timestamp < left.timestamp){
                setChildren(node, newChild, left, middle);
            }
            else if(newChild.timestamp < middle.timestamp){
                setChildren(node, left, newChild, middle);
            }
            else{
                setChildren(node, left, middle, newChild);
            }

            return null;
        }

        Node<T> y = new Node<T>();

        if(newChild.timestamp < left.timestamp){
            setChildren(node, newChild, left, null);
            setChildren(y, middle, right, null);
        }
        else if(newChild.timestamp < middle.timestamp){
            setChildren(node, left, newChild, null);
            setChildren(y, middle, right, null);
        }
        else if(newChild.timestamp < right.timestamp){
            setChildren(node, left, middle, null);
            setChildren(y, newChild, right, null);
        }
        else{
            setChildren(node, left, middle, null);
            setChildren(y, right, newChild, null);
        }

        return y;
    }

    public void delete(T key){
        Node<T> x = find(key);
        if(x == null){
            throw new IllegalArgumentException("StockData not found");
        }

        Node<T> y = x.p;

        if(x == y.left){
            setChildren(y, y.middle, y.right, null);
        }
        else if(x == y.middle){
            setChildren(y, y.left, y.right, null);
        }
        else{
            setChildren(y, y.left, y.middle, null);
        }

        while(y != null){
            if(y.middle != null){
                updateKey(y);
                y = y.p;
            }
            else {
                if (y != root) {
                    y = borrowOrMerge(y);
                }
                else {
                    root = y.left;
                    y.left.p = null;
                    return;
                }
            }
        }
    }

    private Node<T> borrowOrMerge(Node<T> y){
        Node<T> z = y.p;
        if (y == z.left){
            Node<T> x = z.middle;
            if(x.right != null){
                setChildren(y, y.left, x.left, null);
                setChildren(x, x.middle, x.right, null);
            }
            else{
                setChildren(x, y.left, x.left, x.middle);
                setChildren(z, x, z.right, null);
            }
            return z;
        }
        if (y == z.middle){
            Node<T> x = z.left;
            if(x.right != null){
                setChildren(y, x.right, y.left, null);
                setChildren(x, x.left, x.middle, null);
            }
            else{
                setChildren(x, x.left, x.middle, y.left);
                setChildren(z, x, z.right, null);
            }
            return z;
        }

        Node<T> x = z.middle;
        if (x.right != null){
            setChildren(y, x.right, y.left, null);
            setChildren(x, x.left, x.middle, null);
        }
        else{
            setChildren(x, x.left, x.middle, y.left);
            setChildren(z, z.left, x, null);
        }

        return z;
    }

    */
/**
     * Update the key of the node
     * The key of a node is the maximum key of its children
     * @param node the node to update the key
     *//*

    private void updateKey(Node<T> node){
        node.updateKeys(node.left);

        if(node.middle != null){
            node.updateKeys(node.middle);
        }
        if(node.right != null){
            node.updateKeys(node.right);
        }
    }

    */
/**
     *
     * @param parent
     * @param left
     * @param middle
     * @param right
     *//*

    private void setChildren(Node<T> parent, Node<T> left, Node<T> middle, Node<T> right){
        parent.left = left;
        parent.middle = middle;
        parent.right = right;

        left.p = parent;
        if(middle != null){
            middle.p = parent;
        }
        if(right != null){
            right.p = parent;
        }

        updateKey(parent);
    }

    public Float getFinalPrice(){
        if (root.timestamp != Long.MIN_VALUE){
            return root.price;
        }
        throw new IllegalArgumentException("No stock data available");
    }
}
*/
