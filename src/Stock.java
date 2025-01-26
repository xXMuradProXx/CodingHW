
public class Stock implements Comparable<Stock> {
    private String stockId;
    private Long timestamp;
    private Float currentPrice;
    private StockDataTree stockData;

    public Stock(String stockId) {
        this.stockId = stockId;
        this.stockData = new StockDataTree();
    }

    public Stock(String stockId, long timestamp, Float startPrice) {
        this.stockId = stockId;
        this.timestamp = timestamp;
        this.currentPrice = startPrice;
        this.stockData = new StockDataTree();
//        this.addStockData(timestamp, price);
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public StockDataTree getStockData() {
        return stockData;
    }

    public void setStockData(StockDataTree stockData) {
        this.stockData = stockData;
    }

    public void addStockData(StockData stockData) {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("New price for stock " + stockId + " is " + this.currentPrice + " with price difference " + stockData.getPriceDifference());
        this.stockData.insert(stockData.getTimestamp(), stockData.getPriceDifference());

        this.currentPrice += stockData.getPriceDifference();
        this.stockData.printTree();
        System.out.println("New price for stock " + stockId + " is " + this.currentPrice);
        System.out.println("-----------------------------------------------------------------------------");
    }

    public void removeStockData(Float priceDifference) {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("New price for stock " + stockId + " is " + this.currentPrice + " with price difference " + -priceDifference);

        this.currentPrice -= priceDifference;
        this.stockData.printTree();
        System.out.println("New price for stock " + stockId + " is " + this.currentPrice);
        System.out.println("-----------------------------------------------------------------------------");
    }

    public Float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public int compareTo(Stock other) {
        return this.currentPrice.compareTo(other.currentPrice);
    }
}
