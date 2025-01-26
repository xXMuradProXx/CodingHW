import java.util.ArrayList;
import java.util.List;

/* 

public class Main {
    public static void main(String[] args) {
    // Given Main

        ArrayList<String> stockIds = new ArrayList<>();
        stockIds.add("ABCDEF");
        stockIds.add("GHIJKL");
        stockIds.add("MNOPQR");
        stockIds.add("STUVWX");
        stockIds.add("YZABCD");
        stockIds.add("EFGHIJ");
        stockIds.add("KLMNOP");
        stockIds.add("QRSTUV");
        stockIds.add("WXYZAB");
        stockIds.add("CDEFGH");

        ArrayList<Float> prices = new ArrayList<>();
        prices.add(123.45f);
        prices.add(67.89f);
        prices.add(45.67f);
        prices.add(234.56f);
        prices.add(89.01f);
        prices.add(150.34f);
        prices.add(98.76f);
        prices.add(210.45f);
        prices.add(115.99f);
        prices.add(300.12f);

        ArrayList<Long> initialTimestamps = new ArrayList<>();
        initialTimestamps.add(1708647300000L);
        initialTimestamps.add(1708650900000L);
        initialTimestamps.add(1708654500000L);
        initialTimestamps.add(1708658100000L);
        initialTimestamps.add(1708661700000L);
        initialTimestamps.add(1708665300000L);
        initialTimestamps.add(1708668900000L);
        initialTimestamps.add(1708672500000L);
        initialTimestamps.add(1708676100000L);
        initialTimestamps.add(1708679700000L);

        StockManager stockmanger = new StockManager();
        stockmanger.initStocks();
        boolean expression = false;
        for (int i = 0; i < stockIds.size(); i++) {
            stockmanger.addStock(stockIds.get(i), initialTimestamps.get(i), prices.get(i));
            expression = stockmanger.getStockPrice(stockIds.get(i)).equals(prices.get(i));
            Assert(expression);
        }

        for (int i = 0; i < 3; i++) {
            String stockIdToRemove = stockIds.remove(0);
            prices.remove(0);
            initialTimestamps.remove(0);
            stockmanger.removeStock(stockIdToRemove);
            try {
                stockmanger.getStockPrice(stockIdToRemove);
                System.out.println("Test failed: Exception was not thrown");
            } catch (IllegalArgumentException e) {
                System.out.println("Test passed: Exception was thrown as expected");
            } catch (Exception e) {
                System.out.println("Test failed: An unexpected exception was thrown");
            }
            Assert(expression);
            System.out.println("Removed Stock: " + stockIdToRemove);

        }
        Map<String, ArrayList<Map.Entry<Long, Float>>> additionalData = new HashMap<>();

        // Adding additional timestamps and price changes based on original timestamps
        ArrayList<Map.Entry<Long, Float>> data1 = new ArrayList<>();
        data1.add(Map.entry(initialTimestamps.get(0) + 60000L, 5.67f)); // Add 1 minute
        data1.add(Map.entry(initialTimestamps.get(0) + 120000L, -3.21f)); // Add 2 minutes
        additionalData.put("STUVWX", data1);

        ArrayList<Map.Entry<Long, Float>> data2 = new ArrayList<>();
        data2.add(Map.entry(initialTimestamps.get(1) + 300000L, 7.89f)); // Add 5 minutes
        additionalData.put("YZABCD", data2);

        ArrayList<Map.Entry<Long, Float>> data3 = new ArrayList<>();
        data3.add(Map.entry(initialTimestamps.get(2) + 60000L, -2.34f)); // Add 1 minute
        data3.add(Map.entry(initialTimestamps.get(2) + 180000L, 4.56f)); // Add 3 minutes
        data3.add(Map.entry(initialTimestamps.get(2) + 300000L, -1.78f)); // Add 5 minutes
        additionalData.put("EFGHIJ", data3);

        for (int i = 0; i < 3; i++) {
            String StockId = stockIds.get(i);
            ArrayList<Map.Entry<Long, Float>> data = additionalData.get(StockId);
            for (Map.Entry<Long, Float> entry : data) {
                stockmanger.updateStock(StockId, entry.getKey(), entry.getValue());
            }

        }
        expression = Math.abs(stockmanger.getStockPrice("STUVWX") - 237.02f) <= 0.1;
        Assert(expression);
        expression = Math.abs(stockmanger.getStockPrice("YZABCD") - 96.9f) <= 0.1;
        Assert(expression);
        expression = Math.abs(stockmanger.getStockPrice("EFGHIJ") - 150.78f) <= 0.1;
        Assert(expression);

        for (String stockId : stockIds) {
            double price = stockmanger.getStockPrice(stockId);
            System.out.println("Stock ID: " + stockId + ", Current Price: " + price);
        }
        if (additionalData.containsKey("STUVWX")) {
            ArrayList<Map.Entry<Long, Float>> stockData = additionalData.get("STUVWX");
            stockmanger.removeStockTimestamp("STUVWX", stockData.get(1).getKey());
            stockData.remove(1);
        }
        if (additionalData.containsKey("EFGHIJ")) {
            ArrayList<Map.Entry<Long, Float>> stockData = additionalData.get("EFGHIJ");
            stockmanger.removeStockTimestamp("EFGHIJ", stockData.get(2).getKey());
            stockmanger.removeStockTimestamp("EFGHIJ", stockData.get(0).getKey());
        }
        for (String stockId : stockIds) {
            double price = stockmanger.getStockPrice(stockId);
            System.out.println("Stock ID: " + stockId + ", Current Price: " + price);
        }
        expression = Math.abs(stockmanger.getStockPrice("STUVWX") - 240.23f) <= 0.1;
        Assert(expression);
        expression = Math.abs(stockmanger.getStockPrice("EFGHIJ") - 154.9f) <= 0.1;
        Assert(expression);
        Float price1 = 10f;
        Float price2 = 30f;
        int stockamount = stockmanger.getAmountStocksInPriceRange(price1, price2);
        expression = stockamount == 0;
        Assert(expression);
        System.out.println(Arrays.toString(stockmanger.getStocksInPriceRange(price1, price2)));
        price1 = 50f;
        price2 = 170f;
        stockamount = stockmanger.getAmountStocksInPriceRange(price1, price2);
        expression = stockamount == 4;
        Assert(expression);
        System.out.println(stockamount);
        String[] stocksInRange = stockmanger.getStocksInPriceRange(price1, price2);

        expression = stocksInRange[0].equals("YZABCD");
        Assert(expression);
        expression = stocksInRange[1].equals("KLMNOP");
        Assert(expression);
        expression = stocksInRange[2].equals("WXYZAB");
        Assert(expression);
        expression = stocksInRange[3].equals("EFGHIJ");
        Assert(expression);
        for (String stock : stocksInRange) {
            System.out.println(stock);
        }

        System.out.println("Passed All tests!!!");
    }

    public static void Assert(boolean expression) {
        if (!expression) {
            throw new AssertionError();
        }

    }
}
 */

