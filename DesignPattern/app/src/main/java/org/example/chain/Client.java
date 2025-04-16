package org.example.chain;

import java.util.ArrayList;
import java.util.Random;

/**
 * @Desc : 责任链模式- 例：中国古代对妇女制定了“三从四德”的道德规范，“三从”是指“未嫁从父、及嫁从夫、夫死从子”。举例来说，如果一位女性要出去逛街，在她出嫁前必须征得父亲的同意，
 * 出嫁之后必须征得丈夫的许可，丈夫死了必须问问儿子是否允许，要是没有儿子，就得请示小叔子、侄子等。在父系社会中，妇女只占从属地位。作为父亲、丈夫、儿子，只有两种选择：
 * 要不承担起责任、允许她或不允许她逛街；要不让她请示下一个人。
 * @Author : Ramon
 * @create 2021/9/3 11:25
 */
public class Client {
    public static void main(String[] args) {
        // 随机挑选几个女性
        Random rand = new Random();
        ArrayList<IWomen> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            arrayList.add(new Women(rand.nextInt(4), "我要去逛街"));
        }
        // 定义三个请示对象
        Handler father = new Father();
        Handler husband = new Husband();
        Handler son = new Son();

        // 设置请示顺序
        father.setNext(husband);
        husband.setNext(son);
        for (IWomen women: arrayList) {
            father.handleMessage(women);
        }
    }
}