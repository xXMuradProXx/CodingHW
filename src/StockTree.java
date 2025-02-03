public class StockTree {

    private StockNode root;

    private boolean isLeaf(StockNode node) {
        return node == null || node.left == null;
    }

    public StockTree() {
        root = new StockNode();
        StockNode left = new StockNode();
        StockNode middle = new StockNode();

        left.stock = new Stock(Stock.MIN_ID);
        middle.stock = new Stock(Stock.MAX_ID);

        setChildren(root, left, middle, null);
    }

    // might remove it because root is always initialized with a sentinel
    public StockNode find(String stockId) {
        return findRecursive(root, stockId);
    }

    private StockNode findRecursive(StockNode stockNode, String stockId) {
        if (isLeaf(stockNode)) {
            if (compareStocks(stockNode.getStockId(), stockId) == 0) {
                return stockNode;
            } else {
                return null;
            }
        }

        if (compareStocks(stockId, stockNode.left.getStockId()) <= 0) {
            return findRecursive(stockNode.left, stockId);
        }
        else if (compareStocks(stockId, stockNode.middle.getStockId()) <= 0) {
            return findRecursive(stockNode.middle, stockId);
        }
        else {
            return findRecursive(stockNode.right, stockId);
        }
    }

    public void insert(Stock stock) {
        StockNode newNode = new StockNode(stock);
        StockNode y = this.root;

        while (!isLeaf(y)) {
            if (compareStocks(stock, y.left.stock) < 0) {
                y = y.left;
            } else if (compareStocks(stock, y.middle.stock) < 0 || stock.getStockId() == null) {
                y = y.middle;
            } else {
                y = y.right;
            }
        }

        StockNode x = y.p;
        newNode = insertAndSplit(x, newNode);

        while (x != root) {
            x = x.p;
            if (newNode != null) {
                newNode = insertAndSplit(x, newNode);
            } else {
                updateKey(x);
            }
        }

        // Split the root
        if (newNode != null) {
            StockNode newRoot = new StockNode();
            setChildren(newRoot, x, newNode, null);
            root = newRoot;
        }
    }

    private StockNode insertAndSplit(StockNode node, StockNode newChild){
        StockNode left = node.left;
        StockNode middle = node.middle;
        StockNode right = node.right;

        if(right == null){
            if(compareStocks(newChild.stock, left.stock) < 0){
                setChildren(node, newChild, left, middle);
            }
            else if(compareStocks(newChild.stock, middle.stock) < 0){
                setChildren(node, left, newChild, middle);
            }
            else{
                setChildren(node, left, middle, newChild);
            }

            return null;
        }

        StockNode y = new StockNode();

        if(compareStocks(newChild.stock, left.stock) < 0){
            setChildren(node, newChild, left, null);
            setChildren(y, middle, right, null);
        }
        else if(compareStocks(newChild.stock, middle.stock) < 0){
            setChildren(node, left, newChild, null);
            setChildren(y, middle, right, null);
        }
        else if(compareStocks(newChild.stock, right.stock) < 0){
            setChildren(node, left, middle, null);
            setChildren(y, newChild, right, null);
        }
        else{
            setChildren(node, left, middle, null);
            setChildren(y, right, newChild, null);
        }

        return y;
    }

    public void delete(StockNode x){
        StockNode y = x.p;

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

    private StockNode borrowOrMerge(StockNode y){
        StockNode z = y.p;
        if (y == z.left){
            StockNode x = z.middle;
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
            StockNode x = z.left;
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

        StockNode x = z.middle;
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
    private void updateKey(StockNode node){
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
    private void setChildren(StockNode parent, StockNode left, StockNode middle, StockNode right){
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

    protected int compareStocks(Stock stock1, Stock stock2){
        if (stock1 == null || stock2 == null) {
            throw new IllegalArgumentException();
        }

        if (stock1.getStockId() == Stock.MAX_ID) {
            if (stock2.getStockId() == Stock.MAX_ID) {
                return 0;
            }
            else {
                return 1;
            }
        }
        else if (stock2.getStockId() == Stock.MAX_ID) {
            return -1;
        }

        return stock1.getStockId().compareTo(stock2.getStockId());
    }

    private int compareStocks(String stockId1, String stockId2){
        if (stockId1 == null) {
            return stockId2 == null ? 0 : 1;
        }
        else if (stockId2 == null) {
            return -1;
        }

        return stockId1.compareTo(stockId2);
    }

    public void printTree(){
        printTreeRecursive("", root, 0);
    }

    private void printTreeRecursive(String child, StockNode node, int level){
        if(node == null){
            return;
        }

        for (int i = 0; i <= level; i++) {
            System.out.print("  ");
        }

        System.out.println(child + "Node: " + node.stock.getStockId());
        System.out.print("                {" );
        node.stock.getStockData().printTree();

        printTreeRecursive("L: ", node.left, level+1);
        printTreeRecursive("M: ", node.middle, level+1);
        printTreeRecursive("R: ", node.right, level+1);
    }

/*    public StockNode successor(StockNode node) {
        StockNode parent = node.p;
        StockNode temp = node;
        while ((temp == parent.right || (parent.right == null && temp == parent.middle))) {
            temp = parent;
            if(parent == this.root){
                break;
            }
            parent = parent.p;
        }

        StockNode y;
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
//        if ()
        return null;
    }

    public StockNode predecessor(StockNode node) {
        StockNode parent = node.p;
        StockNode temp = node;

        while (temp != root && temp == parent.left) {
            temp = parent;
            parent = parent.p;
        }

        StockNode y;
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
    }*/
}
