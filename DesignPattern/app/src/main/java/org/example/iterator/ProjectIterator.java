/*
 * @Author: Ramon
 * @Date: 2025-04-25 16:03:46
 * @LastEditTime: 2025-04-25 16:04:19
 * @FilePath: /DesignPattern/app/src/main/java/org/example/iterator/ProjectIterator.java
 * @Description:
 */
package org.example.iterator;

import java.util.ArrayList;

public class ProjectIterator implements IProjectIterator {
    //所有的项目都放在这里ArrayList中
    private ArrayList<IProject> projectList = new ArrayList<IProject>();
    private int currentItem = 0;
    //构造函数传入projectList
    public ProjectIterator(ArrayList<IProject> projectList){
            this.projectList = projectList;
    }
    //判断是否还有元素，必须实现
    public boolean hasNext() {
        //定义一个返回值
        boolean b = true;
        if(this.currentItem>=projectList.size()||this.projectList.get(this.currentItem)==null){
            b =false;
        }
        return b;
    }
    //取得下一个值
    public IProject next() {
        return (IProject)this.projectList.get(this.currentItem++);
    }
    //删除一个对象
    public void remove() {
        //暂时没有使用到
    }
}
