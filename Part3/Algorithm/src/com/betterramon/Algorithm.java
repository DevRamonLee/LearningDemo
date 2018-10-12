package com.betterramon;

import java.util.ArrayList;

import com.betterramon.arithmetic.Binomial;
import com.betterramon.arithmetic.KnapsackProblem;
import com.betterramon.arithmetic.WhoIsThief;
import com.betterramon.model.Item;
import com.betterramon.model.Merchandise;


public class Algorithm {
	 public static void main(String args[]){
		 //问题1：谁是小偷
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
		 knapsackProblem.greedyAlgo(knapsackProblem, 2);
		 
	 }
}
