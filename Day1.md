# 代码随想录算法训练营第一天：704. 二分查找，35.搜索插入位置，34. 在排序数组中查找元素的第一个和最后一个位置, 27. 移除元素，977.有序数组的平方

## 704. 二分查找

### 适用条件

二分查找算法的适用条件：

1. **有序性**：数据必须是有序的（升序或降序）
2. **随机访问**：数据结构必须支持高效的随机访问（如数组）
3. **边界明确**：需要明确定义搜索空间的上下界
4. **时间要求**：当需要将O(n)的查找优化到O(log n)时

不适用情况：
- 数据是无序的（除非先排序，但排序成本可能抵消二分查找的优势）
- 数据结构不支持高效随机访问（如链表）
- 数据集很小（此时线性搜索可能更快，因为常数因子更小）
- 频繁插入删除的数据（维护有序性成本高）

### 算法复杂度

#### 时间复杂度
- **平均情况**: O(log n)
- **最坏情况**: O(log n)
- **最好情况**: O(1) - 当目标值位于数组中间位置时

每次操作将搜索空间减少一半，这导致了对数级的时间复杂度。对于大型数据集尤其高效。
举例说明：
- 对于含有10个元素的数组，最多需要约4次比较
- 对于含有1000个元素的数组，最多需要约10次比较
- 对于含有1,000,000个元素的数组，最多需要约20次比较

#### 空间复杂度
- **O(1)** - 恒定的额外空间，仅使用几个变量（left, right, mid）

迭代实现的二分查找不使用额外的数据结构或递归调用，因此具有常数级的空间复杂度。

### 代码实现

```java
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

## 35. 搜索插入位置

### 适用条件

搜索插入位置算法的适用条件：

1. **有序数组**：输入数组必须是按升序排列的
2. **查找+插入**：需要找到目标值在数组中的位置，或者它应该被插入的位置
3. **效率要求**：要求时间复杂度为O(log n)，而不是线性搜索的O(n)

不适用情况：
- 无序数组
- 需要处理重复元素的复杂规则（基本版本假设无重复元素或新元素插入到等值元素后面）

### 代码实现

```java
public int searchInsert(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        // 每个地方都要注意让nums[mid]和target做比较，而不是用mid和target做比较
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }

    // 经推算当target落在合理范围的right左边的时候需要特别让其在right的位置，而不是right+1
