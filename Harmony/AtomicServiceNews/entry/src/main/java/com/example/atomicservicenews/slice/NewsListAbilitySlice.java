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

import com.example.atomicservicenews.NewsAbility;
import com.example.atomicservicenews.ResourceTable;
import com.example.atomicservicenews.bean.NewsInfo;
import com.example.atomicservicenews.bean.NewsType;
import com.example.atomicservicenews.provider.NewsListProvider;
import com.example.atomicservicenews.provider.NewsTypeProvider;
import com.example.atomicservicenews.utils.CommonUtils;
import com.example.atomicservicenews.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;
import ohos.agp.utils.Color;
import ohos.utils.zson.ZSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * News list slice
 *
 * @since 2020-12-04
 */
public class NewsListAbilitySlice extends AbilitySlice {
    private static final String TAG = "NewsListAbilitySlice";
    private static final float FOCUS_TEXT_SIZE = 1.2f;
    private static final float UNFOCUSED_TEXT_SIZE = 1.0f;
    private Text selectText;

    private ListContainer newsListContainer;
    private ListContainer selectorListContainer;
    private List<NewsInfo> totalNewsDataList;
    private List<NewsInfo> newsDataList;

    private NewsTypeProvider newsTypeProvider;
    private NewsListProvider newsListProvider;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_news_list_layout);
        initView();
        initData();
        initListener();
        selectorListContainer.setItemProvider(newsTypeProvider);
        newsListContainer.setItemProvider(newsListProvider);
        newsTypeProvider.notifyDataChanged();
        newsListProvider.notifyDataChanged();
        String param = intent.getStringParam("params");
        if (param != null) {
            LogUtils.info(TAG, "onStart: param = " + param);
            ZSONObject data = ZSONObject.stringToZSON(param);
            String index = data.getString("index");
            LogUtils.info(TAG, "onStart: index = " + index);
            goNewsDetail(Integer.parseInt(index));
        }
    }

    private void initView() {
        selectorListContainer = (ListContainer) findComponentById(ResourceTable.Id_selector_list);
        newsListContainer = (ListContainer) findComponentById(ResourceTable.Id_news_container);
    }

    private void initData() {
        Gson gson = new Gson();
        List<NewsType> newsTypeList =
                gson.fromJson(
                        CommonUtils.getStringFromJsonPath(this, "entry/resources/rawfile/news_type_datas.json"),
                        new TypeToken<List<NewsType>>() { }.getType());
        totalNewsDataList =
                gson.fromJson(
                        CommonUtils.getStringFromJsonPath(this, "entry/resources/rawfile/news_datas.json"),
                        new TypeToken<List<NewsInfo>>() { }.getType());
        newsDataList = new ArrayList<>();
        newsDataList.addAll(totalNewsDataList);
        newsTypeProvider = new NewsTypeProvider(newsTypeList, this);
        newsListProvider = new NewsListProvider(newsDataList, this);
    }

    /**
     * init listener of news type and news detail
     */
    private void initListener() {
        selectorListContainer.setItemClickedListener(
            (listContainer, component, position, id) -> {
                setCategorizationFocus(false);
                selectText = (Text) component.findComponentById(ResourceTable.Id_news_type_text);
                setCategorizationFocus(true);
                newsDataList.clear();
                for (NewsInfo mTotalNewsData : totalNewsDataList) {
                    if (selectText.getText().equals(mTotalNewsData.getType()) || position == 0) {
                        newsDataList.add(mTotalNewsData);
                    }
                }
                updateListView();
            });
        newsListContainer.setItemClickedListener(
            (listContainer, component, position, id) -> {
                goNewsDetail(position);
            });
    }

    private void goNewsDetail(int position) {
        Intent intent = new Intent();
        Operation operation =
                new Intent.OperationBuilder()
                        .withBundleName(getBundleName())
                        .withAbilityName(NewsAbility.class.getName())
                        .withAction("action.detail")
                        .build();
        intent.setOperation(operation);
        intent.setParam(NewsDetailAbilitySlice.INTENT_TITLE, newsDataList.get(position).getTitle());
        intent.setParam(NewsDetailAbilitySlice.INTENT_READ, newsDataList.get(position).getReads());
        intent.setParam(NewsDetailAbilitySlice.INTENT_LIKE, newsDataList.get(position).getLikes());
        intent.setParam(NewsDetailAbilitySlice.INTENT_CONTENT, newsDataList.get(position).getContent());
        intent.setParam(NewsDetailAbilitySlice.INTENT_IMAGE, newsDataList.get(position).getImgUrl());
        startAbility(intent);
    }

    private void setCategorizationFocus(boolean isFocus) {
        if (selectText == null) {
            return;
        }
        if (isFocus) {
            selectText.setTextColor(
                    new Color(CommonUtils.getColor(NewsListAbilitySlice.this, ResourceTable.Color_news_type_text_on)));
            selectText.setScaleX(FOCUS_TEXT_SIZE);
            selectText.setScaleY(FOCUS_TEXT_SIZE);
        } else {
            selectText.setTextColor(
                    new Color(CommonUtils.getColor(NewsListAbilitySlice.this, ResourceTable.Color_news_type_text_off)));
            selectText.setScaleX(UNFOCUSED_TEXT_SIZE);
            selectText.setScaleY(UNFOCUSED_TEXT_SIZE);
        }
    }

    private void updateListView() {
        newsListProvider.notifyDataChanged();
        newsListContainer.invalidate();
        newsListContainer.scrollToCenter(0);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
