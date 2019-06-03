package com.li.ramon.list;
/**
 * 栈的应用
 * @author limeng
 *
 */
public class ArrayStackTest {

	public static void main(String[] args) {
		ArrayStackTest myArrayStackTest = new ArrayStackTest();
		// 将 6  转化为 2 进制
		System.out.println(myArrayStackTest.conversion(10000, 2));
		System.out.println("LinkStack test: " + myArrayStackTest.conversionByLinkStack(10, 2));
		// 行编辑
		System.out.println(myArrayStackTest.lineEdit("abcd#dsa@fafda#fdafa#k9"));
		// 括号匹配
		System.out.println(myArrayStackTest.isMatch("[[([]([])()[])]]"));
	}

	/**栈的应用 1: 进制转换
	 * 将 10 进制正整数转换为 n 进制
	 */
	private String conversion(int num, int n) {
		ArrayStack<Integer> myStack = new ArrayStack<Integer>(Integer.class);
		Integer result = num;
		while(true) {
			// 将余数入栈
			myStack.push(result % n);
			result = result / n;
			if (result == 0) {
				break;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		// 按出栈顺序倒序排列
		while ((result = myStack.pop()) != null) {
			sb.append(result + " ");
		}
		return sb.toString();
	}
	
	/**栈的应用 1: 进制转换,测试链式栈
	 * 将 10 进制正整数转换为 n 进制
	 */
	private String conversionByLinkStack(int num, int n) {
		LinkStack<Integer> myStack = new LinkStack<Integer>();
		Integer result = num;
		while(true) {
			// 将余数入栈
			myStack.push(result % n);
			result = result / n;
			if (result == 0) {
				break;
			}
		}
		System.out.println("Print stack : " + myStack.toString());
		StringBuilder sb = new StringBuilder();
		// 按出栈顺序倒序排列
		while ((result = myStack.pop()) != null) {
			sb.append(result + " ");
		}
		return sb.toString();
	}
	
	/**
	 * 栈的应用2：
	 * 行编辑：输入行中字符 # 表示退格，@ 表示之前的输入全部无效
	 */
	private String lineEdit(String input) {
		ArrayStack<Character> myStack = new ArrayStack<Character>(Character.class);
		char[] arr = input.toCharArray();
		for (char c : arr) {
			if (c == '#') {
				myStack.pop();
			} else if(c == '@') {
				myStack.clear();
			} else {
				myStack.push(c);
			}
		}
		StringBuilder sb = new StringBuilder();
		Character temp = null;
		while ((temp = myStack.pop()) != null) {
			sb.append(temp);
		}
		// 反转字符串
		sb.reverse();
		return sb.toString();
	}
	
	/**
	 * 栈的应用3：
	 * 检验符号是否匹配 '['和']'成对出现时字符串合法
	 * 例如："[][]()"，"[[([]([])()[])]]"是合法的
	 * "([(])","[())"是不合法的
	 */
	private boolean isMatch(String str) {
		ArrayStack<Character> myStack = new ArrayStack<Character>(Character.class);
		char[] arr = str.toCharArray();
		for (char c : arr) {
			Character temp = myStack.pop();
			// 如果栈为空，则把这个字符放入
			if(temp == null) {
				myStack.push(c);
			} else if(temp == '[' && c == ']') {
				// 匹配时 c 不入栈
			} else if(temp == '(' && c == ')') {
				
			} else {// 不匹配时把 temp 放入，c 没有匹配的也入栈
				myStack.push(temp);
				myStack.push(c);
			}
		}
		return myStack.isEmpty();
	}
}
