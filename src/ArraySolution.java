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


    public static void main(String[] args) {
        int[] myArray = {-4,-1,0,3,10};

//        int target = 100;
        int val = 2;
        ArraySolution mySolution = new ArraySolution();
//        System.out.println(mySolution.search(myArray, target));
//        System.out.println(mySolution.removeElement(myArray, val));
        int[] newNums = mySolution.sortedSquares(myArray);
        for(int i = 1; i < newNums.length; i++) {
            System.out.print(newNums[i] + "\t");
        }


    }
}
