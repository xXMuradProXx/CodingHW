import java.util.Arrays;

public class hw4 {

    public static int[][] multiplyRecursive(int[][] A, int[][] B) {
        int n = A.length;

        // Base case: if the matrix is 1x1, perform scalar multiplication
        if (n == 1) {
            return new int[][]{{A[0][0] * B[0][0]}};
        }

        // Divide matrices into 2x2 submatrices
        int mid = n / 2;

        int[][] A11 = new int[mid][mid];
        int[][] A12 = new int[mid][mid];
        int[][] A21 = new int[mid][mid];
        int[][] A22 = new int[mid][mid];

        int[][] B11 = new int[mid][mid];
        int[][] B12 = new int[mid][mid];
        int[][] B21 = new int[mid][mid];
        int[][] B22 = new int[mid][mid];

        // Fill submatrices
        for (int i = 0; i < mid; i++) {
            for (int j = 0; j < mid; j++) {
                A11[i][j] = A[i][j];
                A12[i][j] = A[i][j + mid];
                A21[i][j] = A[i + mid][j];
                A22[i][j] = A[i + mid][j + mid];

                B11[i][j] = B[i][j];
                B12[i][j] = B[i][j + mid];
                B21[i][j] = B[i + mid][j];
                B22[i][j] = B[i + mid][j + mid];
            }
        }

        // Recursively calculate submatrix products
        int[][] C11 = addMatrices(multiplyRecursive(A11, B11), multiplyRecursive(A12, B21));
        int[][] C12 = addMatrices(multiplyRecursive(A11, B12), multiplyRecursive(A12, B22));
        int[][] C21 = addMatrices(multiplyRecursive(A21, B11), multiplyRecursive(A22, B21));
        int[][] C22 = addMatrices(multiplyRecursive(A21, B12), multiplyRecursive(A22, B22));

        // Combine the results into a single matrix
        int[][] C = new int[n][n];
        for (int i = 0; i < mid; i++) {
            for (int j = 0; j < mid; j++) {
                C[i][j] = C11[i][j];
                C[i][j + mid] = C12[i][j];
                C[i + mid][j] = C21[i][j];
                C[i + mid][j + mid] = C22[i][j];
            }
        }

        return C;
    }

    // Helper method to add two matrices
    public static int[][] addMatrices(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] + B[i][j];
            }
        }
        return result;
    }

    // Main method to test the implementation
    public static void main(String[] args) {
        int n = 4; // Matrix size (must be a power of 2)

        int[][] A = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        int[][] B = {
                {17, 18, 19, 20},
                {21, 22, 23, 24},
                {25, 26, 27, 28},
                {29, 30, 31, 32}
        };

        System.out.println("Matrix A:");
        printMatrix(A);

        System.out.println("\nMatrix B:");
        printMatrix(B);

        int[][] C = multiplyRecursive(A, B);

        System.out.println("\nMatrix A * B (Recursive):");
        printMatrix(C);

        String s = null;
        System.out.println(s instanceof String);
    }

    // Helper method to print a matrix
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}
