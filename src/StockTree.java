public class StockTree {
    private static class Stock {
        String stockId;
        StockData stockData;
        Stock left, middle, right;
        Stock p;

        Stock(String stockId, long timestamp, Float price) {
            this.stockId = stockId;
            this.stockData = new StockData(timestamp, price);
            left = middle = right = null;
        }

        public boolean isLeaf() {
            return this.left == null && this.middle == null && this.right == null;
        }

        private boolean isAfter(String stockId) {
            return stockId.compareTo(this.stockId) >= 0;
        }
    }

    private Stock root;

    public StockTree() {
        root = null;
    }

    public Stock find(String stockId) {
        if (root == null) {
            return null;
        }
        else if(root.stockId.equals(stockId)) {
            return root;
        } else {
            return findRecursive(root, stockId);
        }
    }

    private Stock findRecursive(Stock stock, String stockId) {
        if(stock.isLeaf()) {
            if(stock.stockId.equals(stockId)) {
                return stock;
            }
            else {
                return null;
            }
        }
        if(stock.left.isAfter(stockId)) {
            return findRecursive(stock.left, stockId);
        } else if(stock.middle.isAfter(stockId)) {
            return findRecursive(stock.middle, stockId);
        } else {
            return findRecursive(stock.right, stockId);
        }
    }

    public void add(String stockId, long timestamp, Float price) {
        if(root == null) {
            root = new Stock(stockId, timestamp, price);
        } else {
//            addRecursive(root, stockId, timestamp, price);
        }
    }
}
