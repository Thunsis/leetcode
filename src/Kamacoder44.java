import java.util.Scanner;

// kamacoder 44
public class Kamacoder44 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] nums = new int[n][m];
        int totalPrice = 0;
        int nPriceUpper = 0;
        int nPriceLower = 0;
        int mPriceUpper = 0;
        int mPriceLower = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                nums[i][j] = scanner.nextInt();
                totalPrice += nums[i][j];
            }
        }

        for (int i = 0; i < n && nPriceUpper <= totalPrice/2; i++) {
            for (int j = 0; j < m; j++) {
                nPriceUpper += nums[i][j];
                if (nPriceUpper <= totalPrice/2) {
                    nPriceLower = nPriceUpper;
                }
            }
        }

        for (int j = 0; j < m && mPriceUpper <= totalPrice/2; j++) {
            for (int i = 0; i < n; i++) {
                mPriceUpper += nums[i][j];
                if (mPriceUpper <= totalPrice/2) {
                    mPriceLower = mPriceUpper;
                }
            }
        }

        System.out.println(Math.min(
                Math.min(Math.abs(totalPrice - 2 * nPriceUpper), Math.abs(totalPrice - 2 * nPriceLower)),
                Math.min(Math.abs(totalPrice - 2 * mPriceUpper), Math.abs(totalPrice - 2 * mPriceLower))
        ));

    }

}
