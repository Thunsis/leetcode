public class Solution704 {
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

    public static void main(String[] args) {
        int[] myArray = {1,2,5,7,10,13,15,19,31,46,100};

        int target = 100;
        Solution704 mySolution = new Solution704();
        System.out.println(mySolution.search(myArray, target));

    }
}
