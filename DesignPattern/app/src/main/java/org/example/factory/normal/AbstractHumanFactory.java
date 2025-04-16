/*
 * @Author: Ramon
 * @Date: 2025-04-14 10:43:02
 * @LastEditTime: 2025-04-14 10:44:07
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/normal/AbstractHumanFactory.java
 * @Description: 
 */
package org.example.factory.normal;

public abstract class AbstractHumanFactory {
    public abstract <T extends Human> T createHuman(Class<T> c);
}
