-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-repackageclasses ''
-allowaccessmodification
-keepattributes *Annotation*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.contentTextView.BroadcastReceiver
-keep public class * extends android.contentTextView.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.billing.IInAppBillingService
-keep public class * extends android.view.View {
    public <init>(android.contentTextView.Context);
    public <init>(android.contentTextView.Context, android.util.AttributeSet);
    public <init>(android.contentTextView.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.contentTextView.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.contentTextView.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.contentTextView.Context {
    public void *(android.view.View);
    public void *(android.view.MenuItem);
}
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class **.R$*

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }

-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }

-keep class android.util.** { *; }
-keep interface android.util.** { *; }

#保留行号.不然会导致bugly跟踪不了
-keepattributes SourceFile,LineNumberTable
#-renamesourcefileattribute SourceFile

#####################################
######### 主程序不能混淆的代码 #########
#####################################

-dontwarn com.dreamliner.prettygirl.entity.**
-keep class com.dreamliner.prettygirl.entity.** { *; }

#####################################
########### 不优化泛型和反射 ##########
#####################################

-keepattributes Signature

#####################################
######### 第三方库或者jar包 ###########
#####################################

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#fresco
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**

#Eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#GreenDao
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

-dontwarn org.greenrobot.greendao.**
-dontwarn org.greenrobot.greendao.rx.**
-dontwarn rx.schedulers.Schedulers.**

#ViewpagerIndicator
-dontwarn com.viewpagerindicator.**
-keep class com.viewpagerindicator.** { *; }
-keep interface com.viewpagerindicator.** { *; }

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

##---------------End: proguard configuration for Gson  ----------

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * implements java.io.Serializable {*;}

#okhttp/leakcanary/okio
-dontwarn com.squareup.**
-keep class com.squareup.** { *; }
-dontwarn okio.**

-dontwarn com.octo.**
-keep class com.octo.** { *; }

-dontwarn de.**
-keep class de.** { *; }

-dontwarn javax.**
-keep class javax.** { *; }

-dontwarn com.google.**
-keep class com.google.** { *; }

-dontwarn com.squareup.**
-keep class com.squareup.** { *; }

#友盟
-dontwarn com.umeng.**
-keep class com.umeng*.** {*; }

-dontwarn u.aly.**
-keep class u.aly*.** {*; }

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# LeakCanary
-keep class org.eclipse.mat.** { *; }
-keep class com.squareup.leakcanary.** { *; }

-dontwarn javax.**
-keep class javax.*.** {*; }

#segmented
-dontwarn info.hoang8f.android.segmented.**
-keep class info.hoang8f.android.segmented.** { *; }

#md dialog
-dontwarn com.github.afollestad.material-dialogs.**
-keep class com.github.afollestad.material-dialogs.** { *; }
-dontwarn me.zhanghai.android.materialprogressbar.**
-keep class me.zhanghai.android.materialprogressbar.** { *; }

#logger
-dontwarn com.orhanobut.**
-keep class com.orhanobut.** { *; }

#crop-picture
-dontwarn com.soundcloud.android.**
-keep class com.soundcloud.android.** { *; }

#动画库
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *; }

#image-chose
-dontwarn com.kbeanie.imagechooser.**
-keep class com.kbeanie.imagechooser.** { *; }

#haha leakcanary
-dontwarn org.eclipse.mat.**
-keep class org.eclipse.mat.** { *; }


#shortcutbadger 桌面红点
-dontwarn me.leolin.shortcutbadger.**
-keep class me.leolin.shortcutbadger.** { *; }

#swipelayout滑动删除
-dontwarn com.daimajia.swipe.**
-keep class com.daimajia.swipe.** { *; }

#zxing
-dontwarn com.google.zxing.**
-keep class com.google.zxing.** { *; }
#zbar
-dontwarn net.sourceforge.zbar.**
-keep class net.sourceforge.zbar.** { *; }


#腾讯相关/微信/avsdk/qvsdk
-dontwarn com.tencent.**
-keep class com.tencent.** { *; }

-dontwarn com.qq.**
-keep class com.qq.** { *; }

-dontwarn tencent.tls.**
-keep class tencent.tls.** { *; }

-dontwarn qalsdk.**
-keep class qalsdk.** { *; }


#高德地图
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}
-dontwarn com.amap.api.**
-keep class com.amap.apis.utils.core.**{*;}
-dontwarn com.amap.apis.utils.core.**
#3D 地图 V5.0.0之前：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.amap.mapcore.*{*;}
-keep   class com.amap.api.trace.**{*;}
#3D 地图 V5.0.0之后：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.**{*;}
-keep   class com.amap.api.trace.**{*;}
#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
#搜索
-keep   class com.amap.api.services.**{*;}
#2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}
#导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}

#jpush
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}


######### bugly腾讯crash ##########
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}

-dontwarn com.sq580.library.view.HeaderLayout.**
-keep public class com.sq580.library.view.HeaderLayout.**{*;}

#TalkingData
-dontwarn com.tendcloud.tenddata.**
-keep class com.tendcloud.** {*;}
-keep public class com.tendcloud.tenddata.** { public protected *;}
-keepclassmembers class com.tendcloud.tenddata.**{
public void *(***);
}
-keep class com.talkingdata.sdk.TalkingDataSDK {public *;}
-keep class com.apptalkingdata.** {*;}
-keep class dice.** {*; }
-dontwarn dice.**


#加载进度图
-dontwarn com.wang.avi.**
-keep public class com.wang.avi.**{*;}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#Retrofit
-dontwarn retrofit2.**
-dontwarn org.codehaus.mojo.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

-keepattributes EnclosingMethod

-keepclasseswithmembers class * {
    @retrofit2.* <methods>;
}

-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
