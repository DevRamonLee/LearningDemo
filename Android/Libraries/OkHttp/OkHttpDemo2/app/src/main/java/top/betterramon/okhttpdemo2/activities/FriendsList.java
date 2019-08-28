/**
  * Copyright 2019 bejson.com 
  */
package top.betterramon.okhttpdemo2.activities;
import java.util.List;

import top.betterramon.okhttpdemo2.bean.BaseDataBean;

/**
 * Auto-generated: 2019-08-07 17:24:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class FriendsList extends BaseDataBean {

    private List<Friend> data;
    private int errorCode;
    private String errorMsg;
    public void setData(List<Friend> data) {
         this.data = data;
     }
     public List<Friend> getData() {
         return data;
     }

    public void setErrorCode(int errorCode) {
         this.errorCode = errorCode;
     }
     public int getErrorCode() {
         return errorCode;
     }

    public void setErrorMsg(String errorMsg) {
         this.errorMsg = errorMsg;
     }
     public String getErrorMsg() {
         return errorMsg;
     }

}