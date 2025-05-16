# 代码随想录算法训练营第三天：203. 移除链表元素，707. 设计链表，206. 反转链表

## 链表基础

链表是一种常见的数据结构，由节点组成，每个节点包含数据字段和指向下一个节点的引用。链表结构在内存中不需要连续空间，插入和删除操作的时间复杂度为O(1)（不考虑查找的时间）。

### ListNode 定义

```java
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
```

## 203. 移除链表元素

### 适用条件

移除链表元素算法适用条件：

1. **顺序遍历**：需要按顺序遍历链表的每个节点
2. **节点删除**：需要能够删除特定值的节点
3. **链表修改**：需要在不破坏链表结构的情况下修改链表

不适用情况：
- 需要随机访问的场景（链表不支持高效的随机访问）
- 需要特殊顺序处理节点的场景（如从尾到头遍历）

### 代码实现 - 普通解法

```java
// 203
// 时间复杂度 O(n)
// 空间复杂度 O(1)
public ListNode removeElements(ListNode head, int val) {
    // head为空的情况
    if (head == null) {
        return head;
    }

    // head等于val，移除head后新的head等于val，移除head后新的head为空 3种情况
    while (head.val == val) {
        head = head.next;
        if (head == null) {
            return head;
        }
    }

    // 非head节点等于val的情况
    ListNode temp = head;
    while (temp.next != null) {
        if (temp.next.val == val) {
            temp.next = temp.next.next;
        } else {
            temp = temp.next;
        }
    }
    return head;
}
```

### 代码实现 - 虚拟头节点

```java
// 203 - 构建虚拟头节点
// 时间复杂度 O(n)
// 空间复杂度 O(1)
public ListNode removeElements(ListNode head, int val) {
    // 构建虚拟头节点，使对head的操作不再特殊化
    ListNode dummyHead = new ListNode(val-1, head);

    ListNode temp = dummyHead;
    while (temp.next != null) {
        if (temp.next.val == val) {
            temp.next = temp.next.next;
        } else {
            temp = temp.next;
        }
    }
    return dummyHead.next;
}
```

### 解题思路

移除链表元素的核心思路是遍历链表，当发现某个节点的值等于目标值时，将其删除。在链表中删除节点意味着修改前一个节点的next指针，使其跳过当前节点指向下一个节点。

链表的处理有两种典型思路：
1. **直接处理原链表**：需要特殊处理头节点，因为头节点没有前驱节点
2. **使用虚拟头节点(dummy node)**：在原链表前添加一个虚拟节点，简化边界情况处理

#### 方法一：直接处理原链表

这种方法需要单独处理头节点：
1. 如果头节点的值等于目标值，需要移动头节点到下一个节点
2. A处理后，会循环到非目标值的节点作为头节点（或链表为空）
3. 处理后，遍历链表删除所有等于目标值的非头节点

**优势**：
- 不需要额外的虚拟头节点，空间效率略高
- 对于特定情况（如头节点不是目标值）有时代码执行速度更快

**缺点**：
- 需要单独处理头节点，导致代码逻辑分支更多
- 考虑边界情况更复杂

#### 方法二：虚拟头节点法

这种方法在链表前添加一个虚拟节点，然后统一处理所有节点：
1. 创建一个虚拟头节点，其next指向原链表的head
2. 遍历时从虚拟头节点开始，处理每个节点的next指针
3. 返回虚拟头节点的next作为新的头节点

**优势**：
- 无需单独处理头节点，代码逻辑更简洁
- 处理边界情况更容易
- 减少了特殊情况的处理，代码更加一致

**缺点**：
- 需要额外创建一个节点，理论上空间效率略低（但实际上影响微乎其微）

### 算法复杂度

| 方法 | 时间复杂度 | 空间复杂度 |
|------|------------|------------|
| 直接处理 | O(n) | O(1) |
| 虚拟头节点 | O(n) | O(1) |

两种方法的时间复杂度都是O(n)，因为需要遍历整个链表。空间复杂度都是O(1)，因为只使用了常数级的额外空间。

