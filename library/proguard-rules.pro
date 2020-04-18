# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/google/home/samstern/android-sdk-linux/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#something by android
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

#####################################
########### 不优化泛型和反射 ##########
#####################################

-keepattributes Signature