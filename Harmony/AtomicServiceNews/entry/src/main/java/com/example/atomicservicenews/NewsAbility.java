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

package com.example.atomicservicenews;

import com.example.atomicservicenews.bean.ClickIndex;
import com.example.atomicservicenews.bean.NewsInfo;
import com.example.atomicservicenews.slice.NewsDetailAbilitySlice;
import com.example.atomicservicenews.slice.NewsListAbilitySlice;
import com.example.atomicservicenews.utils.CommonUtils;
import com.example.atomicservicenews.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ohos.aafwk.ability.*;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.aafwk.content.Operation;
import ohos.bundle.IBundleManager;
import ohos.data.DatabaseHelper;
import ohos.data.preferences.Preferences;
import ohos.utils.zson.ZSONObject;

import java.util.List;

/**
 * News ability
 *
 * @since 2020-12-04
 */
public class NewsAbility extends Ability implements IAbilityContinuation {
    private static final String TAG = "NewsAbility";
    private static final String PERMISSION_DATASYNC = "ohos.permission.DISTRIBUTED_DATASYNC";
    private static final int MY_PERMISSION_REQUEST_CODE = 1;
    /**
     * 2x2 form dimension
     */
    public static final int DEFAULT_DIMENSION_2X2 = 2;
    /**
     * invalid form id
     */
    private static final int INVALID_FORM_ID = -1;
    /**
     * invalid number
     */
    private static final int INVALID_NUMBER = -1;
    /**
     * formName
     */
    private static final String FORM_NAME = "formName";
    /**
     * dimension
     */
    private static final String DIMENSION = "dimension";
    /**
     * database name
     */
    private static final String SHARED_SP_NAME = "form_info_sp.xml";

    private List<NewsInfo> totalNewsDataList;

    @Override
    public void onStart(Intent intent) {
        LogUtils.info(TAG, "onStart");
        super.onStart(intent);
        super.setMainRoute(NewsListAbilitySlice.class.getName());
        addActionRoute("action.detail", NewsDetailAbilitySlice.class.getName());

        if (verifySelfPermission(PERMISSION_DATASYNC) != IBundleManager.PERMISSION_GRANTED) {
            if (canRequestPermission(PERMISSION_DATASYNC)) {
                requestPermissionsFromUser(new String[] {PERMISSION_DATASYNC}, MY_PERMISSION_REQUEST_CODE);
            }
        } else {
            initData();
            startServiceAbility();
        }
    }

    @Override
    public void onRequestPermissionsFromUserResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsFromUserResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == IBundleManager.PERMISSION_GRANTED) {
                    LogUtils.info(TAG, "permission granted");
                    initData();
                    startServiceAbility();
                } else {
                    LogUtils.info(TAG, "permission denied");
                }
        }
    }

    private void initData() {
        LogUtils.info(TAG, "initData");
        Gson gson = new Gson();
        if (totalNewsDataList == null) {
            totalNewsDataList =
                    gson.fromJson(
                            CommonUtils.getStringFromJsonPath(this, "entry/resources/rawfile/news_datas.json"),
                            new TypeToken<List<NewsInfo>>() {
                            }.getType());
        }
    }

    private void startServiceAbility() {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId("")
                .withBundleName(getBundleName())
                .withAbilityName(ServiceAbility.class.getName())
                .build();
        intent.setOperation(operation);
        startAbility(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected ProviderFormInfo onCreateForm(Intent intent) {
        LogUtils.info(TAG, "onCreateForm");
        long formId = INVALID_FORM_ID;
        if (intent.hasParameter(AbilitySlice.PARAM_FORM_IDENTITY_KEY)) {
            formId = intent.getLongParam(AbilitySlice.PARAM_FORM_IDENTITY_KEY, INVALID_FORM_ID);
        }
        String formName = "";
        if (intent.hasParameter(AbilitySlice.PARAM_FORM_NAME_KEY)) {
            formName = intent.getStringParam(AbilitySlice.PARAM_FORM_NAME_KEY);
        }
        int dimension = DEFAULT_DIMENSION_2X2;
        if (intent.hasParameter(AbilitySlice.PARAM_FORM_DIMENSION_KEY)) {
            dimension = intent.getIntParam(AbilitySlice.PARAM_FORM_DIMENSION_KEY, DEFAULT_DIMENSION_2X2);
        }
        LogUtils.info(TAG, "onCreateForm: formId=" + formId + ",formName=" + formName);
        insertForm(formId, formName, dimension);
        int first = CommonUtils.getRandomInt(totalNewsDataList.size(), INVALID_NUMBER);
        int second = CommonUtils.getRandomInt(totalNewsDataList.size(), first);
        NewsInfo firstNews = totalNewsDataList.get(first);
        NewsInfo secondNews = totalNewsDataList.get(second);
        ZSONObject zsonObject = CommonUtils.getJsBindData(first, second, firstNews, secondNews);
        ProviderFormInfo formInfo = new ProviderFormInfo();
        formInfo.setJsBindingData(new FormBindingData(zsonObject));
        return formInfo;
    }

    /**
     * insert form
     *
     * @param formId form id
     * @param formName form name
     * @param dimension dimension
     */
    private void insertForm(long formId, String formName, int dimension) {
        ZSONObject formObj = new ZSONObject();
        formObj.put(FORM_NAME, formName);
        formObj.put(DIMENSION, dimension);
        DatabaseHelper databaseHelper = new DatabaseHelper(this.getApplicationContext());
        Preferences preferences = databaseHelper.getPreferences(SHARED_SP_NAME);
        preferences.putString(Long.toString(formId), ZSONObject.toZSONString(formObj));
        preferences.flush();
    }

    @Override
    protected void onUpdateForm(long formId) {
        LogUtils.info(TAG, "onUpdateForm");
        super.onUpdateForm(formId);
    }

    @Override
    protected void onTriggerFormEvent(long formId, String message) {
        super.onTriggerFormEvent(formId, message);
        LogUtils.info(TAG, "onTriggerFormEvent: formId = " + formId + " message = " + message);
    }

    @Override
    protected void onDeleteForm(long formId) {
        LogUtils.info(TAG, "onDeleteForm: formId=" + formId);
        super.onDeleteForm(formId);
        deleteForm(formId);
    }

    private void deleteForm(long formId) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Preferences preferences = databaseHelper.getPreferences(SHARED_SP_NAME);
        preferences.delete(Long.toString(formId));
    }

    @Override
    public boolean onStartContinuation() {
        LogUtils.info(TAG, "onStartContinuation");
        return true;
    }

    @Override
    public boolean onSaveData(IntentParams intentParams) {
        LogUtils.info(TAG, "onSaveData");
        return true;
    }

    @Override
    public boolean onRestoreData(IntentParams intentParams) {
        LogUtils.info(TAG, "onRestoreData");
        return true;
    }

    @Override
    public void onCompleteContinuation(int i) {
        LogUtils.info(TAG, "onCompleteContinuation");
    }

    @Override
    public void onRemoteTerminated() {
        LogUtils.info(TAG, "onRemoteTerminated");
    }

    @Override
    public void onFailedContinuation(int errorCode) {
        LogUtils.info(TAG, "onFailedContinuation: errorCode = " + errorCode);
    }
}
