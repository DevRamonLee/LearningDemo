import java.util.Stack;

/**
 * @Desc : 从尾到头打印链表
 * @Author : Ramon
 * @create 2021/2/21 19:52
 */
public class PrintListInReversedOrder_06 {
    public static void main(String args[]) {
        SingleLinkedList<Integer> list = new SingleLinkedList();
        int[] values = {23, 4, 52, 13, 55};
        for(int i = 0; i < values.length; i++) {
            list.add(values[i]);
        }
//        list.printListReversingly();
        list.printListRecursively(list.header.next);
    }
}


// 定义单链表的数据结构
class Entry<T> {
    T value;
    Entry<T> next;

    public Entry(T value) {
        this.value = value;
    }
}

class SingleLinkedList<E> {
    // 创建头节点
    public Entry<E> header = new Entry<>(null);
    // 单链表长度
    public int size = 0;
    /**
     * 添加元素
     * @param e
     */
    public void add(E e) {
        Entry temp = header;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = new Entry<E>(e);
        size++;
    }

    /**
     * 逆向打印单链表，通过栈来实现
     */
    public void printListReversingly() {
        Stack<E> stack = new Stack<>();
        Entry<E> node = header.next;
        while(node!= null) {
            stack.push(node.value);
            node = node.next;
        }
        while (!stack.isEmpty()) {
            System.out.println(stack.pop() + " ");
        }
    }

    /**
     * 使用递归的方式打印
     * @param node
     */
    public void printListRecursively(Entry<E> node) {
        if (node != null) {
            if (node.next != null) {
                printListRecursively(node.next);
            }
            System.out.println(node.value);
        }
    }
}