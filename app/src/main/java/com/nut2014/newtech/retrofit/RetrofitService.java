package com.nut2014.newtech.retrofit;



import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 请求参数接口
 * Created by tangfei
 */

public interface RetrofitService {
    @GET(Constant.UrlOrigin.loginUrl)
    Observable<ResponseBean> login(@Query("userName") String userName, @Query("password") String password);



    /**********************************************/
    /**
     * 获取主页数据 分页
     *
     * @param page  页码
     * @param limit 每页个数
     * @return Observable
     */
    @GET(Constant.UrlOrigin.get_main_list_data)
    Observable<ResponseBean> getMainListData(@Query("page") Integer page, @Query("limit") Integer limit);

    @GET(Constant.UrlOrigin.delete_item)
    Observable<ResponseBean> delete(@Query("id") Integer id);

    /**
     * 保存日记
     *
     * @param mainListItem 保存的数据集合
     * @return Observable
     */
    @POST(Constant.UrlOrigin.save_item_data)
    Observable<ResponseBean> saveItemData(@Body ResponseBean mainListItem);

    /**
     * 根据经纬度获取地址信息
     *
     * @param url 请求地址
     * @return Observable
     */
    @GET
    Observable<ResponseBean> getAddress(@Url String url);

    /**
     * 上传图片
     *
     * @param file 上传的文件
     * @return Observable
     */
    @Multipart
    @POST(Constant.UrlOrigin.upload_pic)
    Observable<ResponseBean> uploadFile(@Part MultipartBody.Part file);


    /**
     * 测试用
     *
     * @param file 上传的文件
     * @return Observable
     */
    @Multipart
    @POST("uploads")
    Observable<String> uploadFiles(@Part MultipartBody.Part file);

}
