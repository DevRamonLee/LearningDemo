package com.betterramon;

import com.betterramon.arithmetic.DataModel;
import com.betterramon.model.Item;


public class Arithmetics {
	 public static void main(String args[]){
		 DataModel dataModel = new DataModel();
		 //dataModel.isThief();

		 // Çó½â (16 + 5) * (16 + 5)
		 int result = 0;
		 Item items[] = dataModel.calculateModulus(2);
		 for(int i = 0; i < items.length; i ++){
			 result += items[i].c * Math.pow(16, items[i].am)*Math.pow(5, items[i].bm);
		 }
		 System.out.println(result);
		 
	 }
}
