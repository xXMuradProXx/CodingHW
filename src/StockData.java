public class StockData {
    private long timestamp;
    private Float priceDifference;

    public StockData(long timestamp, Float priceDifference) {
        this.priceDifference = priceDifference;
        this.timestamp = timestamp;
    }


    public Float getPriceDifference() {
        return priceDifference;
    }

    public void setPriceDifference(Float priceDifference) {
        this.priceDifference = priceDifference;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
