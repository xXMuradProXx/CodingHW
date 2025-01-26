public class StockManager {
    // add code here
    private StockTree stocks;

    public StockManager() {
    // add code here

    }

    // 1. Initialize the system
    public void initStocks() {
    // add code here
        stocks = new StockTree();
    }

    // 2. Add a new stock
    public void addStock(String stockId, long timestamp, Float price) {
    // add code here

        if (price <= 0) {
            throw new IllegalArgumentException("The initial price must be greater than 0");
        }

        StockNode stockNode = stocks.find(stockId);
        if (stockNode != null) {
            throw new IllegalArgumentException("Stock already exists");
        }

        Stock stock = new Stock(stockId, timestamp, price); // add the initial stock data

        stocks.insert(stock);
    }

    // 3. Remove a stock
    public void removeStock(String stockId) {
        StockNode stockNode = stocks.find(stockId);
        if(stockNode == null){
            throw new IllegalArgumentException("Stock not found");
        }

        stocks.delete(stockNode);
    }

    // 4. Update a stock price
    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        if (priceDifference == 0) {
            throw new IllegalArgumentException("Price difference must be non-zero");
        }

        StockNode stockNode = stocks.find(stockId);
        if (stockNode == null) {
            throw new IllegalArgumentException("Stock not found");
        }

        Stock stock = stockNode.stock;
        stock.addStockData(new StockData(timestamp, priceDifference));
    }

    // 5. Get the current price of a stock
    public Float getStockPrice(String stockId) {
        StockNode stockNode = stocks.find(stockId);
        if (stockNode == null) {
            throw new IllegalArgumentException("Stock not found");
        }

        Stock stock = stockNode.stock;
        return stock.getCurrentPrice();
    }

    // 6. Remove a specific timestamp from a stock's history
    // TODO: finish this method. Removing timestamp from stock's history is not implemented yet, the price should change accordingly
    public void removeStockTimestamp(String stockId, long timestamp) {
        StockNode stockNode = stocks.find(stockId);
        if (stockNode == null) {
            throw new IllegalArgumentException("Stock not found");
        }

        Stock stock = stockNode.stock;
        StockDataTree stockData = stock.getStockData();
        StockDataNode stockDataNode = stockData.find(timestamp);
        if(stockDataNode == null){
            throw new IllegalArgumentException("Timestamp not found");
        }

        stock.removeStockData(stockDataNode.priceDifference);
        stockData.delete(stockDataNode);
    }

    // 7. Get the amount of stocks in a given price range
    public int getAmountStocksInPriceRange(Float price1, Float price2) {
    // add code here
        int count = 0;

        /*for (int i = 0; i < stocks.size(); i++) {
            Stock stock = stocks.get(i);
            StockDataTree stockData = stock.getStockData();
            Float currentPrice = stock.getCurrentPrice();

            if (currentPrice >= price1 && currentPrice <= price2) {
                count++;
            }
        }*/

        return count;
    }

    // 8. Get a list of stock IDs within a given price range
    public String[] getStocksInPriceRange(Float price1, Float price2) {
    // add code here
        /*Array<Stock> stocksInRange = new Array<Stock>();

        // Find stocks within the price range
        for (int i = 0; i < stocks.size(); i++) {
            Stock stock = stocks.get(i);
            Float currentPrice = stock.getCurrentPrice();

            if (currentPrice >= price1 && currentPrice <= price2) {
                stocksInRange.add(stock);
            }
        }

        String[] stockIds = new String[stocksInRange.size()];*/
        // Sort the stocks by price
        // TODO: Implement a sorting algorithm
        //  suggestion: create another tree that stores the stocks by price, therefore will be able to get the stocks in sorted order
        // if we are using a 2-3 tree, we can use in-order traversal to get the stocks in sorted order
        // just to call find() method for each stockId in


//        return stockIds;
        return null;
    }
   
}


