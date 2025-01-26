
public class Stock implements Comparable<Stock> {
    private String stockId;
    private Float currentPrice;
    private StockDataTree stockData;

    public Stock(String stockId) {
        this.stockId = stockId;
        this.stockData = new StockDataTree();
    }

    public Stock(String stockId, long timestamp, Float price) {
        this.stockId = stockId;
        this.stockData = new StockDataTree();
        this.addStockData(timestamp, price);
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

    public void addStockData(long timestamp, Float price) {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Adding stock data for " + stockId + " at timestamp " + timestamp + " with price " + price);
        this.stockData.insert(timestamp, price);

        this.currentPrice = price;
    }

    public void addStockData(StockData stockData) {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Adding stock data for " + stockId + " at timestamp " + stockData.getTimestamp() + " with price " + stockData.getPrice());
        this.stockData.insert(stockData.getTimestamp(), stockData.getPrice());

        this.currentPrice = stockData.getPrice();
    }

    public Float getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public int compareTo(Stock other) {
        return this.currentPrice.compareTo(other.currentPrice);
    }
}
