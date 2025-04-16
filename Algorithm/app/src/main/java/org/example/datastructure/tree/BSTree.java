package org.example.datastructure.tree;

import java.util.Stack;

/**
 * 二叉查找树
 * @author meng.li
 *
 */
public class BSTree<T extends Comparable<T>> {
	
	private BSTNode<T> mRoot;// 根节点

	private class BSTNode<T extends Comparable<T>> {
		T key; // 键值
		BSTNode<T> left; // 左孩子
		BSTNode<T> right;// 右孩子
		BSTNode<T> parent;//父节点
		boolean isFirst;// 后序非递归使用,方法 1
		boolean isVisited;// 后序非递归，方法 2
		
		public BSTNode(T key, BSTNode<T> parent, BSTNode<T> left, BSTNode<T> right) {
			this.key = key;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}
	}
	
	// 前序遍历，递归法
	private void preOrder(BSTNode<T> tree) {
		if (tree != null) {
			System.out.print(tree.key + " ");
			preOrder(tree.left);
			preOrder(tree.right);
		}
	}
	
	public void preOrder() {
		preOrder(mRoot);
		preOrder2(mRoot);
	}
	
	// 非递归前序遍历
	private void preOrder2(BSTNode<T> tree) {
		Stack<BSTNode<T>> stack = new Stack<BSTNode<T>>();
		BSTNode<T> p = tree;
		while(p != null || !stack.empty()) {
			while (p != null) {
				System.out.print(p.key + " ");// 直接打印
				stack.push(p);// 入栈
				p = p.left;// 遍历左孩子
			}
			if (!stack.empty()) {
				p = stack.pop();
				p = p.right;// 遍历右子树
			}
		}
	}
	
	// 中序遍历，递归法
	private void inOrder(BSTNode<T> tree) {
		if (tree != null) {
			inOrder(tree.left);
			System.out.print(tree.key + " ");
			inOrder(tree.right);
		}
	}
	
	// 中序遍历，非递归法
	private void inOrder2(BSTNode<T> tree) {
		Stack<BSTNode<T>> stack = new Stack<BSTNode<T>>();
		BSTNode<T> p = tree;
		while(p != null || !stack.empty()) {
			while (p != null) {
				stack.push(p);// 入栈
				p = p.left;// 遍历左孩子
			}
			if (!stack.empty()) {
				p = stack.pop();
				System.out.print(p.key + " ");
				p = p.right;// 遍历右子树
			}
		}
	}
	
	public void inOrder() {
		inOrder(mRoot);
		inOrder2(mRoot);
	}
	
	// 后序遍历
	private void postOrder(BSTNode<T> tree) {
		if (tree != null) {
			postOrder(tree.left);
			postOrder(tree.right);
			System.out.print(tree.key + " ");
		}
	}
	
	/*
	 * 后序遍历：非递归思路一。对于任一结点P，将其入栈，然后沿其左子树一直往下搜索，直到搜索到没有左孩子的结点，
	 * 此时该结点出现在栈顶，但是此时不能将其出栈并访问，因此其右孩子还未被访问。
	 * 所以接下来按照相同的规则对其右子树进行相同的处理，当访问完其右孩子时，
	 * 该结点又出现在栈顶，此时可以将其出栈并访问。这样就保证了正确的访问顺序。
	 * 可以看出，在这个过程中，每个结点都两次出现在栈顶，只有在第二次出现在栈顶时，
	 * 才能访问它。因此需要多设置一个变量标识该结点是否是第一次出现在栈顶。
	 */
	private void postOrder2(BSTNode<T> tree) {
		Stack<BSTNode<T>> stack = new Stack<BSTNode<T>>();
		BSTNode<T> p = tree;
		BSTNode<T> temp;

		while(p != null || !stack.empty()) {
			while (p != null) {
				p.isFirst = true;
				stack.push(p);// 入栈
				p = p.left;// 遍历左孩子
			}
			if (!stack.empty()) {
				temp = stack.pop();
				if (temp.isFirst == true) { // 表示是第一次出现在栈顶
					temp.isFirst = false;
					stack.push(temp); // 把isFirst 置为 false，再把它放进去
					p = temp.right;// 开始遍历它的右子树
				} else {// 第二次出现在栈顶
					System.out.print(temp.key + " ");
				}
			}
		}
	}
	
	/* 后序遍历，非递归法，思路二：要保证根结点在左孩子和右孩子访问之后才能访问，
	 * 因此对于任一结点P，先将其入栈。如果P不存在左孩子和右孩子，则可以直接访问它；
	 * 或者P存在左孩子或者右孩子，但是其左孩子和右孩子都已被访问过了，
	 * 则同样可以直接访问该结点。若非上述两种情况，则将P的右孩子和左孩子依次入栈，
	 * 这样就保证了每次取栈顶元素的时候，左孩子在右孩子前面被访问，
	 * 左孩子和右孩子都在根结点前面被访问。
	 * 
	 */
	private void postOrder3(BSTNode<T> tree) {
		Stack<BSTNode<T>> stack = new Stack<BSTNode<T>>();
		BSTNode<T> cur; // 当前节点
		BSTNode<T> pre = null; // 前一次访问的节点
		stack.push(tree); // 首先将节点入栈
		while(!stack.empty()) {
			cur = stack.peek();
			if ((cur.left == null && cur.right == null) ||
					(pre != null && (pre == cur.left || pre == cur.right))) {
				System.out.print(cur.key + " ");// 如果当前节点没有孩子节点或者孩子节点已经被访问了
				stack.pop();
				pre = cur;
			} else {
				if (cur.right != null)
					stack.push(cur.right);
				if (cur.left != null)
					stack.push(cur.left);
			}
		}
	}
	
