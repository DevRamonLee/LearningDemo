package com.betterramon;

import java.util.ArrayList;

import com.betterramon.arithmetic.*;
import com.betterramon.model.Item;
import com.betterramon.model.Merchandise;


public class Algorithm {
	 public static void main(String args[]){
		 /*//问题1：谁是小偷
		 WhoIsThief whoIsThief = new WhoIsThief();
		 whoIsThief.isThief();
		 
		 Binomial dataModel = new Binomial();
		 //问题2：求解 (16 + 5) * (16 + 5)
		 int result = 0;
		 Item items[] = dataModel.calculateModulus(2);
		 for(int i = 0; i < items.length; i ++){
			 result += items[i].c * Math.pow(16, items[i].am)*Math.pow(5, items[i].bm);
		 }
		 System.out.println(result);
		 
		 //问题3：0-1 背包问题，贪心算法
		 //重量分别是 wi=[35、30、60、50、40、10、25]
		 //价值分别是 pi=[10、40、30、50、35、40、30]
		 ArrayList<Merchandise> objs = new ArrayList<Merchandise>();
		 objs.add(new Merchandise(35, 10, 0));
		 objs.add(new Merchandise(30, 40, 0));
		 objs.add(new Merchandise(60, 30, 0));
		 objs.add(new Merchandise(50, 50, 0));
		 objs.add(new Merchandise(40, 35, 0));
		 objs.add(new Merchandise(10, 40, 0));
		 objs.add(new Merchandise(25, 30, 0));
		 KnapsackProblem  knapsackProblem = new KnapsackProblem(objs, 150);
		 //knapsackProblem.greedyAlgo(knapsackProblem, 0);
		 //knapsackProblem.greedyAlgo(knapsackProblem, 1);
		 knapsackProblem.greedyAlgo(knapsackProblem, 2);*/
		 
		 /*//问题4： 字符串全排列问题，分治法
		 Permutation  permutation= new Permutation();
		 int [] chList = {1,2,3};
		 permutation.permutation(chList, 0, chList.length);
		 
		 //问题5： 二分法查找，分治法
		 BinarySearch binarySearch = new BinarySearch();
		 int[] arr = {1,6,23,56,80,100,300,500,1000,4322};
		 int index = binarySearch.binarySearch(arr, 500, 0, arr.length);
		 System.out.println("递归实现 index = " + index);
		 int index2 = binarySearch.binarySearch(arr, 4322);
		 System.out.println("非递归实现 index2 = " + index2);
		 
		 //问题 6： 迭代法求一个数的平方根
		 CalculateRoot calculateRoot = new CalculateRoot();
		 System.out.println(calculateRoot.clRoot(9.0, 0.0001));
		 
		 //问题7：动态规划法求最长公共子序列
		 DpLcs dpLcs = new DpLcs();
		 String str1 = "abcdefgh";
		 String str2 = "abdfh";
		 int maxLength = dpLcs.dPLcs(str1, str2, new int[str1.length()+1][str2.length()+1]);
		 System.out.println("Max length is " + maxLength);*/
		 
		 //问题8： 百钱买鸡问题
		 /*new BuyChickens().buyChickens();
		 new BuyChickens().chickensAndRabbits();*/
		 
		 //问题9： 走楼梯问题
		 System.out.println("走法: " + GoStairs.goStairs2(4));
	 }
}
