/*
 * @Author: Ramon
 * @Date: 2025-04-27 10:59:52
 * @LastEditTime: 2025-04-27 11:01:18
 * @FilePath: /DesignPattern/app/src/main/java/org/example/facade/ILetterProcess.java
 * @Description:
 */
package org.example.facade;

public interface ILetterProcess {
    void writeContext(String context);
    void filEnvelope(String address);
    void letterInfoEnvelope();
    void sendLetter();
}
