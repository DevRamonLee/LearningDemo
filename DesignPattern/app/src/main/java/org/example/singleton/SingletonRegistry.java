/*
 * @Author: Ramon
 * @Date: 2021-12-11 11:45:03
 * @LastEditTime: 2025-03-29 11:08:56
 * @FilePath: /DesignPattern/app/src/main/java/org/example/singleton/SingletonRegistry.java
 * @Description: 登记式单例，用于管理获取多个单例实例
 */
package org.example.singleton;

import java.util.concurrent.ConcurrentHashMap;

public class SingletonRegistry {
    private static final ConcurrentHashMap<Class<?>, Object> map = new ConcurrentHashMap<>();

    // 私有构造，防止外部实例化
    private SingletonRegistry() {}

    // 泛型单例获取方法
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz) {
        return (T) map.computeIfAbsent(clazz, SingletonRegistry::createInstance);
    }

    // 反射创建实例（必须有无参构造）
    private static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("无法创建单例实例: " + clazz.getName(), e);
        }
    }
}

