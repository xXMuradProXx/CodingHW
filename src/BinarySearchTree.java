/*

public class BinarySearchTree<T> {
    private static class StockEvent<T extends ExerciseObject> {
        T key;
        StockEvent<T> left, middle, right;
        StockEvent<T> p;

        StockEvent(T key) {
            this.key = key;
            left = middle = right = null;
        }
    }

    private StockEvent root;

    // Add an element to the BST
    public void add(int value) {
        root = addRecursive(root, value);
    }

    public void add(T value extends Comparable<T>) {
        root = addRecursive(root, value);
    }

    public void add(long timestamp, Float price) {
        if(root == null) {
            root = new StockEvent(timestamp, price);
        } else {
            addRecursive(root, timestamp, price);
        }

    }

    private StockEvent addRecursive(StockEvent stockEvent, int value) {
        if (stockEvent == null) {
            return new StockEvent(value);
        }
        if (value < stockEvent.timestamp) {
            stockEvent.left = addRecursive(stockEvent.left, value);
        } else if (value > stockEvent.timestamp) {
            stockEvent.right = addRecursive(stockEvent.right, value);
        }

        stockEvent.key.isSmaller(stockEvent.key);
        return stockEvent;
    }

    // Remove an element from the BST
    public void remove(int value) {
        root = removeRecursive(root, value);
    }

    private StockEvent removeRecursive(StockEvent stockEvent, int value) {
        if (stockEvent == null) {
            return null;
        }
        if (value < stockEvent.timestamp) {
            stockEvent.left = removeRecursive(stockEvent.left, value);
        } else if (value > stockEvent.timestamp) {
            stockEvent.right = removeRecursive(stockEvent.right, value);
        } else {
            // Node to delete found
            if (stockEvent.left == null) {
                return stockEvent.right;
            } else if (stockEvent.right == null) {
                return stockEvent.left;
            }

            // Node with two children: Get the inorder successor
            stockEvent.timestamp = findMin(stockEvent.right).timestamp;
            stockEvent.right = removeRecursive(stockEvent.right, stockEvent.timestamp);
        }
        return stockEvent;
    }

    private StockEvent findMin(StockEvent stockEvent) {
        while (stockEvent.left != null) {
            stockEvent = stockEvent.left;
        }
        return stockEvent;
    }

    // Check if the BST contains a value
    public boolean contains(int value) {
        return containsRecursive(root, value);
    }

    private boolean containsRecursive(StockEvent stockEvent, int value) {
        if (stockEvent == null) {
            return false;
        }
        if (value == stockEvent.timestamp) {
            return true;
        }
        return value < stockEvent.timestamp
                ? containsRecursive(stockEvent.left, value)
                : containsRecursive(stockEvent.right, value);
    }

    // Print the BST in-order
    public void inOrderTraversal() {
        inOrderRecursive(root);
        System.out.println();
    }

    private void inOrderRecursive(StockEvent stockEvent) {
        if (stockEvent != null) {
            inOrderRecursive(stockEvent.left);
            System.out.print(stockEvent.timestamp + " ");
            inOrderRecursive(stockEvent.right);
        }
    }
}
*/
