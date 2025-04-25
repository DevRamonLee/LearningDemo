/*
 * @Author: Ramon
 * @Date: 2025-04-25 16:05:45
 * @LastEditTime: 2025-04-25 16:20:20
 * @FilePath: /DesignPattern/app/src/main/java/org/example/iterator/Boss.java
 * @Description:
 */
package org.example.iterator;

public class Boss {
    public static void iteratorTest() {
        //定义一个List，存放所有的项目对象
        IProject project = new Project();
        //增加星球大战项目
        project.add("星球大战项目ddddd",10,100000);
        //增加扭转时空项目
        project.add("扭转时空项目",100,10000000);
        //增加超人改造项目
        project.add("超人改造项目",10000,1000000000);
        for(int i=4;i<10;i++){
            project.add("第"+i+"个项目",i*5,i*1000000);
        }
        //遍历一下ArrayList，把所有的数据都取出
        IProjectIterator projectIterator = project.iterator();
        while(projectIterator.hasNext()){
            IProject p = (IProject)projectIterator.next();
            System.out.println(p.getProjectInfo());
        }
    }
}
