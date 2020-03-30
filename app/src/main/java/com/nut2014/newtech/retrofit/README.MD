implementation 'com.squareup.retrofit2:retrofit:2.6.1'

implementation 'com.squareup.retrofit2:converter-gson:2.6.2'


Retrofit 的使用步骤
这里我们直接用官方给的例子来做示范：

第一步：定义接口。
public interface IGitHubService {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
}
复制代码
第二步：创建装换对象。
public class Repo {
    ……//其他属性
    private String name;
     public String getName() {
        return name;
    }
}
复制代码
第三步：构造 Retrofit 对象。
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
               .addConverterFactory(GsonConverterFactory.create())
                .build();

IGitHubService service = retrofit.create(IGitHubService.class);

Call<List<Repo>> listRepos = service.listRepos("aserbao");

复制代码
第四步：执行请求。enqueue 为异步，execute 为同步。和 OkHttp 一模一样。
listRepos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                updateUi(response.body());//返回处理
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });
    
    ##GET POST  上传文件带名称    