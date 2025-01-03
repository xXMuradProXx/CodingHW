public class StockData {
    private Float price;
    private long timestamp;

    public StockData(Float price, long timestamp) {
        this.price = price;
        this.timestamp = timestamp;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
