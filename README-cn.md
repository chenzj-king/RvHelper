# RvHelper
-----

这个项目的灵感来自于秋哥的[liaohuqiu-uptr](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh "liaohuqiu-uptr")和[SuperRecyclerView](https://github.com/Malinskiy/SuperRecyclerView "SuperRecyclerView").基于他们我做了一些封装和实现.

这个项目可以很友好的让你在项目中使用Recyclerview.简化你的Adapter和非常简单的定制你的刷新头部和加载更多.同时还可以定制数据为空的时候的界面.

## 例子
![](http://i.imgur.com/irhBX5s.gif)

## Usage

## JitPack.io

我把项目放到了[jitpack.io](https://jitpack.io).如果要使用请按照如下对项目进行配置.

    repositories {
    	//...
    	maven { url "https://jitpack.io" }
	}

	dependencies {
		//...
    	compile 'com.github.chenzj-king:RvHelper:1.2'
	}

##使用方式

## 在xml中下面sample代码一样配置相关属性:  

	    <com.dreamliner.rvhelper.OptimumRecyclerview
	        android:id="@+id/list"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        android:transcriptMode="normal"
	        app:layout_empty="@layout/layout_emptyview"
	        app:layout_moreProgress="@layout/layout_progress"
	        app:mainLayoutId="@layout/layout_recyclerview_verticalscroll"
	        app:recyclerClipToPadding="false"
	        app:scrollbarStyle="insideInset"
	        />

## 在代码中这样设置

    mOptimumRecyclerview.setLayoutManager(new LinearLayoutManager(this));//设置你的layoutmanager
    mOptimumRecyclerview.setAdapter(mAdapter);							 //设置你的Adapter
    mOptimumRecyclerview.setRefreshListener(this);						 //设置下拉刷新监听
    mOptimumRecyclerview.setupMoreListener(this, 1);					 //设置加载更多的监听.并且设置预加载数据为1.意思就是说.如果你列表中有100个item.当你滚动到99个item的时候.就会触发加载更多的回调.

## 下拉刷新和加载更多的实现sample ##

    @Override
    public void onRefresh(PtrFrameLayout ptrFrameLayout) {
        mAdapter.clear();
		//... 从db/Api获取数据
        mOptimumRecyclerview.setOnMoreListener(this);//重新设置加载更多的监听.因为加载更多的监听会在到了底部的时候remove掉.如果重新刷新过了.需要重新设置一次.
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
		//假设从Api/Db查询到最大size=120.当列表显示的item＜120.那么就会出发loadmore逻辑.
        if (overallItemsCount < 120) {
			// ....从db/Api获取数据
        } else {
			//隐藏加载更多页面和移除回调
            mOptimumRecyclerview.hideMoreProgress();
            mOptimumRecyclerview.removeMoreListener();
        }
    }

## Thanks ##

1.  [butterknife](https://github.com/JakeWharton/butterknife) - 优化findviewById等逻辑
2.  [android-Ultra-Pull-To-Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh) - 下拉刷新
3.  [SuperRecyclerView](https://github.com/Malinskiy/SuperRecyclerView) - 加载更多&数据为空界面
4.  [AVLoadingIndicatorView](https://github.com/81813780/AVLoadingIndicatorView) - 加载更多的loadingview  

## License ##

    Copyright (c) 2016 chenzj-king

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    Come on, don't tell me you read that.

## About me ##

- **QQ:** 364972027
- **Weibo:** [http://weibo.com/u/1829515680](http://weibo.com/u/1829515680)
- **Email:** admin@chenzhongjin.cn
- **Github:** [https://github.com/chenzj-king](https://github.com/chenzj-king)
- **Blog:** [http://www.chenzhongjin.cn](http://www.chenzhongjin.cn)


