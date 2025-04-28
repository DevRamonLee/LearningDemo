/*
 * @Author: Ramon
 * @Date: 2025-04-27 11:03:37
 * @LastEditTime: 2025-04-27 11:06:32
 * @FilePath: /DesignPattern/app/src/main/java/org/example/facade/ModernPostOffice.java
 * @Description:
 */
package org.example.facade;

public class ModernPostOffice {
    ILetterProcess letterProcess = new LetterProcessImpl();
    Police police = new Police();

    public void sendLetter(String context, String address) {
        letterProcess.writeContext(context);
        letterProcess.filEnvelope(address);
        police.checkLetter(letterProcess);
        letterProcess.letterInfoEnvelope();
        letterProcess.sendLetter();
    }
}
