# Java 基础笔记
#### 1.super 方法
##### 在构造器里第一行代码总是调用super()，不论写或者不写，即默认调用了父类的构造器，构造方法会自动向上追溯
```
class childClass extends fatherClass{
    public childClass(){
        super(); //默认会调用 不管写还是不写
        //TODO
    }
}
```
#### 2.封装
##### 高内聚,低耦合  高内聚：封装细节 便于修改内部代码，提高可维护性
低耦合：简化外部调用 便于调用者使用 便于扩展和协作
 
#### 3.类模板
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
 
 ```