/*
 * @Author: Ramon
 * @Date: 2025-03-29 10:34:47
 * @LastEditTime: 2025-04-28 14:49:43
 * @FilePath: /DesignPattern/app/src/main/java/org/example/App.java
 * @Description:
 */
/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import org.example.adapter.AdapterTest;
import org.example.bridge.BridgeTest;
import org.example.builder.BuilderTest;
import org.example.chain.ChainTest;
import org.example.command.CommandTest;
import org.example.composite.CompositeTest;
import org.example.decorator.Father;
import org.example.expression.ExpressionTest;
import org.example.facade.FacadeTest;
import org.example.factory.abs.Nvwa2;
import org.example.factory.normal.NvWa;
import org.example.iterator.Boss;
import org.example.mediator.MediatorTest;
import org.example.memento.MementoTest;
import org.example.observer.ObserverTest;
import org.example.protocol.MailTest;
import org.example.proxy.ProxyTest;
import org.example.share.GoGame;
import org.example.singleton.SingletonTest;
import org.example.state.StateTest;
import org.example.strategy.ZhaoYun;
import org.example.template.TemplateTest;
import org.example.visitor.VisitorTest;

enum PatternType {
    /* 单例模式 */
    SINGLETON_NORMAL,   // 普通单例
    SINGLETON_REGISTRY, // 登记式单例
    /* 代理模式 */
    PROXY_NORMAL,   // 普通代理
    PROXY_FORCE,    // 强制代理
    PROXY_JDK,      // JDK 动态代理实现
    PROXY_CGLIB,     // CGLIB 动态代理实现
    /* 工厂 */
    FACTORY_NORMAL,  // 工厂模式
    FACTORY_ABSTRACT, // 抽象工厂
    TEMPLATE,   // 模板方法
    BUILDER,  // 建造者模式
    PROTOTYPE, // 原型模式
    MEDIATOR, // 中介者模式
    COMMAND, // 命令模式
    CHAIN, // 责任链模式
    DECORATOR, // 装饰模式
    STRATEGY, // 策略模式
    ADAPTER, // 适配器模式
    ITERATOR, // 迭代器模式
    COMPOSITE, // 组合模式
    OBSERVER, // 观察者模式
    FACADE, // 门面模式
    MEMENTO, // 备忘录模式
    VISITOR, // 访问者模式
    STATE, // 状态模式
    EXPRESSION, // 解释器模式
    SHARE, // 享元模式
    BRIDGE, // 桥接模式
}

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void runTest(PatternType type) {
        switch (type) {
            case SINGLETON_NORMAL:
                try {
                    SingletonTest.testSingleton();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case SINGLETON_REGISTRY:
                SingletonTest.testSingletonRegistry();
                break;
            case PROXY_NORMAL:
                ProxyTest.testProxyNormal();
                break;
            case PROXY_JDK:
                ProxyTest.testProxyJdk();
                break;
            case PROXY_CGLIB:
                ProxyTest.testProxyCglib();
                break;
            case FACTORY_NORMAL:
                NvWa.normalFactoryTest();
                break;
            case FACTORY_ABSTRACT:
                Nvwa2.abstractFactoryTest();
                break;
            case TEMPLATE:
                TemplateTest.templageTest();
                break;
            case BUILDER:
                BuilderTest.builderTest();
                break;
            case PROTOTYPE:
                MailTest.mailTest();
                break;
            case MEDIATOR:
                MediatorTest.mediatorTest();
                break;
            case COMMAND:
                CommandTest.commandTest();
                break;
            case CHAIN:
                ChainTest.chainTest();
                break;
            case DECORATOR:
                Father.decoratorTest();
                break;
            case STRATEGY:
                ZhaoYun.strategyTest();
                break;
            case ADAPTER:
                AdapterTest.adapterTest();
                break;
            case ITERATOR:
                Boss.iteratorTest();
                break;
            case COMPOSITE:
                CompositeTest.compositeTest();
                break;
            case OBSERVER:
                ObserverTest.observerTest();
                break;
            case FACADE:
                FacadeTest.facadeTest();
                break;
            case MEMENTO:
                MementoTest.mementoTest();
                break;
            case VISITOR:
                VisitorTest.visitorTest();
                break;
            case STATE:
                StateTest.stateTest();
                break;
            case EXPRESSION:
                ExpressionTest.expressionTest();
                break;
            case SHARE:
                GoGame.shareTest();
                break;
            case BRIDGE:
                BridgeTest.bridgeTest();
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        // runTest(PatternType.SINGLETON_NORMAL);
        // runTest(PatternType.SINGLETON_REGISTRY);
        // runTest(PatternType.PROXY_NORMAL);
        // runTest(PatternType.PROXY_JDK);
        // runTest(PatternType.PROXY_CGLIB);
        // runTest((PatternType.FACTORY_NORMAL));
        // runTest(PatternType.FACTORY_ABSTRACT);
        // runTest(PatternType.TEMPLATE);
        // runTest(PatternType.BUILDER);
        // runTest(PatternType.PROTOTYPE);
        // runTest(PatternType.MEDIATOR);
        // runTest(PatternType.COMMAND);
        // runTest(PatternType.CHAIN);
        // runTest(PatternType.DECORATOR);
        // runTest((PatternType.STRATEGY));
        // runTest(PatternType.ADAPTER);
        // runTest(PatternType.ITERATOR);
        // runTest(PatternType.COMPOSITE);
        // runTest(PatternType.OBSERVER);
        // runTest(PatternType.FACADE);
        // runTest(PatternType.MEMENTO);
        // runTest(PatternType.VISITOR);
        // runTest(PatternType.STATE);
        // runTest(PatternType.EXPRESSION);
        // runTest(PatternType.SHARE);
        runTest(PatternType.BRIDGE);
    }
}
