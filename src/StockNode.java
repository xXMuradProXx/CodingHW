public class StockNode {
    // keys
    Stock stock;
    // other nodes
    StockNode left, middle, right;
    StockNode p;

    StockNode() {}

    StockNode(Stock stock) {
        this.stock = stock;
        left = middle = right = null;
    }

    StockNode(String stockId, long timestamp, Float price) {
        this.stock = new Stock(stockId);
        this.stock.addStockData(new StockData(timestamp, price));
        left = middle = right = null;
    }

    //TODO: maybe use copy method
    public void updateKeys(StockNode other){
        this.stock = other.stock;
    }

    public String getStockId() {
        return this.stock.getStockId();
    }
}