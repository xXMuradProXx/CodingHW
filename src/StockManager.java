import java.util.ArrayList;

public class StockManager {
    // add code here
    private Array<Stock> stocks; //or TODO 2-3 tree. standard 2-3 tree for storing Stocks and 2-3 tree with max value attribute for each node for storing StockData;
    //TODO maybe to use arrays and heaps instead of trees for better performance

    public StockManager() {
    // add code here

    }

    // 1. Initialize the system
    public void initStocks() {
    // add code here
        stocks = new Array<Stock>();
    }

    // 2. Add a new stock
    public void addStock(String stockId, long timestamp, Float price) {
    // add code here

        if (price <= 0) {
            throw new IllegalArgumentException("The initial price must be greater than 0");
        }

        for (int i = 0; i < stocks.size(); i++) {
            if (stocks.get(i).getStockId().equals(stockId)) {
                throw new IllegalArgumentException("Stock already exists");
            }
        }

        Stock stock = new Stock(stockId);
        stock.addStockData(new StockData(timestamp, price));

        stocks.add(stock);
    }

    // 3. Remove a stock
    public void removeStock(String stockId) {
    // add code here
        for (int i = 0; i < stocks.size(); i++) {
            if (stocks.get(i).getStockId().equals(stockId)) {
                stocks.remove(i);
                return;
            }
        }

        throw new IllegalArgumentException("Stock not found");
    }

    // 4. Update a stock price
    public void updateStock(String stockId, long timestamp, Float priceDifference) {
    // add code here

        if (priceDifference == 0) {
            throw new IllegalArgumentException("Price difference must be non-zero");
        }

        for (int i = 0; i < stocks.size(); i++) {
            if (stocks.get(i).getStockId().equals(stockId)) {
                Stock stock = stocks.get(i);
                StockDataTree stockData = stock.getStockData();

                Float currentPrice = stockData.getFinalPrice();
                Float newPrice = currentPrice + priceDifference;
                stock.addStockData(new StockData(timestamp, newPrice));
//                System.out.println("Stock price updated successfully " + stock.getStockData().getLast().getPrice());
                return;
            }
        }
    }

    // 5. Get the current price of a stock
    public Float getStockPrice(String stockId) {
    // add code here
        for (int i = 0; i < stocks.size(); i++) {
            if (stocks.get(i).getStockId().equals(stockId)) {
                Stock stock = stocks.get(i);
                StockDataTree stockData = stock.getStockData();
//                System.out.println("The stock price is " + stockData.getLast().getPrice());

                return stockData.getFinalPrice();
            }
        }

        throw new IllegalArgumentException("Stock not found");
    }

    // 6. Remove a specific timestamp from a stock's history
    public void removeStockTimestamp(String stockId, long timestamp) {
    // add code here
        for (int i = 0; i < stocks.size(); i++) {
            if (stocks.get(i).getStockId().equals(stockId)) {
                Stock stock = stocks.get(i);
                StockDataTree stockData = stock.getStockData();

                /*if (stockData.size() == 1) {
                    throw new IllegalArgumentException("Cannot remove the only timestamp");
                }*/

                stockData.delete(timestamp);

//                throw new IllegalArgumentException("Timestamp not found");
            }
        }

        throw new IllegalArgumentException("Stock not found");
    }

    // 7. Get the amount of stocks in a given price range
    public int getAmountStocksInPriceRange(Float price1, Float price2) {
    // add code here
        int count = 0;

        for (int i = 0; i < stocks.size(); i++) {
            Stock stock = stocks.get(i);
            StockDataTree stockData = stock.getStockData();
            Float currentPrice = stockData.getFinalPrice();

            if (currentPrice >= price1 && currentPrice <= price2) {
                count++;
            }
        }

        return count;
    }

    // 8. Get a list of stock IDs within a given price range
    public String[] getStocksInPriceRange(Float price1, Float price2) {
    // add code here
        Array<Stock> stocksInRange = new Array<Stock>();

        // Find stocks within the price range
        for (int i = 0; i < stocks.size(); i++) {
            Stock stock = stocks.get(i);
            /*Array<StockData> stockData = stock.getStockData();
            Float currentPrice = stockData.get(stockData.size() - 1).getPrice();

            if (currentPrice >= price1 && currentPrice <= price2) {
                stocksInRange.add(stock);
            }*/
        }

        String[] stockIds = new String[stocksInRange.size()];
        // Sort the stocks by price
        // TODO: Implement a sorting algorithm


        return stockIds;
    }
   
}


