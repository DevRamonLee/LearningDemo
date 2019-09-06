// IBinderPool.aidl
package top.betterramon.binderpooldemo;

// Binder pool
interface IBinderPool {
    IBinder queryBinder(int binderCode);
}
