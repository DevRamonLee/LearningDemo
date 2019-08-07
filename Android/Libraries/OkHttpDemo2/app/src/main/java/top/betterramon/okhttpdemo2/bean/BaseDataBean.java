package top.betterramon.okhttpdemo2.bean;

import java.io.Serializable;

import top.betterramon.okhttpdemo2.net.Error;

/**
 * Created by Ramon Lee on 2019/8/7.
 * 所有的数据类都需要继承这个类
 */
public class BaseDataBean extends Error implements Serializable {
    public String message;
}
