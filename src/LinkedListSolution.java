class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

// 707
class MyLinkedList {
    // 只维护并初始化虚拟头节点，而不是head指针，从而无需对head做特殊处理
    private ListNode dummyHead;

    public MyLinkedList() {
        dummyHead =  new ListNode(0);
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
        ListNode newNode = new ListNode(val,dummyHead.next);
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

public class LinkedListSolution {
    // 只维护并初始化虚拟头节点，而不是head指针，从而无需对head做特殊处理
    private ListNode dummyHead;

    public LinkedListSolution() {
        dummyHead =  new ListNode(0);
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
        ListNode newNode = new ListNode(val,dummyHead.next);
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

    // 206 - 迭代
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

    // 206 - 递归

    public ListNode reverseList(ListNode head) {


        if (head == null || head.next == null) {
            return head;
        }


        ListNode newHead = reverseList(head.next);

        head.next.next = head;
        head.next = null;

        return newHead;
    }


    public static void main(String[] args) {
        LinkedListSolution myLinkedList = new LinkedListSolution();


    }

}
