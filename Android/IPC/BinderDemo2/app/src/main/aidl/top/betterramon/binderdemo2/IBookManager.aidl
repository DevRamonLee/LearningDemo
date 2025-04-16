// IBookManager.aidl
package top.betterramon.binderdemo2;

import top.betterramon.binderdemo2.Book;
import top.betterramon.binderdemo2.IOnNewBookArrivedListener;
// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
