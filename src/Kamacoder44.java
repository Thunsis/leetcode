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

        // 初始化数组的同时把totalPrice计算出来
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                nums[i][j] = scanner.nextInt();
                totalPrice += nums[i][j];
            }
        }

        /*
            把横向最接近totalPrice/2的以下及以上两种price算出来
            a b c
            - - -
            d e f
            g h i
         */
        for (int i = 0; i < n && nPriceUpper <= totalPrice/2; i++) {
            for (int j = 0; j < m; j++) {
                nPriceUpper += nums[i][j];
                if (nPriceUpper <= totalPrice/2) {
                    nPriceLower = nPriceUpper;
                }
            }
        }

        /*
            把纵向最接近totalPrice/2的以下及以上两种price算出来
            a | b c
            d | e f
            g | h i
         */
        for (int j = 0; j < m && mPriceUpper <= totalPrice/2; j++) {
            for (int i = 0; i < n; i++) {
                mPriceUpper += nums[i][j];
                if (mPriceUpper <= totalPrice/2) {
                    mPriceLower = mPriceUpper;
                }
            }
        }

        // 无非这四种情况之一为差值最小
        System.out.println(Math.min(
                Math.min(Math.abs(totalPrice - 2 * nPriceUpper), Math.abs(totalPrice - 2 * nPriceLower)),
                Math.min(Math.abs(totalPrice - 2 * mPriceUpper), Math.abs(totalPrice - 2 * mPriceLower))
        ));

        scanner.close();
    }

}
