package sxkeji.net.dailydiary.utils;

import android.content.Context;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.yuntu.carmaster.network.HttpUrls;
import com.yuntu.carmaster.storage.SharedPreferencesUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by qiujie on 2015/11/5.
 */
public class FileUploadClient {
    //////////////////////////////////////////////////////////
    ////////////////////上传接口地址
    private static final String URL = HttpUrls.UPLOAD_FILE;
    //////////////////////////////////////////////////////////
    ////////////////////上传文件类型
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final OkHttpClient client = new OkHttpClient();
    // 每个post参数之间的分隔。随意设定，只要不会和其他的字符串重复即可。
    private static final String BOUNDARY = "----------HV2ymHFg03ehbqgZCaKO6jyH";
//    map.put("clientVersion", "1.0.0");

    public static void uploadFilePost(Context ctx,String moduleName, String filepath) {
        File uploadFile = new File(filepath);
        RequestBody requestBody = new MultipartBuilder().type(MultipartBuilder.FORM)
                .addFormDataPart("moduleName", moduleName)
                .addFormDataPart("token", (String) SharedPreferencesUtil.getParam(ctx, Config.TOKEN, ""))
                .addFormDataPart("clientVersion", "1.0.0")
//                .addPart(
//                        Headers.of("Content-Disposition", "form-data; name=\"clientVersion\""),
//                        RequestBody.create(null, "1.0.0"))
//                .addPart(
//                        Headers.of("Content-Disposition", "form-data; name=\"moduleName\""),
//                        RequestBody.create(null, moduleName))
//                .addPart(
//                        Headers.of("Content-Disposition", "form-data; name=\"token\""),
//                        RequestBody.create(null, (String) SharedPreferencesUtil.getParam(ctx, Config.TOKEN, "")))
//                .addPart(
//                        Headers.of("Content-Disposition", "form-data; name=\"imageFile\""),
//                        RequestBody.create(MEDIA_TYPE_PNG, uploadFile))
                .addFormDataPart("imageFile", "image.png", RequestBody.create(MEDIA_TYPE_PNG, uploadFile))
                .build();

        Request request = new Request.Builder()
                .url(URL)
//                .addHeader("Content-Type", "multipart/form-data;boundary=" + BOUNDARY)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                LogUtils.i(request.toString());

            }

            @Override
            public void onResponse(Response response) throws IOException {
                int code = response.code();
                LogUtils.i(code+"");

            }
        });
    }
}