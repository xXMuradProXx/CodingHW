public class StockNode {
    // keys
    public Stock stock;

    // other nodes
    public StockNode left, middle, right;
    public StockNode p;

    public StockNode() {}

    public StockNode(Stock stock) {
        this.stock = stock;
        left = middle = right = null;
    }

    public void updateKeys(StockNode other){
        this.stock = other.stock;
    }

    public String getStockId() {
        return this.stock.getStockId();
    }
}