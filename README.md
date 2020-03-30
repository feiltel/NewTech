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
 