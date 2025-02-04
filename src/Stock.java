public class Stock {

    private final String stockId;
    private long timestamp;
    private Float currentPrice;
    private final StockDataTree stockData;

    public static final String MIN_ID = "";
    public static final String MAX_ID = null;

    public static final long MIN_TIMESTAMP = Long.MIN_VALUE;
    public static final long MAX_TIMESTAMP = Long.MAX_VALUE;

    public static final Float MIN_PRICE = Float.NEGATIVE_INFINITY;
    public static final Float MAX_PRICE = Float.POSITIVE_INFINITY;

    public Stock(String stockId) {
        this.stockId = stockId;
        this.stockData = new StockDataTree();
    }

    public Stock(String stockId, Float currentPrice) {
        this.stockId = stockId;
        this.currentPrice = currentPrice;
        this.stockData = new StockDataTree();
    }

    public Stock(String stockId, long timestamp, Float startPrice) {
        this.stockId = stockId;
        this.timestamp = timestamp;
        this.currentPrice = startPrice;
        this.stockData = new StockDataTree();
    }

    public String getStockId() {
        return stockId;
    }

    public StockDataTree getStockData() {
        return stockData;
    }

    public void addStockData(long timestamp, Float priceDifference) {
        this.stockData.insert(timestamp, priceDifference);
        this.currentPrice += priceDifference;
    }

    public void removeStockData(Float priceDifference) {
        this.currentPrice -= priceDifference;
    }

    public Float getCurrentPrice() {
        return currentPrice;
    }
}