/*
public class Main {

  public static void main(String[] args) {
      // Given Main

      // Initialize stock manager and stock data
      StockManager stockManager = new StockManager();
      stockManager.initStocks();

      // Add some stock data
      stockManager.addStock("XYZ123", 1708647300000L, 150.75f);
      stockManager.addStock("ABC789", 1708650900000L, 200.00f);
      stockManager.addStock("DEF456", 1708654500000L, 120.50f);

      // Test getStockPrice after adding stocks
      try {
          assert stockManager.getStockPrice("XYZ123").equals(150.75f) : "Stock XYZ123 price mismatch!";
          assert stockManager.getStockPrice("ABC789").equals(200.00f) : "Stock ABC789 price mismatch!";
          System.out.println("AddStock Test Passed");
      } catch (AssertionError e) {
          System.out.println("AddStock Test Failed: " + e.getMessage());
      }

      // Test removeStock and exception handling
      stockManager.removeStock("XYZ123");
      try {
          stockManager.getStockPrice("XYZ123");
          System.out.println("Test failed: Exception was not thrown");
      } catch (IllegalArgumentException e) {
          System.out.println("Test passed: Exception was thrown as expected");
      }

      // Test stock price update
      stockManager.updateStock("DEF456", 1708658100000L, 10.50f);
      try {
          assert stockManager.getStockPrice("DEF456").equals(131.00f) : "Stock DEF456 price mismatch after update!";
          System.out.println("UpdateStock Test Passed");
      } catch (AssertionError e) {
          System.out.println("UpdateStock Test Failed: " + e.getMessage());
      }

      // Test getStocksInPriceRange
      try {
          String[] stocksInRange = stockManager.getStocksInPriceRange(100f, 180f);
          assert stocksInRange.length == 1 : "Expected 1 stock in range";
          assert stocksInRange[0].equals("DEF456") : "Expected DEF456 in range";
          System.out.println("GetStocksInPriceRange Test Passed");
      } catch (AssertionError e) {
          System.out.println("GetStocksInPriceRange Test Failed: " + e.getMessage());
      }

      // Test getAmountStocksInPriceRange
      try {
          int count = stockManager.getAmountStocksInPriceRange(150f, 250f);
          assert count == 1 : "Expected 1 stock in range";
          System.out.println("GetAmountStocksInPriceRange Test Passed");
      } catch (AssertionError e) {
          System.out.println("GetAmountStocksInPriceRange Test Failed: " + e.getMessage());
      }

      stockManager.updateStock("ABC789", 1708650900001L, 10f);
      stockManager.updateStock("ABC789", 1708650900002L, 100f);
      stockManager.updateStock("ABC789", 1708650900003L, 1000f);
      stockManager.updateStock("ABC789", 1708650900004L, -10000f);

      System.out.println(stockManager.getStockPrice("ABC789"));
      stockManager.removeStockTimestamp("ABC789", 1708650900001L);
      System.out.println(stockManager.getStockPrice("ABC789"));;
      stockManager.removeStockTimestamp("ABC789", 1708650900004L);
      System.out.println(stockManager.getStockPrice("ABC789"));;

      // Final check of all stocks
      for (String stockId : Arrays.asList("ABC789", "DEF456")) {
          try {
              float price = stockManager.getStockPrice(stockId);
              System.out.println("Stock ID: " + stockId + ", Current Price: " + price);
          } catch (IllegalArgumentException e) {
              System.out.println("Error fetching price for stock: " + stockId);
          }
      }
  }
}
*/
public class Main2 {

