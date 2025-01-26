public class StockDataTree {

    //TODO:

    private StockDataNode root;

    private boolean isLeaf(StockDataNode node) {
        return node == null || node.left == null || node.isLeaf();
    }

    public StockDataTree() {
        root = new StockDataNode();
        StockDataNode left = new StockDataNode();
        StockDataNode middle = new StockDataNode();

        left.timestamp = Long.MIN_VALUE;
        middle.timestamp = Long.MAX_VALUE;

        setChildren(root, left, middle, null);
    }

    // might remove it because root is always initialized with a sentinel
    private StockDataNode find(long timestamp) {
        return findRecursive(root, timestamp);
    }

    private StockDataNode findRecursive(StockDataNode stockDataNode, long timestamp) {
        if (stockDataNode.isLeaf()) {
            if (stockDataNode.timestamp == timestamp) {
                return stockDataNode;
            } else {
                return null;
            }
        }

        if (timestamp <= stockDataNode.left.timestamp) {
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
        StockDataNode newNode = new StockDataNode(timestamp, price);

        System.out.println("Inserting stockData with timestamp: " + timestamp + " and price: " + price);
        printTree();

        StockDataNode y = this.root;

        while(y != null && !y.isLeaf()){
            if(timestamp < y.left.timestamp){
                System.out.println("y=y.left");
                y = y.left;
            }
            else if(timestamp < y.middle.timestamp || timestamp == Long.MAX_VALUE){
                System.out.println("y=y.middle");
                y = y.middle;
            }
            else{
                System.out.println("y=y.right");
                y = y.right;
            }
        }

        System.out.println("Arrived to leaf node");

        StockDataNode x = y.p;
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
            StockDataNode newRoot = new StockDataNode();
            setChildren(newRoot, x, newNode, null);
            root = newRoot;
        }

        System.out.println("After inserting stockData with timestamp: " + timestamp + " and price: " + price);
        printTree();
        System.out.println("-----------------------------------------------------------------------------");

    }

    private StockDataNode insertAndSplit(StockDataNode node, StockDataNode newChild){
        StockDataNode left = node.left;
        StockDataNode middle = node.middle;
        StockDataNode right = node.right;

        if(right == null){
            System.out.println("right child is null");
            if(newChild.timestamp < left.timestamp){
                System.out.println("newChild < left");
                setChildren(node, newChild, left, middle);
            }
            else if(newChild.timestamp < middle.timestamp){
                System.out.println("newChild < middle");
                setChildren(node, left, newChild, middle);
            }
            else{
                System.out.println("inserting newChild to the right");
                setChildren(node, left, middle, newChild);
            }

            return null;
        }

        StockDataNode y = new StockDataNode();

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

    public void delete(long timestamp){
        StockDataNode x = find(timestamp);
        if(x == null){
            throw new IllegalArgumentException("timestamp not found");
        }

        System.out.println(x.timestamp + "  " + x.price);
        System.out.println(x.p.timestamp + "  " + x.p.price);

        StockDataNode y = x.p;

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

    private StockDataNode borrowOrMerge(StockDataNode y){
        StockDataNode z = y.p;
        if (y == z.left){
            StockDataNode x = z.middle;
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
            StockDataNode x = z.left;
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

        StockDataNode x = z.middle;
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

    /**
     * Update the key of the node
     * The key of a node is the maximum key of its children
     * @param node the node to update the key
     */
    private void updateKey(StockDataNode node){
        node.updateKeys(node.left);

        if(node.middle != null){
            node.updateKeys(node.middle);
        }
        if(node.right != null){
            node.updateKeys(node.right);
        }
    }

    /**
     *
     * @param parent the parent node
     * @param left the left child
     * @param middle the middle child
     * @param right the right child
     */
    private void setChildren(StockDataNode parent, StockDataNode left, StockDataNode middle, StockDataNode right){
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

    public void printTree(){
        printTreeRecursive("", root, 0);
    }

    private void printTreeRecursive(String child, StockDataNode node, int level){
        if(node == null){
            return;
        }

        for (int i = 0; i <= level; i++) {
            System.out.print("  ");
        }

        System.out.println(child + "Node: " + node.timestamp + "  " + node.price);

        printTreeRecursive("L: ", node.left, level+1);
        printTreeRecursive("M: ", node.middle, level+1);
        printTreeRecursive("R: ", node.right, level+1);
    }

    public StockDataNode successor(StockDataNode node) {
        StockDataNode parent = node.p;
        StockDataNode temp = node;
        while ((temp == parent.right || (parent.right == null && temp == parent.middle))) {
            temp = parent;
            if(parent == this.root){
                break;
            }
            parent = parent.p;
        }

        StockDataNode y;
        if (temp == parent.left) {
            y = parent.middle;
        } else {
            y = parent.right;
        }

        while (!isLeaf(y)) {
            y = y.left;
        }

        if (y.timestamp < Long.MAX_VALUE) {
            return y;
        }
        return null;
    }

    public StockDataNode predecessor(StockDataNode node) {
        StockDataNode parent = node.p;
        StockDataNode temp = node;

        while (temp != root && temp == parent.left) {
            temp = parent;
            parent = parent.p;
        }

        StockDataNode y;
        if (parent != null) {
            if (temp == parent.right) {
                y = parent.middle != null ? parent.middle : parent.left;
            } else {
                y = parent.left;
            }
        } else {
            y = temp;
        }

        while (!isLeaf(y)) {
            if (y.right != null) {
                y = y.right;
            } else {
                y = y.middle;
            }
        }

        if (y.timestamp > Long.MIN_VALUE) {
            return y;
        }

        return null;
    }
}
