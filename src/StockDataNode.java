public class StockDataNode {
    // keys
    public long timestamp;
    public Float priceDifference;
    // other nodes
    public StockDataNode left, middle, right;
    public StockDataNode p;

    public StockDataNode() {}

    public StockDataNode(long timestamp, Float priceDifference) {
        this.timestamp = timestamp;
        this.priceDifference = priceDifference;
        left = middle = right = null;
    }

    public void updateKeys(StockDataNode other){
        this.timestamp = other.timestamp;
        this.priceDifference = other.priceDifference;
    }
}