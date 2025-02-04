public class StockPriceNode {
    // keys
    public Stock stock;

    // other nodes
    public StockPriceNode left, middle, right;
    public StockPriceNode p;
    public StockPriceNode successor, predecessor;

    int size;

    public StockPriceNode() {}

    public StockPriceNode(Stock stock) {
        this.stock = stock;
        left = middle = right = successor = predecessor = null;
        this.size = 1;
    }

    public void updateKeys(StockPriceNode other){
        this.stock = other.stock;
    }

    public Float getStockPrice() {
        return this.stock.getCurrentPrice();
    }

}
