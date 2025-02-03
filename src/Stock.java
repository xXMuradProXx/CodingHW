
public class Stock implements Comparable<Stock> {
    private String stockId;
    private Long timestamp;
    private Float currentPrice;
    private StockDataTree stockData;

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
        this.stockData.insert(stockData.getTimestamp(), stockData.getPriceDifference());
        this.currentPrice += stockData.getPriceDifference();
    }

    public void removeStockData(Float priceDifference) {
        this.currentPrice -= priceDifference;
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
