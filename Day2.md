# 代码随想录算法训练营第二天：209. 长度最小的子数组，59. 螺旋矩阵II，kamacoder 58.区间和，kamacoder 44. 开发商购买土地

## 209. 长度最小的子数组

### 适用条件

长度最小的子数组算法（滑动窗口）适用条件：

1. **连续子序列**：需要处理连续元素的子序列/子数组问题
2. **优化需求**：需要避免暴力解法中的重复计算
3. **条件判断**：涉及满足某种条件（如和≥目标值）的子数组
4. **最优化问题**：寻找最长/最短满足条件的子数组

不适用情况：
- 需要考虑非连续子序列的场景
- 问题不涉及"窗口大小"调整的场景
- 数据结构不支持高效随机访问（如链表，除非另外处理）

### 代码实现
```java
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
```
### 解题思路

#### 方法一：暴力解法（嵌套循环）

最直观的方法是使用嵌套循环检查所有可能的子数组：

```java
// 暴力解法 - 时间复杂度O(n³)或优化后O(n²)
public int minSubArrayLen(int target, int[] nums) {
    int minLength = Integer.MAX_VALUE;
    
    for (int start = 0; start < nums.length; start++) {
        for (int end = start; end < nums.length; end++) {
            int sum = 0;
            // 计算从start到end的子数组和
            for (int i = start; i <= end; i++) {
                sum += nums[i];
            }
            // 如果子数组和大于等于目标值，更新最小长度
            if (sum >= target) {
                minLength = Math.min(minLength, end - start + 1);
                break; // 找到满足条件的子数组后就跳出内层循环
            }
        }
    }
    
    return minLength == Integer.MAX_VALUE ? 0 : minLength;
}
```

**优化的暴力解法** - 减少一层循环：

```java
// 优化的暴力解法 - 时间复杂度O(n²)
public int minSubArrayLen(int target, int[] nums) {
    int minLength = Integer.MAX_VALUE;
    
    for (int start = 0; start < nums.length; start++) {
        int sum = 0;
        for (int end = start; end < nums.length; end++) {
            sum += nums[end]; // 累加当前元素
            
            // 找到满足条件的子数组
            if (sum >= target) {
                minLength = Math.min(minLength, end - start + 1);
                break; // 可以选择break或不break，取决于是否需要继续寻找
            }
        }
    }
    
    return minLength == Integer.MAX_VALUE ? 0 : minLength;
}
```

**复杂度分析**：
- 时间复杂度：O(n²)，两层嵌套循环
- 空间复杂度：O(1)，只需常数额外空间

#### 方法二：滑动窗口（双指针）

滑动窗口是解决该问题的最优方法，它利用双指针在数组上动态调整"窗口"大小：

```java
// 滑动窗口 - 时间复杂度O(n)
public int minSubArrayLen(int target, int[] nums) {
    int minLength = Integer.MAX_VALUE;
    int left = 0; // 窗口左边界
    int sum = 0;  // 当前窗口内的元素和
    
    for (int right = 0; right < nums.length; right++) {
        sum += nums[right]; // 扩展右边界
        
        // 当窗口内元素和大于等于目标值时，尝试收缩左边界
        while (sum >= target) {
            minLength = Math.min(minLength, right - left + 1);
            sum -= nums[left]; // 移除左边界元素
            left++; // 收缩左边界
        }
    }
    
    return minLength == Integer.MAX_VALUE ? 0 : minLength;
}
```

**实现分析**：
- 维护一个可变大小的窗口，用`left`和`right`指针表示窗口的左右边界
- 通过右指针(`right`)不断扩展窗口，增加元素到窗口和
- 当窗口内元素和满足条件时，尝试通过左指针(`left`)收缩窗口，以找到最小长度
- 不断重复以上步骤，直到右指针到达数组末尾

**优势**：
- 时间复杂度为O(n)，每个元素最多被访问两次（一次添加到窗口，一次从窗口移除）
- 比暴力方法高效得多，特别是对于大型数组
- 利用了数组元素的连续性特性

