
public class Stock extends ExerciseObject {
    private String stockId;
    private StockDataTree stockData;

    public Stock(String stockId) {
        this.stockId = stockId;
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
        this.stockData.insert(stockData.getTimestamp(), stockData.getPrice());
    }

    public Float getCurrentPrice() {
        return stockData.getFinalPrice();
    }

    @Override
    public boolean isSmaller(ExerciseObject other) {
        return stockId.compareTo(((Stock) other).getStockId()) < 0;
    }

    @Override
    public boolean isLarger(ExerciseObject other) {
        return stockId.compareTo(((Stock) other).getStockId()) > 0;
    }

    @Override
    public boolean isEqual(ExerciseObject other) {
        return stockId.compareTo(((Stock) other).getStockId()) == 0;
    }
}
