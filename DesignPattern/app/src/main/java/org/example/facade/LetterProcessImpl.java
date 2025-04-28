/*
 * @Author: Ramon
 * @Date: 2025-04-27 11:01:33
 * @LastEditTime: 2025-04-27 11:03:03
 * @FilePath: /DesignPattern/app/src/main/java/org/example/facade/LetterProcessImpl.java
 * @Description:
 */
package org.example.facade;

public class LetterProcessImpl implements ILetterProcess {

    @Override
    public void writeContext(String context) {
        System.out.println("写信：" + context);
    }

    @Override
    public void filEnvelope(String address) {
        System.out.println("写信封：" + address);
    }

    @Override
    public void letterInfoEnvelope() {
        System.out.println("把信放到信封里");
    }

    @Override
    public void sendLetter() {
        System.out.println("发送信");
    }

}