//        if (right >= 0 && right <= nums.length - 1) {
//            if (target < nums[right]) {
//                return right;
//            }
//        }
//
//        return right + 1;
    return left; // 为什么返回left是个值得思考的问题
    // 换个角度想，这可能和计算mid时/2会自动向下取整有关
}
```

### 为什么返回left是正确的？

返回`left`是搜索插入位置算法中的一个精妙之处，它利用了二分查找终止时指针的自然位置特性：

1. **二分查找终止时的指针位置**：
   - 当循环终止时（即`left > right`），`left`指针恰好指向第一个大于等于目标值的元素位置
   - `right`指针恰好指向最后一个小于目标值的元素位置
   - 因此，`left`正好是目标值应该插入的位置

2. **与整数除法向下取整的深层关系**：
   - 计算`mid = left + (right - left) / 2`时，整数除法会向下取整（而不是四舍五入或向上取整）
   - 这个向下取整的特性在二分查找中起着至关重要的作用，尤其对`return left`的正确性有直接影响
   - 当搜索区间包含偶数个元素时，有两个"中间"元素，向下取整使算法总是选择**左侧**的那个作为`mid`
   
   ```
   例如区间[3,4,5,6]中：
   - left = 0, right = 3
   - mid = 0 + (3-0)/2 = 0 + 1 = 1
   - mid指向索引1的值4（而不是5）
   ```
   
   - 这种偏向与指针更新规则完美配合：
     - 当`target > nums[mid]`时，我们将`left = mid + 1`，完全排除了当前mid及左侧所有元素
     - 当`target < nums[mid]`时，我们将`right = mid - 1`，完全排除了当前mid及右侧所有元素
     - 由于mid偏左，这确保了当有两个可能的中间点时，我们的判断和排除不会错过任何插入位置

3. **所有可能情况的分析**：
   - **目标值存在于数组中**：循环会在发现目标时直接返回
   - **目标值小于所有元素**：`right`最终变为-1，`left`为0，返回`left`(0)正确
   - **目标值大于所有元素**：`left`最终变为`nums.length`，返回`left`正确
   - **目标值应插入数组中间**：考虑搜索区间缩小到两个元素的情况，例如区间`[3,5]`插入值4：
     ```
     1. left=0, right=1, mid=0（指向值3）
     2. 3 < 4，所以left = mid + 1 = 1
     3. 现在left=1, right=1, mid=1（指向值5）
     4. 5 > 4，所以right = mid - 1 = 0
     5. 此时left=1, right=0，循环终止
     6. return left给出索引1，正好是4应该插入的位置
     ```
     - 如果算法使用向上取整，`mid`会偏向右侧元素，可能导致在指针更新时跳过正确的插入位置

4. **替代方案的问题**：
   - 返回`right + 1`看起来也是合理的
   - 但这需要处理边界情况（例如`right`可能是-1）
   - 而返回`left`天然地处理了所有边界情况

从数学上讲，这种行为可以证明：由于向下取整和指针更新规则的组合，`left`指针总是"追赶"正确的插入位置，而绝不会超过它。同时，`right`指针总是"接近"但永远不会小于正确位置之前的元素。当二分查找终止时，`left`恰好指向第一个大于或等于目标值的元素位置，这正是插入位置的定义。

这个数学性质可以更详细地解释为：

1. **左指针的"追赶"行为**：
   - `left`指针每次更新时，都是基于`mid`的位置：`left = mid + 1`
   - 由于`mid`的计算使用了向下取整，`mid`总是偏向左侧
   - 所以`left`的更新是谨慎的，只有当确定目标值大于当前中间值时才会右移
   - 这确保了`left`指针始终不会跳过正确的插入位置，而是一步步"追赶"它

2. **右指针的"接近"行为**：
   - `right`指针每次更新为`mid - 1`
   - 这保证了`right`永远不会小于应该插入位置之前的元素索引
   - 即使`right`最终变为-1（目标值小于所有元素），这也正确地表示插入位置应在数组最前面

3. **维持的循环不变量**：
   - 在每次循环中，我们都维持这样的不变量：
     - `nums[0...left-1]`中的所有元素都小于目标值
     - `nums[right+1...n-1]`中的所有元素都大于等于目标值
   - 随着算法进行，搜索范围不断缩小，但这个不变量始终成立
   - 当循环终止时（`left > right`），根据不变量，`left`指向的位置恰好是第一个大于等于目标值的元素

4. **终止状态的保证**：
   - 二分查找必定会终止，且终止时必有`left = right + 1`
   - 此时，根据维持的不变量：
     - 索引`left-1`及其左侧的所有元素都小于目标值
     - 索引`right+1`（即`left`）及其右侧的所有元素都大于等于目标值
   - 因此，`left`正好是目标值应该插入的位置

这就是为什么`return left`能够处理所有可能的插入情况，并且不需要任何额外的条件逻辑。

### 算法复杂度

- **时间复杂度**：O(log n) - 二分查找每次将搜索空间减半
- **空间复杂度**：O(1) - 只使用常量额外空间

### 实际应用

搜索插入位置算法可应用于：
- 有序数据结构中维护元素顺序
- 数据库索引查找
- 自动完成/预测文本功能中查找最接近的匹配

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

### 适用条件

移除元素算法的适用条件：

1. **原地操作**：需要在不使用额外数组的情况下修改原数组
2. **空间限制**：只允许使用O(1)的额外空间
3. **元素识别**：需要从集合中识别并移除特定元素
4. **长度追踪**：需要返回处理后的有效长度

不适用情况：
- 需要保留原始数组不变的场景
- 有足够额外空间且性能要求不高的情况下，创建新数组可能更简单
- 数据结构不支持随机访问的情况（如链表，有更高效的方法）

### 代码实现
```java
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
```

### 解题思路

移除元素问题有多种解决方案，每种方案都有不同的时间复杂度和情景适用性。

#### 方法一：暴力解法（嵌套循环）

最直观的方法是使用嵌套循环：当找到一个等于目标值的元素时，将其后面所有元素往前移动一位。

```java
// 性能较差的解法 - 时间复杂度O(n²)
public int removeElement(int[] nums, int val) {
    int k = nums.length;
    int i = 0;
    while (i < nums.length) {
        if (nums[i] == val) {
            k--;
            // 将后面的所有元素向前移一位
            for (int j = i; j < nums.length - 1; j++) {
                nums[j] = nums[j+1];
            }
        } else {
            i++;
        }
    }
    return k;
}
```

**实现分析**：
- 外循环遍历数组中的每个元素
- 当遇到目标值时：
  1. 长度计数器`k`减1
  2. 内循环将该位置之后的所有元素前移一位
  3. **关键错误点**: 外循环索引`i`不递增，因为当前位置已被新元素替换
- 当前元素不是目标值时，外循环索引`i`递增

**问题与缺陷**：
- 时间复杂度为O(n²)，效率低下
- 每找到一个目标元素就要移动大量元素
- 数组越大或目标值出现越频繁，性能越差

#### 方法二：快慢指针法（单次遍历）

一种更高效的方法是使用"快慢指针"：
- "慢指针"(`index`)指向当前可以放置非目标值的位置
- "快指针"(`i`)用于遍历数组

```java
// 单次遍历 - 时间复杂度O(n)
public int removeElement(int[] nums, int val) {
    int index = 0;
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] != val) {
            nums[index] = nums[i];
            index++;
        }
    }
    return index;
}
```

**实现分析**：
- `index`追踪不等于目标值的元素应该放置的位置
- 遍历数组，只有当元素不等于目标值时才操作：
  1. 将该元素复制到`index`指定的位置
  2. `index`向前移动一位
- 遍历结束后，`index`即为新数组的长度

**优势**：
- 时间复杂度为O(n)，只需一次遍历
- 元素移动次数最少（每个保留的元素最多移动一次）
- 简单直观，易于理解

#### 方法三：首尾双指针法

当我们不关心元素的相对顺序时，可以使用更高效的首尾双指针方法：

```java
// 首尾双指针 - 时间复杂度O(n)，但移动次数可能更少
public int removeElement(int[] nums, int val) {
    int left = 0;
    int right = nums.length - 1;
    
    while (left <= right) { // 等号处理边界情况
        if (nums[left] == val) {
            // 用最后一个元素替换当前元素
            nums[left] = nums[right];
            right--;
        } else {
            left++;
        }
    }
    
    return right + 1;
}
```

**实现分析**：
- `left`从左向右遍历，`right`指向数组末尾有效元素
- 当`left`指向目标值时：
  1. 用`right`位置的元素覆盖它
  2. `right`左移一位（缩小有效区间）
  3. `left`不移动，因为需要检查新复制过来的元素
- 当`left`指向非目标值时，`left`右移一位
- 循环结束条件为`left <= right`（处理数组只有一个元素的情况）

**返回值说明**：
为什么返回`right + 1`而不是`left + 1`？
- 当循环结束时，`right`指向最后一个有效元素的位置
- 由于数组索引从0开始，长度 = 最大索引 + 1
- 例如：数组[1,2,3]的最大索引是2，长度是3 = 2 + 1
- 而`left`此时指向的是第一个"超出有效范围"的位置，不适合用于计算长度

**优势**：
- 时间复杂度为O(n)
- 当目标元素较少时，操作次数比方法二还少
- 特别适合不需要保持元素顺序的场景

### 算法复杂度比较

| 方法 | 时间复杂度 | 空间复杂度 | 是否保持元素相对顺序 |
|------|------------|------------|----------------------|
| 暴力解法 | O(n²) | O(1) | 是 |
| 快慢指针 | O(n) | O(1) | 是 |
| 首尾双指针 | O(n) | O(1) | 否 |

### 易错点与注意事项

1. **循环条件**：首尾双指针方法中，使用`left <= right`而非`left < right`，这确保了对单元素数组的正确处理。

2. **元素重复检查**：在首尾双指针方法中，替换元素后不要忘记检查新元素是否为目标值。

3. **返回值计算**：首尾双指针方法返回`right + 1`，因为`right`指向最后一个有效元素的索引。

4. **边界情况**：
   - 空数组：返回0
   - 所有元素都是目标值：返回0
   - 没有目标值：返回原数组长度
   - 单元素数组：上述方法都能正确处理
    
## 977. 有序数组的平方

### 适用条件

有序数组平方算法的适用条件：

1. **已排序数据**：输入数组已按升序（非递减）排序
2. **包含负数**：数组可能包含负数，平方后会改变其相对大小
3. **结果有序性**：需要结果数组保持有序
4. **效率要求**：需要优于简单的"平方后排序"解法的O(nlogn)复杂度

不适用情况：
- 数组本身无序
- 只包含非负数的有序数组（此时直接平方即可保持有序性）
- 不要求结果有序时

### 代码实现
```java
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
```

### 解题思路

这个问题的挑战在于：数组已排序，但可能包含负数，而负数平方后会变大，打破原有的排序。

#### 方法一：平方后排序

最直观的方法是将每个元素平方，然后对结果数组排序。

```java
// 暴力解法 - 时间复杂度O(nlogn)
public int[] sortedSquares(int[] nums) {
    int[] result = new int[nums.length];
    
    // 计算每个元素的平方
    for (int i = 0; i < nums.length; i++) {
        result[i] = nums[i] * nums[i];
    }
    
    // 对结果排序
    Arrays.sort(result);
    
    return result;
}
```

**实现分析**：
- 直接遍历数组，计算每个元素的平方
- 使用内置排序函数对结果排序
- 简单直观，但没有利用输入数组已排序的特性

#### 方法二：双指针法

更优的方法是利用输入数组已排序的特性和平方的性质：两端的元素平方后可能最大。

```java
// 双指针法 - 时间复杂度O(n)
public int[] sortedSquares(int[] nums) {
    int left = 0;
    int right = nums.length - 1;
    int[] result = new int[nums.length];
    int position = nums.length - 1;  // 从结果数组的末尾开始填充
    
    while (left <= right) {
        int leftSquare = nums[left] * nums[left];
        int rightSquare = nums[right] * nums[right];
        
        if (leftSquare > rightSquare) {
            result[position] = leftSquare;
            left++;
        } else {
            result[position] = rightSquare;
            right--;
        }
        position--;
    }
    
    return result;
}
```

**优化实现**：避免重复计算平方

```java
public int[] sortedSquares(int[] nums) {
    int left = 0;
    int right = nums.length - 1;
    int[] result = new int[nums.length];
    int current = nums.length - 1;  // 从结果数组的末尾开始填充
    
    while (left <= right) {
        // 计算两端元素的平方
        int leftSquare = nums[left] * nums[left];
        int rightSquare = nums[right] * nums[right];
        
        if (leftSquare <= rightSquare) {
            result[current] = rightSquare;
            right--;
        } else {
            result[current] = leftSquare;
            left++;
        }
        current--;
    }
    
    return result;
}
```

**实现分析**：
- 使用两个指针分别指向原数组的两端
- 比较两端元素平方的大小，将较大者放入结果数组的末尾
- 移动相应的指针，并将结果数组的填充位置向前移动
- 重复直到两个指针相遇

**优势**：
- 时间复杂度为O(n)，只需一次遍历
- 充分利用了输入数组已排序的特性
- 不需要额外的排序操作

### 算法复杂度

| 方法 | 时间复杂度 | 空间复杂度 |
|------|------------|------------|
| 平方后排序 | O(nlogn) | O(n) |
| 双指针法 | O(n) | O(n) |

空间复杂度O(n)来自于需要创建一个新的结果数组。

### 性能优化技巧

1. **避免重复计算**：如果需要多次使用平方值，先计算一次再使用，而不是重复调用`Math.pow()`函数。

2. **直接使用乘法**：对于平方运算，使用`nums[i] * nums[i]`比调用`Math.pow(nums[i], 2)`更高效，因为避免了函数调用开销。

3. **正确的初始化**：一开始就创建正确大小的结果数组，避免动态扩容。

### 易错点与注意事项

1. **边界条件处理**：
   - 空数组：返回空数组
   - 单元素数组：直接返回该元素的平方
   
2. **循环条件**：使用`left <= right`确保处理所有元素。

3. **从后向前填充**：双指针法中从结果数组末尾开始填充是关键，这样能确保按照非递减顺序排列。

4. **符号注意**：原数组可能包含正数、负数和零，但平方后全为非负数。平方运算会使负数的绝对值更重要。

### 实际应用

这种双指针技巧适用于许多需要合并或比较两个有序集合的场景，例如：
- 合并两个有序数组
- 查找两个有序数组的交集
- 判断一个数组是否包含另一个数组的所有元素
