public class ArraySolution {
    // 704 - 左闭右闭
    // 时间复杂度 O(log n)
    // 空间复杂度 O(1)
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2; // 避免大整数溢出
            if (target == nums[mid]) {
                return mid;
            } else if (target > nums[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    // 704 - 左开右闭
    // 时间复杂度 O(log n)
    // 空间复杂度 O(1)
//    public int search(int[] nums, int target) {
//        int left = 0;
//        int right = nums.length;
//        while (left < right) {
//            int mid = left + (right - left) / 2;
//            if (target == nums[mid]) {
//                return mid;
//            } else if (target > nums[mid]) {
//                left = mid + 1;
//            } else {
//                right = mid;
//            }
//        }
//        return -1;
//    }


    // 27 - 性能很差的解法
    // 时间复杂度 O(n^2)
    // 空间复杂度 O(1)
//    public int removeElement(int[] nums, int val) {
//        int k = nums.length;
//        int i = 0;
//        while (i < nums.length) {
//            if (nums[i] == val) {
//                k--;
//                for (int j = i; j < nums.length - 1; j++) {
//                    nums[j] = nums[j+1];
//                }
//            } else {
//                i++;
//            }
//        }
//        return k;
//    }

    // 27 - 单次遍历
    // 时间复杂度 O(n)
    // 空间复杂度 O(1)
//    public int removeElement(int[] nums, int val) {
//        int index = 0;
//        for (int i = 0; i < nums.length; i++) {
//            if (nums[i] != val) {
//                nums[index] = nums[i]; // 不需要每次都将后面所有的都往前移
//                index++;
//            }
//        }
//        return index;
//    }

    // 27 - 双指针
    // 时间复杂度 O(n)
    // 空间复杂度 O(1)
    public int removeElement(int[] nums, int val) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) { // 这里等号要处理nums只有一个元素的情况
            if (nums[left] == val) {
                nums[left] = nums[right];
                right --;
            } else {
                left ++;
            }
        }
        return right + 1; //尤其注意
    }

    // 977 - 双指针
    // 时间复杂度 O(n)
    // 空间复杂度 O(n)
    public int[] sortedSquares(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int[] newNums = new int[nums.length];
        int current = nums.length - 1;
        // 从两边往中间遍历，初始最大值一定在最两端
        while (left <= right) {
            if (Math.pow(nums[left],2) <= Math.pow(nums[right],2)) {
                newNums[current] = (int) Math.pow(nums[right],2);
                right--;
            } else {
                newNums[current] = (int) Math.pow(nums[left],2);
                left++;
            }
            current--;
        }
        return newNums;
    }

    // 209 - 滑动窗口
    // 时间复杂度 O(n)
    // 空间复杂度 O(1)
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int right = 0;
        int length = 0;
        int minLength = Integer.MAX_VALUE;
        int sum = 0;
        while (right <= nums.length) {
            if (sum < target) {
                if (right <= nums.length - 1) { // right遍历到最后，right = nums.length，nums[right]越界
                    sum += nums[right];
                }
                right++;
            } else {
                length = right - left;
                minLength = minLength > length ? length : minLength;
                sum -= nums[left];
                left++;
            }
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    // 59 - 转圈
    // 时间复杂度 O(n^2)
    // 空间复杂度 O(1) (算法的辅助空间)
    public int[][] generateMatrix(int n) {
        int[][] result = new int[n][n];
        int startx = 0;
        int starty = 0;
        int offset = 1;
        int count = 1;
        int i;
        int j;

        // startx = starty = offset-1
        for (int k = 0; k < n/2; k++) {

            // 把握好每个循环的起点和终点，确保没有overlap
            // 起点 [startx][starty]，每往里一层-1

            // 起点 [startx][starty]
            // 终点 [startx][n-offset-1]
            for (j = startx; j < n - offset; j++) {
                result[startx][j] = count;
                count++;
            }

            // 起点 [startx][n-offset]
            // 终点 [n-offset-1][n-offset]
            for (i = starty; i < n - offset; i++) {
                result[i][j] = count;
                count++;
            }

            // 起点 [n-offset][n-offset]
            // 终点 [n-offset][offset]
            for (; j > offset - 1; j--) {
                result[i][j] = count;
                count++;
            }

            // 起点 [n-offset][offset-1]
            // 终点 [offset][offset-1]
            for (; i > offset - 1; i--) {
                result[i][j] = count;
                count++;
            }
            startx++;
            starty++;
            offset++;
        }
        // 对奇数最中间的最大数单独赋值
        if (n%2 == 1) {
            result[n/2][n/2] = count;
        }
        return result;
    }



    public static void main(String[] args) {
        int[] myArray = {2,3,1,2,4,3};

        int target = 7;
//        int val = 2;
        ArraySolution mySolution = new ArraySolution();
//        System.out.println(mySolution.search(myArray, target));
//        System.out.println(mySolution.removeElement(myArray, val));
//        int[] newNums = mySolution.sortedSquares(myArray);
//        for (int i = 1; i < newNums.length; i++) {
//            System.out.print(newNums[i] + "\t");
//        }
//        System.out.println(mySolution.minSubArrayLen(target, myArray));
        int n = 4;
        int[][] result = mySolution.generateMatrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(result[i][j] + "\t");
            }
        }


    }
}
