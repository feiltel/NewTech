Android 脚手架
===========

1.封装
----


##### 高内聚,低耦合  高内聚：封装细节 便于修改内部代码，提高可维护性低耦合：简化外部调用 便于调用者使用 便于扩展和协作

2.完成的工程
-------
##### 2.1  MVP 自动生成
##### 2.2  网络请求库加入 OkHttp
##### 2.3 ButterKnife 加入
##### 2.4 图片压缩库加入 Luban compressor
##### 2.5 RecyclerViewAdapter库加入 BaseRecyclerViewAdapterHelper
##### 2.6 加入状态栏工具 statusbarutil
##### 2.7 图片加载框架 glide
##### 2.8 图片选择框架 PictureSelector

4.类模板
-----
 ```  
 <!--单例模式模板-->
 
 package ${PACKAGE_NAME};



#parse("File Header.java")
public class ${NAME} {
  private volatile static ${NAME} instance;

    private ${NAME}() {
    }

    public static ${NAME} getInstance() {
        if (instance == null) {
            synchronized (${NAME}.class) {
                if (instance == null) {
                    instance = new ${NAME}();
                }
            }
        }
        return instance;
    }

}

<!--MvpActivity-->

package ${PACKAGE_NAME};

import android.app.Activity;
import android.os.Bundle;

import com.nut2014.newtech.base.BaseMvpActivity;
import com.nut2014.newtech.R;

#parse("File Header.java")
public class ${NAME}Activity extends BaseMvpActivity<${NAME}View,${NAME}Presenter> implements ${NAME}View {

    @Override
    protected int getViewId() {
        return R.layout.activity_${LAYOUT};
    }
    
    @Override
    public void initView() {
      
    }

    @Override
    public void initEvent() {
        
    }
     @Override
    protected ${NAME}Presenter createPresenter() {
        return new ${NAME}Presenter();
    }


}

<!--MVPPresenter-->

package ${PACKAGE_NAME};



import com.nut2014.newtech.base.BaseMvpPresenter;

#parse("File Header.java")
public class ${NAME}Presenter extends BaseMvpPresenter<${NAME}View> {
   public ${NAME}Presenter() {

    }

}

<!--MVP_VIEW-->
package ${PACKAGE_NAME};


import com.nut2014.newtech.base.BaseMvpView;

#parse("File Header.java")
public interface ${NAME}View extends BaseMvpView {
  

}
 <!--BaseAdapter-->
 package ${PACKAGE_NAME};
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


import java.util.List;
#parse("File Header.java")
public class ${NAME}Adapter extends BaseQuickAdapter<${BeanName},BaseViewHolder> {
    public ${NAME}Adapter(int layoutId,List<${BeanName}> list) {
        super(layoutId, list);
    }
    @Override
    protected void convert( BaseViewHolder helper,  ${BeanName} item) {
      
    }
}
 
 
 ```