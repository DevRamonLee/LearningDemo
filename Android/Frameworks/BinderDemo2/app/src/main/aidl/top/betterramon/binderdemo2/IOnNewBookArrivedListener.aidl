// IOnNewBookArrivedListener.aidl
package top.betterramon.binderdemo2;

import top.betterramon.binderdemo2.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
