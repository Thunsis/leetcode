# 代码随想录算法训练营第一天：704. 二分查找，27. 移除元素，977.有序数组的平方 

## 704. 二分查找

### 代码实现

```java
public class Solution {
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
        }
        return -1;
    }
}
```

### 二分查找的两种写法

#### 左闭右闭区间 [left, right]

这种实现中搜索区间的两个端点都被包括在内。

```java
public int binarySearch(int[] nums, int target) {
    // 初始化区间[0, nums.length-1]，两端都包含
    int left = 0;
    int right = nums.length - 1;
    
    // 当left > right时表示区间为空，搜索结束
    while (left <= right) {
        // 计算中间位置（防溢出写法）
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;  // 找到目标值
        } else if (nums[mid] < target) {
            // 目标在右半部分[mid+1, right]
            left = mid + 1;
        } else {
            // 目标在左半部分[left, mid-1]
            right = mid - 1;
        }
    }
    
    return -1;  // 未找到
}
```

#### 左闭右开区间 [left, right)

这种实现中搜索区间包括左端点但不包括右端点。

```java
public int binarySearch(int[] nums, int target) {
    // 初始化区间[0, nums.length)，不包括右端点
    int left = 0;
    int right = nums.length;
    
    // 当left == right时区间为空，搜索结束
    while (left < right) {
        // 计算中间位置（防溢出写法）
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;  // 找到目标值
        } else if (nums[mid] < target) {
            // 目标在右半部分[mid+1, right)
            left = mid + 1;
        } else {
            // 目标在左半部分[left, mid)
            right = mid;
        }
    }
    
    return -1;  // 未找到
}
```

### 二分查找易错点与技巧

#### 计算中间值的技巧

中间值计算推荐使用：`mid = left + (right - left) / 2`

- 避免大整数相加溢出问题
- 当left和right都很大时，直接`(left + right)`可能超出int类型范围
- `left + (right - left) / 2`数学上等价但不会溢出

#### 边界条件处理

当二分查找缩小到只剩一个数字时：

**左闭右闭区间 [left, right]**
- 当`left == right`时：
  - `right - left = 0`
  - `mid = left + 0 = left = right`
  - mid正确指向唯一元素

**左闭右开区间 [left, right)**
- 当`right = left + 1`时：
  - `right - left = 1`
  - `mid = left + 0 = left` (整数除法)
  - mid同样指向区间中唯一元素

### 深入理解二分查找

#### 区间定义直观解释

**左闭右闭 [left, right]**  
想象你在一排人中找人，你说"我要在第3个到第8个人之间找"，这就是左闭右闭，第3个和第8个都会被检查。

**左闭右开 [left, right)**  
想象你在找房间，你说"我要找101到109号房间"，但按惯例，109不包括在内，实际查找101到108，这就是左闭右开。

#### 详细对比图解

假设我们在数组 [2,5,8,12,16,23,38,56,72,91] 中查找 23:

**左闭右闭 [left, right]:**
```
初始: left=0, right=9
     [2,5,8,12,16,23,38,56,72,91]
      ^                       ^
     left                   right
```

- 循环条件: `left <= right` (区间至少有一个元素才继续)
- 当找到中间元素小于目标时: `left = mid + 1` (排除mid)
- 当找到中间元素大于目标时: `right = mid - 1` (排除mid)

**左闭右开 [left, right):**
```
初始: left=0, right=10
     [2,5,8,12,16,23,38,56,72,91]
      ^                         ^
     left                     right (注意right指向数组之外)
```

- 循环条件: `left < right` (区间至少有一个元素才继续)
- 当找到中间元素小于目标时: `left = mid + 1` (排除mid)
- 当找到中间元素大于目标时: `right = mid` (排除mid但保持右开特性)

#### 关键差异记忆口诀

1. **初始化**:
    - 左闭右闭: `right = 数组长度-1` (最后一个元素)
    - 左闭右开: `right = 数组长度` (超出一个位置)

2. **循环条件**:
    - 左闭右闭: `<=` (相等时还有一个元素)
    - 左闭右开: `<` (相等时区间为空)

3. **右边界更新**:
    - 左闭右闭: `mid-1` (排除中间值)
    - 左闭右开: `mid` (右边界本来就不包含)

#### 视觉区间变化

对于左闭右闭 [left, right]:
- 区间 [3,7] 表示检查索引 3、4、5、6、7
- 区间 [3,3] 表示只检查索引 3
- 区间 [4,3] 表示区间为空，查找结束

对于左闭右开 [left, right):
- 区间 [3,8) 表示检查索引 3、4、5、6、7
- 区间 [3,4) 表示只检查索引 3
- 区间 [3,3) 表示区间为空，查找结束

## Java知识点补充

### Java中不同长度获取方式

Java中不同数据结构的长度获取方式不同：
- 数组：`array.length` 属性
- 字符串：`string.length()` 方法
- 集合：`collection.size()` 方法

**历史原因：**
- 数组：Java早期设计受C/C++影响，作为低级语言结构，使用length属性追求性能
- 字符串API：初始版本作为完整对象设计，使用length()方法遵循面向对象封装原则
- 集合框架：Java 1.2(1998年)引入，借鉴了C++ STL等库的命名惯例
- Java特别重视向后兼容性，不会轻易改变已有接口，所以保持这些差异

## 27. 移除元素

### 待补充

<!-- 此处可以添加第27题的内容 -->
