# BaseLibrary
Android项目基础库 简化Android项目开发  
使用了以下库
```
   //appCompat
    api 'androidx.appcompat:appcompat:1.1.0'
    api 'androidx.constraintlayout:constraintlayout:1.1.3'
    api 'androidx.legacy:legacy-support-v4:1.0.0'
    api 'androidx.recyclerview:recyclerview:1.1.0'
    api 'androidx.legacy:legacy-support-v4:1.0.0'
    api 'com.google.android.material:material:1.1.0'


    //kotlin_java
    api "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
    api "androidx.core:core-ktx:1.1.0"
    //kotlin 组件
    api "org.jetbrains.anko:anko:0.10.8"

    api 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    //OkHttp
    api 'com.squareup.okhttp3:logging-interceptor:4.5.0'
    api 'com.squareup.okhttp3:okhttp:4.5.0'
    //JSON
    api 'com.alibaba:fastjson:1.2.68'
    //压缩
    api 'top.zibin:Luban:1.1.8'
    api 'id.zelory:compressor:2.1.0'
    //状态栏 https://jaeger.itscoder.com/android/2016/03/27/statusbar-util.html
    api 'com.jaeger.statusbarutil:library:1.5.1'
    //butterknife
    api 'com.jakewharton:butterknife:10.2.1'
    //annotationProcessor 'com.jakewharton:butterknife-compiler:10.2.1'
    //glide
    api 'com.github.bumptech.glide:glide:4.11.0'
    //annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'

    //RecyclerView Adapter https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/1-BaseQuickAdapter.md
    api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.2'
    //图片选择 https://github.com/LuckSiege/PictureSelector
    api 'com.github.LuckSiege.PictureSelector:picture_library:v2.5.1'
    //下拉刷新
    api 'com.lcodecorex:tkrefreshlayout:1.0.7'


    //RXJAVA
    api 'io.reactivex.rxjava2:rxjava:2.1.0'
    api 'io.reactivex.rxjava2:rxandroid:2.0.1'
    //retrofit
    api 'com.squareup.retrofit2:retrofit:2.8.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.8.1'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.8.1'
    //banner
    api 'com.youth.banner:banner:2.0.8'

    //Navigation
    api "androidx.navigation:navigation-fragment-ktx:2.1.0"
    api "androidx.navigation:navigation-ui-ktx:2.1.0"
```
# 导入方式
#### 将JitPack存储库添加到您的构建文件中(项目根目录下build.gradle文件)
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
#### 添加依赖项

```
dependencies {
    '''
    //基础库
    implementation 'com.github.feiltel.NewTech:baselibrary:1.5'
    //异常捕获
    implementation 'com.github.feiltel.NewTech:crashlib:1.5'
}
```
# 使用
####  1. Application 中使用

```
public class MyApp extends BaseApplication {  
//DO something  
 
}

```
#### 2. Mvp  中使用
###### 2.1 Activity
```
public class TestActivity extends BaseMvpActivity<TestView,TestPresenter> implements TestView {  
    @Override
    protected ContentPresenter createPresenter() {
        return new ContentPresenter();
    }
    @Override
    protected int getViewId() {
        return layout_ID;
    }
    @Override
    protected void initView() {

    }
    @Override
    protected void initEvent() {

    }
}
```
###### 2.2 View

```
public interface TestView extends BaseMvpView {
  
}
```
###### 2.3 Presenter

```
public class TestPresenter extends BaseMvpPresenter<TestView> {
    public ContentPresenter() {

    }
}
```

#### 3. Activity  中使用
```
public class MainActivity extends BaseActivity {
}
```

# 功能模块
#### 1.Retrofit 网络请求
###### 1.1  Application =》onCreate 初始化
```
   RetrofitManager.getInstance().init(baseUrl, interceptor);
    /**
     * @param baseUrl 基础URL
     * @param interceptor 拦截器 可以为null
     */
```
###### 1.2  使用
```
RetrofitManager.create(ApiService.class).login(userName, password).enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                LoginBean body = response.body();
            
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
               
            }
        });
```