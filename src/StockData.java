public class StockData extends ExerciseObject {
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

    @Override
    public boolean isSmaller(ExerciseObject other) {
        return timestamp < ((StockData) other).getTimestamp();
    }

    @Override
    public boolean isLarger(ExerciseObject other) {
        return timestamp > ((StockData) other).getTimestamp();
    }

    @Override
    public boolean isEqual(ExerciseObject other) {
        return timestamp == ((StockData) other).getTimestamp();
    }
}
