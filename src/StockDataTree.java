public class StockDataTree {

    private static class StockDataNode {
        long timestamp;
        Float price;
        StockDataNode left, middle, right;
        StockDataNode p;

        StockDataNode() {
        }

        StockDataNode(long timestamp, Float price) {
            this.timestamp = timestamp;
            this.price = price;
//            this.stockData = new StockData(timestamp, price);
            left = middle = right = null;
        }

        public void updateKeys(StockDataNode other){
            this.timestamp = other.timestamp;
            this.price = other.price;
        }

        public boolean isLeaf() {
            return this.left == null && this.middle == null && this.right == null;
        }
    }

    private StockDataNode root;

    public StockDataTree() {
        root = new StockDataNode();
        StockDataNode left = new StockDataNode();
        StockDataNode middle = new StockDataNode();

        left.timestamp = Long.MIN_VALUE;
        middle.timestamp = Long.MIN_VALUE;

        left.p = middle.p = root;

        root.timestamp = Long.MIN_VALUE;
        root.left = left;
        root.middle = middle;
    }

    private StockDataNode find(long timestamp) {
        if (root == null) {
            return null;
        }
        else if (root.timestamp == timestamp) {
            return root;
        }
        else {
            return findRecursive(root, timestamp);
        }
    }

    private StockDataNode findRecursive(StockDataNode stockDataNode, long timestamp) {
        if (stockDataNode.isLeaf()) {
            if (stockDataNode.timestamp == timestamp) {
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
        StockDataNode newNode = new StockDataNode(timestamp, price);
        StockDataNode y = root;

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
    }

    private StockDataNode insertAndSplit(StockDataNode node, StockDataNode newChild){
        StockDataNode left = node.left;
        StockDataNode middle = node.middle;
        StockDataNode right = node.right;

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

    public boolean isNew(){
        return root.left.timestamp == Long.MIN_VALUE && root.middle.timestamp == Long.MIN_VALUE ;
    }

    public void delete(long timestamp){
        StockDataNode x = find(timestamp);
        if(x == null){
            throw new IllegalArgumentException("StockData not found");
        }

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
     * @param parent
     * @param left
     * @param middle
     * @param right
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
}
