package top.betterramon.binderpooldemo;

import android.os.RemoteException;

/**
 * Created by meng.li on 2019/3/12.
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
