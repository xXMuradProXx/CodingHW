public class StockData {
    private long timestamp;
    private Float price;

    public StockData(long timestamp, Float price) {
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