### 易错点与注意事项

1. **空链表处理**：检查输入链表是否为空
2. **头节点特殊处理**：不使用虚拟头节点时，需要特别注意头节点的处理
3. **指针更新**：删除节点时需要正确更新前一个节点的next指针
4. **循环条件**：在虚拟头节点方法中，使用temp.next != null作为循环条件
5. **返回值**：使用虚拟头节点时，返回dummyHead.next而不是head

## 707. 设计链表

### 适用条件

设计链表适用条件：

1. **动态数据存储**：需要频繁插入、删除数据的场景
2. **顺序访问**：主要是顺序访问数据而非随机访问
3. **内存利用**：需要高效利用内存空间，不要求连续内存

不适用情况：
- 需要频繁随机访问的场景
- 数据量固定且很少变动的场景
- 需要高效索引的场景

### 代码实现

```java
// 707
class MyLinkedList {
    // 只维护并初始化虚拟头节点，而不是head指针，从而无需对head做特殊处理
    private ListNode dummyHead;

    public MyLinkedList() {
        dummyHead = new ListNode(0);
    }

    public int get(int index) {
        ListNode temp = dummyHead;
        for (int i = 0; i < index + 1; i++) {
            if (temp.next != null) {
                temp = temp.next;
            } else {
                return -1;
            }
        }
        return temp.val;
    }

    public void addAtHead(int val) {
        ListNode newNode = new ListNode(val, dummyHead.next);
        dummyHead.next = newNode;
    }

    public void addAtTail(int val) {
        ListNode newNode = new ListNode(val);
        ListNode temp = dummyHead;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
    }

    public void addAtIndex(int index, int val) {
        ListNode newNode = new ListNode(val);
        ListNode temp = dummyHead;
        for (int i = 0; i < index; i++) {
            if (temp.next != null) {
                temp = temp.next;
            } else {
                return;
            }
        }
        newNode.next = temp.next;
        temp.next = newNode;
    }

    public void deleteAtIndex(int index) {
        ListNode temp = dummyHead;
        for (int i = 0; i < index; i++) {
            if (temp.next != null) {
                temp = temp.next;
            } else {
                return;
            }
        }
        if (temp.next != null) {
            temp.next = temp.next.next;
        }
    }
}
```

### 解题思路

设计链表需要实现链表的基本操作，包括：
- 获取指定索引的节点值
- 在链表头部添加节点
- 在链表尾部添加节点
- 在指定位置添加节点
- 删除指定位置的节点

使用虚拟头节点可以简化各种操作，特别是对头节点的处理。每个操作的基本思路如下：

#### get(index)
1. 从虚拟头节点开始遍历
2. 移动index+1次（考虑虚拟头节点的存在）
3. 如果中途遇到null，返回-1
4. 返回找到的节点值

#### addAtHead(val)
1. 创建新节点，将其next指向当前的第一个节点
2. 将虚拟头节点的next指向新节点

#### addAtTail(val)
1. 创建新节点
2. 遍历到链表的最后一个节点
3. 将最后一个节点的next指向新节点

#### addAtIndex(index, val)
1. 创建新节点
2. 遍历到索引位置的前一个节点
3. 如果位置无效（超出链表长度），直接返回
4. 插入节点：将新节点的next指向当前节点的next，然后将当前节点的next指向新节点

#### deleteAtIndex(index)
1. 遍历到索引位置的前一个节点
2. 如果位置无效，直接返回
3. 删除节点：将当前节点的next指向下下个节点

### 算法复杂度

| 操作 | 时间复杂度 | 空间复杂度 |
|------|------------|------------|
| get | O(n) | O(1) |
| addAtHead | O(1) | O(1) |
| addAtTail | O(n) | O(1) |
| addAtIndex | O(n) | O(1) |
| deleteAtIndex | O(n) | O(1) |

- 添加头节点是O(1)时间，因为不需要遍历
- 其他操作需要O(n)时间，因为可能需要遍历链表
- 所有操作只使用常数额外空间，空间复杂度为O(1)

