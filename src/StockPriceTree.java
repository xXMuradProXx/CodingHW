public class StockPriceTree {

    private StockPriceNode root;

    public StockPriceTree() {
        this.root = new StockPriceNode();

        StockPriceNode left = new StockPriceNode();
        StockPriceNode middle = new StockPriceNode();

        left.stock = new Stock(Stock.MIN_ID, Stock.MIN_PRICE);
        middle.stock = new Stock(Stock.MAX_ID, Stock.MAX_PRICE);

        setChildren(root, left, middle, null);
    }

    // might remove it because root is always initialized with a sentinel
    public StockPriceNode find(Stock stock) {
        return findRecursive(root, stock);
    }

    private StockPriceNode findRecursive(StockPriceNode stockPriceNode, Stock stock) {
        if (isLeaf(stockPriceNode)) {
            if (compareStocks(stockPriceNode.stock, stock) == 0) {
                return stockPriceNode;
            } else {
                return null;
            }
        }

        if (compareStocks(stock, stockPriceNode.left.stock) <= 0) {
            return findRecursive(stockPriceNode.left, stock);
        }
        else if (compareStocks(stock, stockPriceNode.middle.stock) <= 0) {
            return findRecursive(stockPriceNode.middle, stock);
        }
        else {
            return findRecursive(stockPriceNode.right, stock);
        }
    }

    public void insert(Stock stock) {
        StockPriceNode newNode = new StockPriceNode(stock);

        insertRecursive(newNode); // add the new stock by price

        // update the successor and predecessor
        StockPriceNode successor = successor(newNode);
        StockPriceNode predecessor = predecessor(newNode);

        if(predecessor != null){
            predecessor.successor = newNode;
        }
        newNode.successor = successor;
    }

    public void insertRecursive(StockPriceNode newNode) {
        StockPriceNode y = this.root;

        while(!isLeaf(y)){
            if(compareStocks(newNode.stock, y.left.stock) < 0){
                y = y.left;
            }
            else if(compareStocks(newNode.stock, y.middle.stock) < 0){
                y = y.middle;
            }
            else{
                y = y.right;
            }
        }

        StockPriceNode x = y.p;
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
            StockPriceNode newRoot = new StockPriceNode();
            setChildren(newRoot, x, newNode, null);
            root = newRoot;
        }

    }

    private StockPriceNode insertAndSplit(StockPriceNode node, StockPriceNode newChild){
        StockPriceNode left = node.left;
        StockPriceNode middle = node.middle;
        StockPriceNode right = node.right;

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

        StockPriceNode y = new StockPriceNode();

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

    public void delete(StockPriceNode x) {
        StockPriceNode successor = successor(x);
        StockPriceNode predecessor = predecessor(x);

        if(predecessor != null){
            predecessor.successor = successor;
        }

        deleteRecursive(x);
    }

    public void deleteRecursive(StockPriceNode x){
        StockPriceNode y = x.p;

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

    private StockPriceNode borrowOrMerge(StockPriceNode y){
        StockPriceNode z = y.p;
        if (y == z.left){
            StockPriceNode x = z.middle;
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
            StockPriceNode x = z.left;
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

        StockPriceNode x = z.middle;
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
    private void updateKey(StockPriceNode node){
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
    private void setChildren(StockPriceNode parent, StockPriceNode left, StockPriceNode middle, StockPriceNode right){
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
        updateSize(parent);
    }

    private void updateSize(StockPriceNode node) {
        int sumOfChildren = node.left.size;

        if (node.middle != null) {
            sumOfChildren += node.middle.size;
        }
        if (node.right != null) {
            sumOfChildren += node.right.size;
        }

        node.size = sumOfChildren;
    }

    private int compareStocks(Stock stock1, Stock stock2){
        if (stock1 == null || stock2 == null) {
            throw new IllegalArgumentException();
        }

        if (stock1.getCurrentPrice().compareTo(stock2.getCurrentPrice()) == 0) {
            return compareStocks(stock1.getStockId(), stock2.getStockId());
        }

        return stock1.getCurrentPrice().compareTo(stock2.getCurrentPrice());
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

    private void printTreeRecursive(String child, StockPriceNode node, int level){
        if(node == null){
            return;
        }

        for (int i = 0; i <= level; i++) {
            System.out.print("  ");
        }

        System.out.println(child + "Node: " + node.stock.getCurrentPrice());
//        System.out.print("                {" );
//        node.stock.getStockData().printTreeRecursive("{", node.stock.getStockData().root, level+1);

        printTreeRecursive("L: ", node.left, level+1);
        printTreeRecursive("M: ", node.middle, level+1);
        printTreeRecursive("R: ", node.right, level+1);
    }

    public StockPriceNode successor(StockPriceNode node) {
        StockPriceNode parent = node.p;
        StockPriceNode temp = node;
        while ((temp == parent.right || (parent.right == null && temp == parent.middle))) {
            temp = parent;
            if(parent == this.root){
                break;
            }
            parent = parent.p;
        }

        StockPriceNode y;
        if (temp == parent.left) {
            y = parent.middle;
        } else {
            y = parent.right;
        }

        while (!isLeaf(y)) {
            y = y.left;
        }

        if (y.getStockPrice() < Stock.MAX_PRICE) {
            return y;
        }
        return null;
    }

    public StockPriceNode predecessor(StockPriceNode node) {
        StockPriceNode parent = node.p;
        StockPriceNode temp = node;

        while (temp != root && temp == parent.left) {
            temp = parent;
            parent = parent.p;
        }

        StockPriceNode y;
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

        if (y.getStockPrice() > Stock.MIN_PRICE) {
            return y;
        }

        return null;
    }

    public int rank(StockPriceNode node) {
        int rank = 1;
        StockPriceNode y = node.p;
        while (y != null) {
            if (node == y.middle) {
                rank = rank + y.left.size;
            } else if (node == y.right) {
                rank = rank + y.left.size + y.middle.size;
            }
            node = y;
            y = y.p;
        }
        return rank;
    }

    private boolean isLeaf(StockPriceNode node) {
        return node == null || node.left == null;
    }
}
