# RvHelper
-----
inspired and modified from  [liaohuqiu-uptr](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh "liaohuqiu-uptr") and [SuperRecyclerView](https://github.com/Malinskiy/SuperRecyclerView "SuperRecyclerView"), with some feature enhancement.  

This project make nice way to init our Recylcerview.  Simplify to new Adapter and easy to do custome onRefresh and loadmore. Also it can cutome the empty datas view. 

## Demo
![](http://i.imgur.com/irhBX5s.gif)

## Usage

## JitPack.io

I strongly recommend [jitpack.io](https://jitpack.io)

    repositories {
    	//...
    	maven { url "https://jitpack.io" }
	}

	dependencies {
		//...
    	compile 'com.github.chenzj-king:RvHelper:1.0'
	}

##Usage

## Use directly OptimumRecyclerview:  

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

## Use it in Java

    mOptimumRecyclerview.setLayoutManager(new LinearLayoutManager(this));//setting layoutmanager
    mOptimumRecyclerview.setAdapter(mAdapter);							 //set your Adapter
    mOptimumRecyclerview.setRefreshListener(this);						 //set onRefresh listener
    mOptimumRecyclerview.setupMoreListener(this, 1);					 //set moreListener and max size = 1.it meant if the itemcount is 100.when you scroll lastvisiable item is 100-1 = 99

## Init Callback ##

    @Override
    public void onRefresh(PtrFrameLayout ptrFrameLayout) {
        mAdapter.clear();
		// Fetch more from Api or DB
        mOptimumRecyclerview.setOnMoreListener(this);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
		//Item 120 just for test
        if (overallItemsCount < 120) {
			// Fetch more from Api or DB
        } else {
            mOptimumRecyclerview.hideMoreProgress();
            mOptimumRecyclerview.removeMoreListener();
        }
    }

## Thanks ##

1.  [butterknife](https://github.com/JakeWharton/butterknife) for bindview more easy.  
2.  [android-Ultra-Pull-To-Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh) for pull to refresh.  
3.  [SuperRecyclerView](https://github.com/Malinskiy/SuperRecyclerView) for loadmore & emptyView.  
4.  [AVLoadingIndicatorView](https://github.com/81813780/AVLoadingIndicatorView) for loadmore custom view.  

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


