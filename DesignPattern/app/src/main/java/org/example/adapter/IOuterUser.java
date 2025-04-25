/*
 * @Author: Ramon
 * @Date: 2025-04-25 12:03:18
 * @LastEditTime: 2025-04-25 12:04:03
 * @FilePath: /DesignPattern/app/src/main/java/org/example/adapter/IOuterUser.java
 * @Description:
 */
package org.example.adapter;

import java.util.Map;

public interface IOuterUser {
    //基本信息，比如名称、性别、手机号码等
    public Map getUserBaseInfo();
    //工作区域信息
    public Map getUserOfficeInfo();
    //用户的家庭信息
    public Map getUserHomeInfo();
}