### 易错点与注意事项

1. **索引检查**：所有涉及索引的操作都需要检查索引的有效性
2. **空链表处理**：需要正确处理空链表的情况
3. **虚拟头节点使用**：使用虚拟头节点时，要记住实际节点索引需要+1
4. **链表断开**：操作中要确保不会断开链表
5. **返回值**：get操作在找不到节点时需要返回-1

## 206. 反转链表

### 适用条件

反转链表算法适用条件：

1. **序列翻转**：需要将有序序列完全反向
2. **链表操作**：基本链表操作能力，能够修改节点的next引用
3. **保留所有节点**：要求保留原链表中的所有节点，只改变他们的连接关系

不适用情况：
- 需要部分反转的场景（例如只反转链表的一部分）
- 不允许修改原链表结构的场景

### 代码实现 - 迭代法

```java
// 206 - 迭代
// 时间复杂度 O(n)
// 空间复杂度 O(1)
public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode current = head;
    ListNode next = null;
    while (current != null) {
        next = current.next;
        current.next = prev;
        prev = current;
        current = next;
    }
    return prev;
}
```

### 代码实现 - 递归法

```java
// 206 - 递归
// 时间复杂度 O(n)
// 空间复杂度 O(n)
public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }

    ListNode newHead = reverseList(head.next);
    
    head.next.next = head;
    head.next = null;
    
    return newHead;
}
```

### 解题思路

反转链表的关键是改变每个节点的next指针的指向，使其指向前一个节点而不是后一个节点。有两种主要的实现方法：迭代和递归。

#### 方法一：迭代法

迭代法使用三个指针来实现链表反转：
1. **prev**：指向当前节点的前一个节点
2. **current**：指向当前正在处理的节点
3. **next**：临时保存当前节点的下一个节点

具体步骤：
1. 初始化prev为null，current为head
2. 遍历链表，对于每个节点：
   - 保存当前节点的下一个节点到next
   - 将当前节点的next指向prev
   - 将prev移动到current
   - 将current移动到next
3. 遍历结束后，prev指向新的头节点

**优势**：
- 空间复杂度为O(1)，只使用常数级额外空间
- 逻辑清晰，易于理解和实现
- 适用于所有链表反转场景

#### 方法二：递归法

递归法利用函数调用栈隐式地保存了节点的顺序，实现链表的反转：
1. **基本情况**：如果链表为空或只有一个节点，直接返回head
2. **递归步骤**：
   - 递归调用reverseList(head.next)获取反转后的新头节点
   - 将head.next.next指向head，实现反向连接
   - 将head.next设为null，断开原来的连接
   - 返回新头节点

**优势**：
- 代码简洁优雅
- 思路清晰：将问题分解为反转除头节点外的所有节点，然后处理头节点
- 适合教学和理解递归思想

**缺点**：
- 空间复杂度为O(n)，因为使用了递归调用栈
- 对于非常长的链表，可能导致栈溢出

### 算法复杂度

| 方法 | 时间复杂度 | 空间复杂度 |
|------|------------|------------|
| 迭代法 | O(n) | O(1) |
| 递归法 | O(n) | O(n) |

两种方法的时间复杂度都是O(n)，因为需要访问链表中的每个节点。迭代法的空间复杂度是O(1)，而递归法因为使用了调用栈，空间复杂度为O(n)。

### 易错点与注意事项

1. **保存next指针**：在改变当前节点的next指针之前，必须保存原来的next，否则会丢失链表的后续部分。
2. **处理边界情况**：特别是空链表和单节点链表的情况。
3. **递归思路理解**：递归法的关键是理解每层递归返回的是反转后的链表的新头节点（即原链表的最后一个节点）。
4. **指针断开**：在递归版本中，别忘了设置`head.next = null`，否则会形成环。
5. **返回值**：迭代法返回prev作为新的头节点，而不是current（此时为null）或head（此时为原链表的最后一个节点）。
