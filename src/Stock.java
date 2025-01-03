
public class Stock {
    private String stockId;
    private Array<StockData> stockData;

    public Stock(String stockId) {
        this.stockId = stockId;
        this.stockData = new Array<StockData>();
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Array<StockData> getStockData() {
        return stockData;
    }

    public void setStockData(Array<StockData> stockData) {
        this.stockData = stockData;
    }

    public void addStockData(StockData stockData) {
        this.stockData.add(stockData);
    }
}
