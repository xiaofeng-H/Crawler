package pers.xiaofeng.crawler.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @className: pers.xiaofeng.crawler.test.HttpGetTest
 * @description: post请求练习
 * @author: xiaofeng
 * @create: 2021-03-06 14:24
 */
public class HttpPostTest {

    public static void main(String[] args) {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建HttpGet对象，设置url访问地址
        HttpPost httpPost = new HttpPost("http://www.itcast.cn");

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