   public static void main(String[] args) {
       // Existing tests
       ArrayList<String> stockIds = new ArrayList<>(List.of("ABCDEF", "GHIJKL", "MNOPQR", "STUVWX", "YZABCD", "EFGHIJ", "KLMNOP", "QRSTUV", "WXYZAB", "CDEFGH"));
       ArrayList<Float> prices = new ArrayList<>(List.of(123.45f, 67.89f, 45.67f, 234.56f, 89.01f, 150.34f, 98.76f, 210.45f, 115.99f, 300.12f));
       ArrayList<Long> initialTimestamps = new ArrayList<>(List.of(
               1708647300000L, 1708650900000L, 1708654500000L, 1708658100000L, 1708661700000L,
               1708665300000L, 1708668900000L, 1708672500000L, 1708676100000L, 1708679700000L
       ));

       StockManager stockManager = new StockManager();
       stockManager.initStocks();

       // Add stocks and verify
       for (int i = 0; i < stockIds.size(); i++) {
           stockManager.addStock(stockIds.get(i), initialTimestamps.get(i), prices.get(i));
           Assert(stockManager.getStockPrice(stockIds.get(i)).equals(prices.get(i)));
       }

       // Test removing stocks
       for (int i = 0; i < 3; i++) {
           String stockIdToRemove = stockIds.remove(0);
           prices.remove(0);
           initialTimestamps.remove(0);
           stockManager.removeStock(stockIdToRemove);
           try {
               stockManager.getStockPrice(stockIdToRemove);
               System.out.println("Test failed: Exception was not thrown");
           } catch (IllegalArgumentException e) {
               System.out.println("Test passed: Exception was thrown as expected");
           }
       }

       // Additional tests
       // 1. Test boundary prices in a range
       Float price1 = 150.34f;
       Float price2 = 150.34f;
       int stockAmount = stockManager.getAmountStocksInPriceRange(price1, price2);
       Assert(stockAmount == 1);
       System.out.println("Boundary price amount test passed.");

       price1 = 150.34f;
       price2 = 120.34f;
       String[] res = stockManager.getStocksInPriceRange(price1, price2);
       Assert(res.length == 0);
       System.out.println("Boundary price array test passed.");

       // 2. Test duplicate stock IDs
       try {
           stockManager.addStock("ABCDEF", 1708680000000L, 123.45f);
           stockManager.addStock("ABCDEF", 1708680000001L, 123.67f);

           System.out.println("Test failed: Duplicate stock ID was allowed.");
       } catch (IllegalArgumentException e) {
           System.out.println("Test passed: Duplicate stock ID was rejected.");
       }

       try {
           stockManager.addStock("aa", 1708680000000L, 0f);

           System.out.println("Test failed: 0 price was allowed.");
       } catch (IllegalArgumentException e) {
           System.out.println("Test passed: 0 price was rejected.");
       }

       // 3. Test extreme timestamps
       stockManager.addStock("EXTREME", Long.MIN_VALUE, 100.0f);
       stockManager.addStock("FUTURE", Long.MAX_VALUE, 200.0f);
       System.out.println("Extreme timestamps test passed.");

       // 4. Test large data sets
       for (int i = 0; i < 10000; i++) {
           stockManager.addStock("STOCK" + i, System.currentTimeMillis(), 50.0f + i);
       }
       System.out.println("Large dataset test passed.");

       // 5. Verify integrity after removing multiple timestamps
       stockManager.addStock("test", 1798654500000L, 2.0F);
       Assert(stockManager.getStockPrice("test") == 2.0f);
       stockManager.updateStock("test", 1798654500001L, 3.0F);
       Assert(stockManager.getStockPrice("test") == 5.0f);
       stockManager.updateStock("test", 1798654500002L, -9.0F);
       Assert(stockManager.getStockPrice("test") == -4.0f);
       stockManager.removeStockTimestamp("test", 1798654500002L);
       Assert(stockManager.getStockPrice("test") == 5.0f);
       System.out.println("Update price test passed.");

       try {
           stockManager.removeStockTimestamp("test", 1798654500000L);
           System.out.println("Test failed: remove main timestamp was allowed.");
       } catch (IllegalArgumentException e) {
           System.out.println("Test passed: main timestamp was rejected.");
       }

       try {
           stockManager.updateStock("test", 1798654500005L, 0f);
           System.out.println("Test failed: 0 price diff was allowed.");
       } catch (IllegalArgumentException e) {
           System.out.println("Test passed: 0 price diff was rejected.");
       }

       // 6. Test adding stocks with negative prices
       try {
           stockManager.addStock("NEGATIVE", 1708680000000L, -50.0f);
           System.out.println("Test failed: Negative price was allowed.");
       } catch (IllegalArgumentException e) {
           System.out.println("Test passed: Negative price was rejected.");
       }

       System.out.println("All tests passed!");
   }

   public static void Assert(boolean expression) {
       if (!expression) {
           throw new AssertionError();
       }
   }

   public static void compareFloats(Float num1, Float num2) {
       final float epsilon = 1e-2f;
       if (Math.abs(num1 - num2) > epsilon) {
           throw new AssertionError();
       }
   }
}
