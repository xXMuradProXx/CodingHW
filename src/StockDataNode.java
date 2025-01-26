public class StockDataNode {
    // keys
    long timestamp;
    Float priceDifference;
    // other nodes
    StockDataNode left, middle, right;
    StockDataNode p;

    StockDataNode() {}

    StockDataNode(long timestamp) {
        this(timestamp, null);
    }

    StockDataNode(long timestamp, Float priceDifference) {
        this.timestamp = timestamp;
        this.priceDifference = priceDifference;
        left = middle = right = null;
    }

    public void updateKeys(StockDataNode other){
        this.timestamp = other.timestamp;
        this.priceDifference = other.priceDifference;
    }
}