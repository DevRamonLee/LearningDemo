/*
 * Copyright (c) 2021 Huawei Device Co., Ltd.
 * Licensed under the Apache License,Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.atomicservicenews.slice;


import com.example.atomicservicenews.ResourceTable;
import com.example.atomicservicenews.distribute.api.SelectDeviceResultListener;
import com.example.atomicservicenews.distribute.factory.DeviceSelector;
import com.example.atomicservicenews.utils.CommonUtils;
import com.example.atomicservicenews.utils.LogUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.ability.continuation.ExtraParams;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.agp.components.DependentLayout;
import ohos.agp.components.Image;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.distributedschedule.interwork.DeviceInfo;

/**
 * News detail slice
 *
 * @since 2020-12-04
 */
public class NewsDetailAbilitySlice extends AbilitySlice implements IAbilityContinuation {
    private static final String TAG = "NewsDetailAbilitySlice";
    public static final String INTENT_TITLE = "intent_title";
    public static final String INTENT_READ = "intent_read";
    public static final String INTENT_LIKE = "intent_like";
    public static final String INTENT_CONTENT = "intent_content";
    public static final String INTENT_IMAGE = "intent_image";
    private DependentLayout parentLayout;
    private TextField commentFocus;
    private Image iconShared;
    private DeviceSelector deviceSelector;
    private String reads;
    private String likes;
    private String title;
    private String content;
    private String image;

    @Override
    public void onStart(Intent intent) {
        LogUtils.info(TAG, "onStart");
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_news_detail_layout);
        if (reads == null) {
            reads = intent.getStringParam(INTENT_READ);
        }
        if (likes == null) {
            likes = intent.getStringParam(INTENT_LIKE);
        }
        if (title == null) {
            title = intent.getStringParam(INTENT_TITLE);
        }
        if (content == null) {
            content = intent.getStringParam(INTENT_CONTENT);
        }
        if (image == null) {
            image = intent.getStringParam(INTENT_IMAGE);
        }
        initView();
        initListener();
        initDistributeComponent();
    }

    private void initView() {
        parentLayout = (DependentLayout) findComponentById(ResourceTable.Id_parent_layout);
        commentFocus = (TextField) findComponentById(ResourceTable.Id_text_file);
        iconShared = (Image) findComponentById(ResourceTable.Id_button4);
        Text newsRead = (Text) findComponentById(ResourceTable.Id_read_num);
        Text newsLike = (Text) findComponentById(ResourceTable.Id_like_num);
        Text newsTitle = (Text) findComponentById(ResourceTable.Id_title_text);
        Text newsContent = (Text) findComponentById(ResourceTable.Id_title_content);
        Image newsImage = (Image) findComponentById(ResourceTable.Id_image_content);
        newsRead.setText("reads: " + reads);
        newsLike.setText("likes: " + likes);
        newsTitle.setText("Original title: " + title);
        newsContent.setText(content);
        newsImage.setPixelMap(CommonUtils.getPixelMapFromPath(this, image));
    }

    private void initListener() {
        parentLayout.setTouchEventListener(
                (component, touchEvent) -> {
                    if (commentFocus.hasFocus()) {
                        commentFocus.clearFocus();
                    }
                    return true;
                });
        iconShared.setClickedListener(component -> deviceSelector.showDistributeDevices(
                new String[]{ExtraParams.DEVICETYPE_SMART_PAD, ExtraParams.DEVICETYPE_SMART_PHONE},
                null));
    }

    private void initDistributeComponent() {
        deviceSelector = new DeviceSelector();
        deviceSelector.setup(getAbility());
        deviceSelector.setSelectDeviceResultListener(new SelectDeviceResultListener() {
            @Override
            public void onSuccess(DeviceInfo info) {
                LogUtils.info(TAG, "onSuccess: deviceId = " + info.getDeviceId());
                // 8.跨端迁移到另一个设备上
                continueAbility(info.getDeviceId());
            }

            @Override
            public void onFail(DeviceInfo info) {
                LogUtils.error(TAG,"onFail is called,info is "+info.getDeviceState());
            }
        });
    }

    @Override
    public boolean onStartContinuation() {
        // 9.开始迁移
        LogUtils.info(TAG, "onStartContinuation");
        return true;
    }

    @Override
    public boolean onSaveData(IntentParams intentParams) {
        // 10.迁移前传递数据
        LogUtils.info(TAG, "onSaveData");
        intentParams.setParam(NewsDetailAbilitySlice.INTENT_TITLE, title);
        intentParams.setParam(NewsDetailAbilitySlice.INTENT_READ, reads);
        intentParams.setParam(NewsDetailAbilitySlice.INTENT_LIKE, likes);
        intentParams.setParam(NewsDetailAbilitySlice.INTENT_CONTENT, content);
        intentParams.setParam(NewsDetailAbilitySlice.INTENT_IMAGE, image);
        return true;
    }

    @Override
    public boolean onRestoreData(IntentParams intentParams) {
        // 11.远端恢复数据
        LogUtils.info(TAG, "onRestoreData");
        reads = (String) intentParams.getParam(INTENT_READ);
        likes = (String) intentParams.getParam(INTENT_LIKE);
        title = (String) intentParams.getParam(INTENT_TITLE);
        content = (String) intentParams.getParam(INTENT_CONTENT);
        image = (String) intentParams.getParam(INTENT_IMAGE);
        return true;
    }

    @Override
    public void onCompleteContinuation(int i) {
        LogUtils.info(TAG, "onCompleteContinuation");
        // 12.迁移完成
        deviceSelector.destroy();
        terminateAbility();
    }

    @Override
    public void onFailedContinuation(int errorCode) {
        LogUtils.info(TAG, "onFailedContinuation：errorCode = " + errorCode);
        // 迁移失败
        deviceSelector.destroy();
        terminateAbility();
    }
}
