# RvHelper
-----

[![](https://jitpack.io/v/chenzj-king/RvHelper.svg)](https://jitpack.io/#chenzj-king/RvHelper)

-----

## [EngLish](https://github.com/chenzj-king/RvHelper/blob/master/README-EN.md)

## 前言 ##

这个项目的灵感来自于秋哥的[liaohuqiu-uptr](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh "liaohuqiu-uptr")和[cube-Sdk](https://github.com/liaohuqiu/cube-sdk).RvHelper的V1.x是基于uptr+SuperRecycleView的方式来进行实现的.在实际项目中发现了loadmore的样式不太优雅.loadmore还是作为adapter的一个itemType比较友好.而这方面秋哥的cube-sdk之前就有实现loadmore-recyclerview的分支.我就提取出来.再加上自己实际项目中遇到的一些可能用到的场景.把这些都进行了定制.就这样.RvHelper V2.x诞生了.

> RvHelper有如下的几个大功能点

1. 随意定制loading页面(一般来讲第一次进入界面都需要一个loading页面)
2. 随意定制空白页面(如网络错误/暂无数据/搜索无结果等等)
3. 随意定制头部(具体可以看uptr的介绍)
4. 随意定制底部(具体实现可以看cube-sdk中loadmore的实现)
5. 更加简单的Adapter & ViewHoler.只需要关心createViewHolder和bindView的逻辑.ItemClick也更加优雅简单.

## 例子

**自定义加载框**  
![](http://oevl814u4.bkt.clouddn.com/customLoading.gif)

**自定义空白页面**  
![](http://oevl814u4.bkt.clouddn.com/customEmpty.gif)

**自定义头部**  
![](http://oevl814u4.bkt.clouddn.com/customHeader.gif)

**自定义加载更多**  
![](http://oevl814u4.bkt.clouddn.com/customFooter.gif)

**默认实现所有**  
![](http://oevl814u4.bkt.clouddn.com/defaultIml.gif)

**自定义实现所有**  
![](http://oevl814u4.bkt.clouddn.com/customIml.gif)

## Usage

## JitPack.io

我把项目放到了[jitpack.io](https://jitpack.io).如果要使用请按照如下对项目进行配置.

    repositories {
    	//...
    	maven { url "https://jitpack.io" }
	}

	dependencies {
		//...
    	compile 'com.github.chenzj-king:RvHelper:2.0.0'
	}

# TODO #

##使用方式



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


