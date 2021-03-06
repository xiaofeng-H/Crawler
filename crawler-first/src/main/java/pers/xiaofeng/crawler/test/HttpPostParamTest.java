package pers.xiaofeng.crawler.test;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: pers.xiaofeng.crawler.test.HttpGetTest
 * @description: post请求练习
 * @author: xiaofeng
 * @create: 2021-03-06 14:24
 */
public class HttpPostParamTest {

    public static void main(String[] args) throws Exception {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建HttpGet对象，设置url访问地址
        HttpPost httpPost = new HttpPost("http://www.itcast.cn");

        // 声明List集合，封装表单中的参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // 设置请求地址是：http://yun.itheima.com/search?keys=Java
        params.add(new BasicNameValuePair("keys", "Java"));

        // 创建表单的Entity对象 第一个参数：封装好的表单数据；第二个参数：编码集
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf-8");

        // 设置表单的Entity对象到Post请求中
        httpPost.setEntity(formEntity);

        System.out.println("发情请求的信息：" + httpPost);

        CloseableHttpResponse response = null;
        try {
            // 使用HttpClient发起请求，获取response
            response = httpClient.execute(httpPost);

            // 解析响应
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(content.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭response
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