### 算法复杂度

| 方法 | 时间复杂度 | 空间复杂度 |
|------|------------|------------|
| 暴力解法 | O(n²) | O(1) |
| 滑动窗口 | O(n) | O(1) |

两种方法的空间复杂度都是O(1)，因为只使用了有限的变量。

### 易错点与注意事项

1. **初始化注意**：将最小长度初始化为`Integer.MAX_VALUE`而不是数组长度，以处理没有满足条件子数组的情况（详见下方Java极值常量部分）。

2. **结果检查**：最后需要检查是否找到了满足条件的子数组，如果没有则返回0：
   ```java
   return minLength == Integer.MAX_VALUE ? 0 : minLength;
   ```

3. **窗口缩小条件**：注意循环的条件是`while (sum >= target)`而不是`if`，因为我们需要尽可能缩小窗口。

4. **边界情况处理**：
   - 空数组：返回0
   - 所有元素之和小于目标值：返回0
   - 单个元素大于等于目标值：返回1

5. **滑动窗口的不变量**：在窗口收缩循环前，窗口内元素和可能大于等于目标值；在循环后，窗口内元素和一定小于目标值。

### 滑动窗口的扩展应用

滑动窗口技巧可应用于许多其他问题：
- 最大连续子数组和
- 字符串中的最长无重复字符子串
- 包含特定字符的最小窗口子串
- 最大连续1的个数
- K个不同整数的子数组

## Java知识点补充

### Java中的极值常量

Java中提供了一系列表示数值类型极限值的常量，这些常量在算法题中非常有用，使用这些常量比使用魔法数字(如2147483647)更清晰、更不易出错：

**整型极值：**
- `Integer.MAX_VALUE`: 整型能表示的最大值 (2^31 - 1 = 2,147,483,647)
- `Integer.MIN_VALUE`: 整型能表示的最小值 (-2^31 = -2,147,483,648)

**其他类型的极值：**
- `Long.MAX_VALUE` / `Long.MIN_VALUE`: 长整型的最大/最小值
- `Double.MAX_VALUE` / `Double.MIN_VALUE`: 双精度浮点型的最大/最小值
- `Float.MAX_VALUE` / `Float.MIN_VALUE`: 单精度浮点型的最大/最小值

在本章的"长度最小的子数组"问题中，我们使用`Integer.MAX_VALUE`来初始化`minLength`变量，这确保了任何实际子数组的长度都会小于初始值，从而能够正确更新最小长度。


## 59. 螺旋矩阵II

### 适用条件

螺旋矩阵算法适用条件：

1. **矩阵填充**：需要按特定顺序（顺时针或逆时针螺旋）填充或遍历矩阵
2. **模拟过程**：能够模拟方向变化的过程
3. **边界处理**：能清晰定义并更新矩阵的边界

不适用情况：
- 不需要按特定顺序处理矩阵元素的场景
- 矩阵处理可以通过数学公式直接计算的情况

### 代码实现
```java
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
```
### 解题思路

螺旋矩阵问题要求我们按照螺旋顺序（通常是顺时针）填充一个n×n的矩阵，从1开始递增填充直到n²。

#### 方法一：按层模拟

这种方法将矩阵看作由多个"层"组成，从外到内一层层处理：

```java
public int[][] generateMatrix(int n) {
    int[][] matrix = new int[n][n];
    
    int num = 1; // 开始填充的数字
    int left = 0, right = n - 1; // 左右边界
    int top = 0, bottom = n - 1; // 上下边界
    
    while (num <= n * n) {
        // 从左到右
        for (int i = left; i <= right; i++) {
            matrix[top][i] = num++;
        }
        top++; // 上边界下移
        
        // 从上到下
        for (int i = top; i <= bottom; i++) {
            matrix[i][right] = num++;
        }
        right--; // 右边界左移
        
        // 从右到左
        for (int i = right; i >= left; i--) {
            matrix[bottom][i] = num++;
        }
        bottom--; // 下边界上移
        
        // 从下到上
        for (int i = bottom; i >= top; i--) {
            matrix[i][left] = num++;
        }
        left++; // 左边界右移
    }
    
    return matrix;
}
```

