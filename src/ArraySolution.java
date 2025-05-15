public class ArraySolution {
    // 704 - 左闭右闭
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target == nums[mid]) {
                return mid;
            } else if (target > nums[mid]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
            System.out.println("left = " + left + "; right = " + right + "; mid = " + mid);
        }
        return -1;
    }

    // 704 - 左开右闭
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
//            System.out.println("left = " + left + "; right = " + right + "; mid = " + mid);
//        }
//        return -1;
//    }


    // 27 - 性能很差的解法
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
//    public int removeElement(int[] nums, int val) {
//        int index = 0;
//        for (int i = 0; i < nums.length; i++) {
//            if (nums[i] != val) {
//                nums[index] = nums[i];
//                index++;
//            }
//        }
//        return index;
//    }

    // 27 - 双指针
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

    // 977
    public int[] sortedSquares(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int[] newNums = new int[nums.length];
        int current = nums.length - 1;
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

    // 209
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int right = 0;
        int length = 0;
        int minLength = Integer.MAX_VALUE;
        int sum = 0;
        while (right <= nums.length) {
            if (sum < target) {
                if (right <= nums.length - 1) {
                    sum += nums[right];
                }
                right++;
            } else {
                length = right - left;
                if (minLength > length) {
                    minLength = length;
                }
                sum -= nums[left];
                left ++;
            }
        }
        if (minLength == Integer.MAX_VALUE) {
            return 0;
        } else {
            return minLength;
        }
    }

    // 59
    public int[][] generateMatrix(int n) {
        int[][] result = new int[n][n];
        int startx = 0;
        int starty = 0;
        int offset = 1;
        int count = 1;
        int i;
        int j;
        for (int k = 0; k < n/2; k++) {
            for (j = startx; j < n - offset; j++) {
                result[startx][j] = count;
                count++;
            }
            for (i = starty; i < n - offset; i++) {
                result[i][j] = count;
                count++;
            }
            for (; j > offset - 1; j--) {
                result[i][j] = count;
                count++;
            }
            for (; i > offset - 1; i--) {
                result[i][j] = count;
                count++;
            }
            startx++;
            starty++;
            offset++;
        }
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
