/*
 * @Author: Ramon
 * @Date: 2025-04-14 10:45:52
 * @LastEditTime: 2025-04-14 10:49:37
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/normal/HumanFactory.java
 * @Description: 
 */
package org.example.factory.normal;

public class HumanFactory extends AbstractHumanFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Human> T createHuman(Class<T> c) {
        Human human = null;
        try {
            human = (Human)Class.forName(c.getName()).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("人种生成错误");
        }
        return (T)human;
    }
}