	public void postOrder() {
		postOrder(mRoot);
		postOrder2(mRoot);
		postOrder3(mRoot);
	}
	
	/**
	 * 递归实现，查找二叉树 x 键值 为 key 的节点
	 */
	private BSTNode<T> search(BSTNode<T> x, T key) {
		if (x == null) {
			return x;
		}
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			return search(x.left, key);
		else if (cmp > 0)
			return search(x.right, key);
		else 
			return x;
	}
	
	public BSTNode<T> search(T key) {
		return search(mRoot, key);
	}
	
	/**
	 * 非递归查找二叉树 x 键值为 key 的节点
	 */
	private BSTNode<T> iterativeSearch(BSTNode<T> x, T key) {
		while (x != null) {
			int cmp = key.compareTo(x.key);
			if (cmp < 0)
				x = x.left;
			else if (cmp > 0)
				x = x.right;
			else 
				return x;
		}
		return x;
	}
	
	public BSTNode<T> iterativeSearch(T key) {
		return iterativeSearch(mRoot, key);
	}
	
	/**
	 * 查找最大节点：返回 tree 为根节点的二叉树的最大节点
	 */
	private BSTNode<T> maximum(BSTNode<T> tree) {
		if (tree == null) {
			return null;
		}
		while (tree.right != null) {
			tree = tree.right;
		}
		return tree;
	}
	public T maximum() {
		BSTNode<T> b = maximum(mRoot);
		if (b != null)
			return b.key;
		return null;
	}
	
	/**
	 * 查找最小值：返回 tree 为根节点的最小值
	 */
	private BSTNode<T> minmum(BSTNode<T> tree) {
		if (tree == null)
			return null;
		while (tree.left != null) {
			tree = tree.left;
		}
		return tree;
	}
	
	public T minmum() {
		BSTNode<T> p = minmum(mRoot);
		if (p != null)
			return p.key;
		return null;
	}
	
	/**
	 * 查找节点 x 的前驱节点。即，查找“二叉树中数据值小于该节点并且值最大的节点”
	 */
	public BSTNode<T> predecessor(BSTNode<T> x) {
		// 如果 x 存在左孩子，则 x 的前驱节点是以其左孩子为根的子树的最大节点
		if (x.left != null)
			return maximum(x.left);
		
		// 如果 x 没有左孩子，则 x 有两种可能
		// 1. x 是「一个右孩子」，则 x 的前驱节点为 「它的父节点」
		// 2. x 是「一个左孩子」，则查找 「x 的最低的父节点，并且该父节点要具有右孩子」
		BSTNode<T> y = x.parent;
		while ((y != null) && (x == y.left)) {
			x = y;
			y = y.parent;
		}
		return y;
	}
	
	/**
	 * 查找 x 的后继节点，即，查找“二叉树中数值大于该节点并且值最小的节点”
	 */
	public BSTNode<T> successor(BSTNode<T> x) {
		// 如果 x 存在右孩子，则「x 的后继节点为 『以其右孩子为根的子树的最小节点』」
		if (x.right != null) {
			return minmum(x.right);
		}
		// 如果 x 没有右孩子。则 x 有两种可能
		// 1. x 是 「一个左孩子」，则「x 的后继节点为 『它的父节点』」
		// 2. x 是 「一个右孩子」，则查找 「x 的最低的父节点，并且该父节点要具有左孩子」
		BSTNode<T> y = x.parent;
		while ((y != null) && (x == y.right)) {
			x = y;
			y = y.parent;
		}
		return y;
	}
	
	/**
	 * 插入新节点
	 */
	private void insert(BSTree<T> bst, BSTNode<T> z) {
		int cmp;
		BSTNode<T> y = null;
		BSTNode<T> x = bst.mRoot;
		
		// 查找 z 的插入位置
		while (x != null) {
			y = x;
			cmp = z.key.compareTo(x.key);
			if (cmp < 0)
				x = x.left;
			else
				x = x.right;
		}
		
		z.parent = y;
		if (y == null) {
			bst.mRoot = z;// z 是第一个节点
		} else {
			cmp = z.key.compareTo(y.key); // 判断是插入左孩子还是插入右孩子
			if (cmp < 0) 
				y.left = z;
			else
				y.right = z;
		}
	}
	
	// 这里实现的是允许插入相同键值的节点
	public void insert(T key) {
		BSTNode<T> z = new BSTNode<T>(key, null, null, null);
		// 如果创建节点失败，则返回
		if (z != null)
			insert(this, z);
	}
}
