public class StockDataNode {
    // keys
    long timestamp;
    Float price;
    // other nodes
    StockDataNode left, middle, right;
    StockDataNode p;

    StockDataNode() {}

    StockDataNode(long timestamp) {
        this(timestamp, null);
    }

    StockDataNode(long timestamp, Float price) {
        this.timestamp = timestamp;
        this.price = price;
        left = middle = right = null;
    }

    public void updateKeys(StockDataNode other){
        this.timestamp = other.timestamp;
        this.price = other.price;
    }

    public boolean isLeaf() {
        return this.left == null/* && StockDataTree.this.root != StockDataNode.this*/;
    }
}