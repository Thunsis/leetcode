// kamacoder 58

import java.util.Scanner;

public class Kamacoder58 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];

        // 把每个位置所对应的它之前所有数加总的值存到数组里
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                nums[i] = scanner.nextInt();
            } else {
                nums[i] = nums[i-1] + scanner.nextInt();
            }
        }

        // 前缀和思想，0~b - 0~a = a~b
        while (scanner.hasNext()) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            if (a == 0) {
                System.out.println(nums[b]);
            } else {
                System.out.println(nums[b] - nums[a-1]);
            }
        }
        scanner.close();

    }
}