package Proxy.Dynamic;

/**
 * @Desc : 普通代理模式的游戏者
 * @Author : Ramon
 * @create 2021/9/5 21:35
 */
public class GamePlayer implements IGamePlayer {
    private String name = "";

    public GamePlayer(String _name) {
            this.name = _name;
    }

    @Override
    public void login(String user, String password) {
        System.out.println("登录名为 " + user + " 的用户" + this.name + " 登录成功！");
    }

    @Override
    public void killBoss() {
        System.out.println(this.name + " 在打怪");
    }

    @Override
    public void upgrade() {
        System.out.println(this.name + " 又升了一级！");
    }
}
