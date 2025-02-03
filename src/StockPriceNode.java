public class StockPriceNode {
    // keys
    Stock stock;

    // other nodes
    StockPriceNode left, middle, right;
    StockPriceNode p;
    StockPriceNode successor, predecessor;

    int size;

    StockPriceNode() {}

    StockPriceNode(Stock stock) {
        this.stock = stock;
        left = middle = right = successor = predecessor = null;
        this.size = 1;
    }

    StockPriceNode(String stockId, long timestamp, Float price) {
        this.stock = new Stock(stockId);
        this.stock.addStockData(new StockData(timestamp, price));
        left = middle = right = null;
    }

    public void updateKeys(StockPriceNode other){
        this.stock = other.stock;
    }

    public Float getStockPrice() {
        return this.stock.getCurrentPrice();
    }

}