**实现分析**：
- 初始化一个n×n的矩阵和四个边界变量：`left`、`right`、`top`、`bottom`
- 使用一个循环按顺时针方向填充矩阵：
  1. 从左到右填充一行（`top`行，从`left`到`right`）
  2. 将上边界`top`下移一位
  3. 从上到下填充一列（`right`列，从`top`到`bottom`）
  4. 将右边界`right`左移一位
  5. 从右到左填充一行（`bottom`行，从`right`到`left`）
  6. 将下边界`bottom`上移一位
  7. 从下到上填充一列（`left`列，从`bottom`到`top`）
  8. 将左边界`left`右移一位
- 重复上述步骤直到填充完整个矩阵（即`num > n*n`）

#### 方法二：模拟螺旋路径

另一种方法是使用方向数组显式模拟转向：

```java
public int[][] generateMatrix(int n) {
    int[][] matrix = new int[n][n];
    
    // 方向数组：右、下、左、上
    int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    int dirIndex = 0; // 当前方向索引
    
    int row = 0, col = 0; // 当前位置
    
    for (int num = 1; num <= n * n; num++) {
        matrix[row][col] = num; // 填充当前位置
        
        // 计算下一个位置
        int nextRow = row + directions[dirIndex][0];
        int nextCol = col + directions[dirIndex][1];
        
        // 检查是否需要改变方向
        if (nextRow < 0 || nextRow >= n || nextCol < 0 || nextCol >= n || matrix[nextRow][nextCol] != 0) {
            // 更改方向（循环使用方向数组）
            dirIndex = (dirIndex + 1) % 4;
            // 重新计算下一个位置
            nextRow = row + directions[dirIndex][0];
            nextCol = col + directions[dirIndex][1];
        }
        
        // 移动到下一个位置
        row = nextRow;
        col = nextCol;
    }
    
    return matrix;
}
```

**实现分析**：
- 使用方向数组`directions`表示四个移动方向：右、下、左、上
- 使用`dirIndex`指示当前方向
- 在每个位置填入相应的数字后，尝试按当前方向移动
- 如果移动会导致超出边界或到达已填充的位置，则更改方向
- 按新方向继续填充，直到矩阵完全填满

### 算法复杂度

| 方法 | 时间复杂度 | 空间复杂度 |
|------|------------|------------|
| 按层模拟 | O(n²) | O(1) |
| 模拟螺旋路径 | O(n²) | O(1) |

两种方法的时间复杂度都是O(n²)，因为需要填充整个n×n矩阵。空间复杂度为O(1)，不包括存储结果的空间。

### 易错点与注意事项

1. **边界条件**：按层模拟时需要确保内侧的边界不会交叉，通常在循环条件中通过比较`left <= right && top <= bottom`来检查。

2. **方向变换时机**：在模拟螺旋路径时，需要正确判断何时改变方向。方向改变的条件是：下一个位置超出边界或者下一个位置已被填充。

3. **奇数维度矩阵**：对于奇数n，矩阵中心点是最后填充的。两种算法都能正确处理这种情况。

4. **单元素和空矩阵**：
   - n=1时：矩阵只有一个元素[1]
   - n=0时：返回空矩阵[]（通常不在问题范围内）

### 实际应用

螺旋矩阵模式在以下场景中有应用：
- 图像处理中的某些扫描算法
- 某些空间填充曲线（如希尔伯特曲线的简化形式）
- 数据可视化中的特定布局算法
- 某些游戏中的地图生成或探索算法

## kamacoder 58.区间和

```java
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
```

## kamacoder 44. 开发商购买土地
```java
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
```
