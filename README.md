[![](https://img.shields.io/badge/%20maven%20central%20-1.1.2-green.svg)](https://bintray.com/chenzj-king/maven/RvHelper)

## [EngLish](https://github.com/chenzj-king/RvHelper/blob/master/README-EN.md)

# RvHelper

这个项目的灵感来自于秋哥的[liaohuqiu-uptr](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh "liaohuqiu-uptr")和[cube-Sdk](https://github.com/liaohuqiu/cube-sdk).RvHelper的V1.x是基于uptr+SuperRecycleView的方式来进行实现的.在实际项目中发现了loadmore的样式不太优雅.loadmore还是作为adapter的一个itemType比较友好.而这方面秋哥的cube-sdk之前就有实现loadmore-recyclerview的分支.我就提取出来.再加上自己实际项目中遇到的一些可能用到的场景.把这些都进行了定制.就这样.RvHelper V2.x诞生了.

> RvHelper有如下的几个大功能点

1. 随意定制loading页面(一般来讲第一次进入界面都需要一个loading页面)
2. 随意定制空白页面(如网络错误/暂无数据/搜索无结果等等)
3. 随意定制头部(具体可以看uptr的介绍)
4. 随意定制底部(具体实现可以看cube-sdk中loadmore的实现)
5. 更加简单的Adapter & ViewHoler.只需要关心createViewHolder和bindView的逻辑.ItemClick也更加优雅简单.

## 例子

![](http://oevl814u4.bkt.clouddn.com/customIml.gif)

## Jecenter

我把项目放到了[RvHelper](https://bintray.com/chenzj-king/maven/RvHelper).如果要使用请按照如下对项目进行配置.

	dependencies {
		//...
    	compile 'compile 'com.dreamliner.lib:RvHelper:1.1.2'
	}

##使用方式

### 自定义Loading ###

> 新建**CustomLoading类**且继承**LoadingLayout**.需要实现**LoadingUIHandler**

	public class DlLoadingLayout extends LoadingLayout {
		
	    public DlLoadingLayout(Context context) {
	        this(context, null);
	    }
	
	    public DlLoadingLayout(Context context, AttributeSet attrs) {
	        this(context, attrs, 0);
	    }
	
	    public DlLoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	    }
	
	
	    @Override
	    protected void onFinishInflate() {
	        super.onFinishInflate();
	       	//findviewById等初始化操作
	    }
	
	    @Override
	    public void onShowLoading() {
			//显示加载中的实现
	    }
	
	    @Override
	    public void onHideLoading() {
			//隐藏加载中的实现
	    }
	}

> 新建对应的loading.xml

	<?xml version="1.0" encoding="utf-8"?>
	<com.dreamliner.rvhelper.sample.view.DlLoadingLayout
	    xmlns:android="http://schemas.android.com/apk/res/android"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent">
			
		<!-- 这里进行自定义的界面定义.只需要配合CustomLoadingLayout的逻辑来进行实现即可-->

	</com.dreamliner.rvhelper.sample.view.DlLoadingLayout>

> 配置到OptimumRecyclerview中

    <com.dreamliner.rvhelper.OptimumRecyclerview
        app:rvhelp_layout_loading="@layout/layout_dl_loading"
        />

### 自定义空白页面 ###

> 新建**CustomEmptyLayout**并且继承**EmptyLayout**.需要实现**EmptyUIHandler**接口

	public class DlEmptyLayout extends EmptyLayout {
	
	    private OnClickListener mOnClickListener;
	
	    public DlEmptyLayout(Context context) {
	        this(context, null);
	    }
	
	    public DlEmptyLayout(Context context, AttributeSet attrs) {
	        this(context, attrs, 0);
	    }
	
	    public DlEmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	    }
	
	    @Override
	    protected void onFinishInflate() {
	        super.onFinishInflate();
			//findviewById等初始化操作
	    }
	
	    @Override
	    public void setEmptyType(int type) {
	        switch (type) {
				//根据不同的自定义tpye来进行切换.如网络错误/暂无数据.搜索结果为空等等
	        }	
	    }
	
	    @Override
	    public void setOnClickListener(OnClickListener onClickListener) {
			//预留onclick.方便空白页面的各种交互
	        mOnClickListener = onClickListener;
	    }
	}

> 新建custom_empty.xml

	<?xml version="1.0" encoding="utf-8"?>
	<com.dreamliner.rvhelper.sample.view.DlEmptyLayout
	    xmlns:android="http://schemas.android.com/apk/res/android"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:gravity="center"
	    android:orientation="vertical">
			
		<!-- 这里进行自定义的界面定义.只需要配合CustomEmptyLayout的逻辑来进行实现即可-->
		
	</com.dreamliner.rvhelper.sample.view.DlEmptyLayout>

> 配置到OptimumRecyclerview中

    <com.dreamliner.rvhelper.OptimumRecyclerview
        app:rvhelp_layout_empty="@layout/layout_dl_empty"
        />

### 自定义下拉刷新头部 ###

> 新建**CustomHeaderView**并且实现**PtrUIHandler**接口

	public class DlHeaderView extends FrameLayout implements PtrUIHandler {
	
	    public DlHeaderView(Context context) {
	        this(context, null);
	    }
	
	    public DlHeaderView(Context context, AttributeSet attrs) {
	        this(context, attrs, 0);
	    }
	
	    public DlHeaderView(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	        setupViews();
	    }
	
	
	    private void setupViews() {
			//初始化UI
	    }
	
	    @Override
	    public void onUIReset(PtrFrameLayout frame) {
			//实现恢复默认的UI回调接口
	    }
	
	    @Override
	    public void onUIRefreshPrepare(PtrFrameLayout frame) {
			//实现准备下拉刷新的回调接口
	    }
	
	    @Override
	    public void onUIRefreshBegin(PtrFrameLayout frame) {
			//实现开始下拉刷新的回调接口
	    }
	
	    @Override
	    public void onUIRefreshComplete(PtrFrameLayout frame) {
			//实现刷新完毕的回调接口
	    }
	
	    @Override
	    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
	    	//实现下拉刷新过程中的下拉程度的回调接口
	    }
	}

> 在Act/Fra中声明

	//暂不提供attr的方式来进行配置.因为毕竟也就一行代码的配置就可以定制了.
	//只需要把下拉刷新的回调实现和自定义的HeaderView配置一下即可
	mOptimumRecyclerview.setRefreshListener(this, new DlHeaderView(this));


### 自定义加载更多界面 ###

> 新建**CustomLoadmoreView**.需要实现**LoadMoreUIHandler**接口

	public class DlLoadmoreView extends RelativeLayout implements LoadMoreUIHandler {
	
	    public DlLoadmoreView(Context context) {
	        this(context, null);
	    }
	
	    public DlLoadmoreView(Context context, AttributeSet attrs) {
	        this(context, attrs, 0);
	    }
	
	    public DlLoadmoreView(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	        setupViews();
	    }
	
	    private void setupViews() {
			//初始化界面
	    }
	
	    @Override
	    public void onLoading(LoadMoreContainer container) {
			//加载中的实现
	    }
	
	    @Override
	    public void onLoadFinish(LoadMoreContainer container, boolean empty, boolean hasMore) {
	        if (!hasMore) {
	            if (empty) {
	             	//没有更多.界面无数据的实现  
	            } else {
	             	//没有更多.界面有数据.即全部数据加载完毕的实现
	            }
	        } else {
	            //还有数据未加载完的实现
	        }
	    }
	
	    @Override
	    public void onWaitToLoadMore(LoadMoreContainer container) {
	    	//等待加载更多的UI实现
	    }
	
	    @Override
	    public void onLoadError(LoadMoreContainer container, int errorCode, String errorMessage) {
	        //因为网络错误等原因的加载失败的UI实现
	    }
	}

> 在Act/Fra中声明

		//如果到了列表的最后一个.则触发loadmore回调
        mOptimumRecyclerview.setNumberBeforeMoreIsCalled(1);
		//设置loadmore的回调实现和设置自定义的加载更多界面
        mOptimumRecyclerview.setLoadMoreHandler(this, new DlLoadmoreView(this));

### CustomAdapter & CustomViewHolder的使用 ###

> 因为BaseAdapter已经做了很多List<Data>等封装.所以实现起来很优雅

	public class TypeAdapter extends BaseAdapter<String, TypeAdapter.ViewHolder> {
	
		//重写onClick/onLongClick的构造方法
	    public TypeAdapter(ItemClickListener itemClickListener) {
	        super(itemClickListener);
	    }
	
	    @Override
	    public ViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
			//新建对应的ViewHolder
	        return new ViewHolder(getView(R.layout.item_type, parent), getItemClickListener());
	    }
	
	    @SuppressLint("SetTextI18n")
	    @Override
	    protected void bindView(ViewHolder holder, int position) {
			//执行bindView的操作
	    }
	
		//直接重写带onClick/onLongClick的构造方法
	    static class ViewHolder extends BaseViewHolder {
	
	        @BindView(R.id.type_tv)
	        TextView mTypeTv;
	
	        ViewHolder(View itemView, ItemClickListener itemClickListener) {
	            super(itemView, itemClickListener);
	            ButterKnife.bind(this, itemView);
	            mTypeTv.setOnClickListener(this);
	        }
	    }
	}

### 总结 ###

> RvHelper的大概功能已经介绍完毕.主要是为了把复用的东西都封装起来.解放生产力.写更加优雅的代码.当然.技术能力有限.可能很多地方实现不够优雅.希望各位共同交流.多提Issue和Pull Requests.

## Thanks ##

1.  [butterknife](https://github.com/JakeWharton/butterknife) - 优化findviewById等逻辑
2.  [android-Ultra-Pull-To-Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh) - 下拉刷新
3.  [cube-sdk](https://github.com/liaohuqiu/cube-sdk) - 加载更多

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


