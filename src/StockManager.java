public class StockManager {

    private StockTree stocks;
    private StockPriceTree stockPrices;

    public StockManager() {}

    // 1. Initialize the system
    public void initStocks() {
        stocks = new StockTree();
        stockPrices = new StockPriceTree();
    }

    // 2. Add a new stock
    public void addStock(String stockId, long timestamp, Float price) {
        if (price <= 0) {
            throw new IllegalArgumentException("The initial price must be greater than 0");
        }

        StockNode stockNode = stocks.find(stockId);
        if (stockNode != null) {
            throw new IllegalArgumentException("Stock already exists");
        }

        Stock stock = new Stock(stockId, timestamp, price); // create stock and add the initial stock data

        stocks.insert(stock); // insert stock into stocks tree
        stockPrices.insert(stock); // insert stock into stockPrices tree
    }

    // 3. Remove a stock
    public void removeStock(String stockId) {
        StockNode stockNode = stocks.find(stockId);

        if(stockNode == null){
            throw new IllegalArgumentException("Stock not found");
        }

        Stock stock = stockNode.stock; // retrieve stock from stockNode
        stocks.delete(stockNode); // delete stock from stocks tree

        StockPriceNode stockPriceNode = stockPrices.find(stock); // find stock in stockPrices tree
        stockPrices.delete(stockPriceNode); // delete stock from stockPrices tree
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

        StockPriceNode stockPriceNode = stockPrices.find(stock); // find stock in stockPrices tree
        stockPrices.delete(stockPriceNode); // delete stock from stockPrices tree

        stock.addStockData(timestamp, priceDifference); // update stock
        stockPrices.insert(stock); // insert stock with new price back into stockPrices tree
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

        stockPrices.delete(stockPrices.find(stock)); // delete stock from stockPrices tree

        stockData.delete(stockDataNode); // delete relevant stock data from stock
        stock.removeStockData(stockDataNode.priceDifference); // update stock price

        stockPrices.insert(stock); // insert updated stock with new price back into stockPrices tree
    }

    // 7. Get the amount of stocks in a given price range
    public int getAmountStocksInPriceRange(Float price1, Float price2) {
        if (price1 > price2) {
            throw new IllegalArgumentException("Illegal price range");
        }

        // Create two dummy stocks with the given prices
        Stock min = new Stock(Stock.MIN_ID, price1);
        Stock max = new Stock(Stock.MAX_ID, price2);

        // Insert the dummy stocks into the tree
        stockPrices.insert(min);
        stockPrices.insert(max);

        // Find the nodes with dummy stocks in the tree
        StockPriceNode minNode = stockPrices.find(min);
        StockPriceNode maxNode = stockPrices.find(max);

        // Get the rank of the dummy stocks
        int rank1 = stockPrices.rank(minNode);
        int rank2 = stockPrices.rank(maxNode);

        // Delete the dummy stocks from the tree
        stockPrices.delete(minNode);
        stockPrices.delete(maxNode);

        // Return the difference in ranks
        return rank2-rank1-1;
    }

    // 8. Get a list of stock IDs within a given price range
    public String[] getStocksInPriceRange(Float price1, Float price2) {
        if (price1 > price2) {
            throw new IllegalArgumentException("Illegal price range");
        }

        int numStocksInRange = getAmountStocksInPriceRange(price1, price2);
        String[] stocksInRange = new String[numStocksInRange];

        // Create dummy stock with the given prices
        Stock min = new Stock(Stock.MIN_ID, price1);

        // Insert the dummy stock into the tree
        stockPrices.insert(min);

        // Find the leaf node with dummy stock in the tree
        StockPriceNode minNode = stockPrices.find(min);

        StockPriceNode temp = minNode.successor; // Move to the next leaf node which actually holds the first stock in the range

        // Get the stock IDs in the given range
        for (int i = 0; i < numStocksInRange; i++) {
            stocksInRange[i] = temp.stock.getStockId();
            temp = temp.successor;
        }

        // Delete the dummy stock from the tree
        stockPrices.delete(minNode);

        return stocksInRange;
    }
   
}


