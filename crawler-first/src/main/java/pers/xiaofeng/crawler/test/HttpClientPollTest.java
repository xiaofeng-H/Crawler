package pers.xiaofeng.crawler.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @className: pers.xiaofeng.crawler.test.HttpClientPollTest
 * @description: 连接池练习
 * @author: xiaofeng
 * @create: 2021-03-06 15:47
 */
public class HttpClientPollTest {

    public static void main(String[] args) {
        // 创建连接池管理器
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

        // 设置最大连接数
        cm.setMaxTotal(100);

        // 设置每个主机的最大连接数
        cm.setDefaultMaxPerRoute(10);

        // 使用连接池管理器发起请求
        doGet(cm);

    }

    private static void doGet(PoolingHttpClientConnectionManager cm) {
        // 不是每次创建新的HttpClient，而是从连接池中获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        HttpGet httpGet = new HttpGet("http://www.itcast.cn");

        CloseableHttpResponse response = null;
        try {
            // 使用HttpClient发起请求，获取response
            response = httpClient.execute(httpGet);

            // 解析响应
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(content.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                // 关闭response
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 不能关闭HttpClient，由连接池管理HttpClient
                //httpClient.close();
            }
        }
    }
}
