/*
 * @Author: Ramon
 * @Date: 2025-04-25 16:00:10
 * @LastEditTime: 2025-04-25 16:00:35
 * @FilePath: /DesignPattern/app/src/main/java/org/example/iterator/IProject.java
 * @Description:
 */
package org.example.iterator;

public interface IProject {
    //增加项目
    public void add(String name,int num,int cost);
    //从老板这里看到的就是项目信息
    public String getProjectInfo();
    //获得一个可以被遍历的对象
    public IProjectIterator iterator();
}
