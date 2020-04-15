### 网络监听框架
1. 需要添加权限  
   ` <uses-permission
   android:name="android.permission.ACCESS_NETWORK_STATE" />
   <uses-permission
   android:name="android.permission.CHANGE_NETWORK_STATE" />
   <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"
   /> <uses-permission
   android:name="android.permission.CHANGE_WIFI_STATE" />`
2. 在Application onCreate里面初始化  
   `    NetWorkManager.getDefault().init(this);`
   
  
3. 具体使用   
   在Activity里面需要注册 
    
  `  NetWorkManager.getDefault().registerObserver(this);   `
    
     @NetWork(netType = NetType.AUTO)
    public void netWork(NetType netType) {
        MToast.show(this,netType.name());
    }

