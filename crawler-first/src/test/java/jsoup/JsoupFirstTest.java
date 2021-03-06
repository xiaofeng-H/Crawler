package jsoup;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.net.URL;
import java.util.Set;

/**
 * @className: jsoup.JsoupFirstTest
 * @description: Jsoup测试
 * @author: xiaofeng
 * @create: 2021-03-06 16:53
 */
public class JsoupFirstTest {

    /*
     * PS：虽然使用Jsoup可以替代HttpClient直接发起请求解析数据，但是往往不会这样用，因为实际的开发过程中，需要使用到多线程，
     * 连接池，代理等等方式，而Jsoup对这些的支持并不是很好，所以我们一般把Jsoup仅仅作为Html解析工具使用。
     * */

    @Test
    public void testUrl() throws Exception {
        // 解析url地址 第一个参数：访问的url；第二个参数：访问的超时时间
        Document document = Jsoup.parse(new URL("http://www.itcast.cn"), 3000);

        // 使用标签选择器，获取title标签中的内容
        String title = document.getElementsByTag("title").first().text();

        // 打印
        System.out.println(title);
    }

    @Test
    public void testString() throws Exception {

        /*// 使用工具类读取文件，获取字符串
        String content = FileUtils.readFileToString(new File("文件路径"), "UTF-8");
        // 解析字符串
        Document doc = Jsoup.parse(content);*/

        // 解析url地址 第一个参数：访问的url；第二个参数：访问的超时时间
        Document document = Jsoup.parse(new URL("http://www.itcast.cn"), 3000);
        String content = document.toString();

        // 解析字符串
        Document doc = Jsoup.parse(content);

        String title = doc.getElementsByTag("title").first().text();

        // 打印
        System.out.println(title);
    }

    @Test
    public void testFile() throws Exception {

        // 解析文件
        Document doc = Jsoup.parse(new File("文件地址"), "UTF-8");

        String title = doc.getElementsByTag("title").first().text();

        // 打印
        System.out.println(title);
    }

    /**
     * 从Document中获取Element
     *
     * @throws Exception
     */
    @Test
    public void testDOM() throws Exception {
        // 解析文件，获取Document对象
        Document doc = Jsoup.parse(new File("文件地址"), "UTF-8");

        // 获取元素
        // 1、根据id查询元素 getElementById
        Element element = doc.getElementById("id");

        // 2、根据标签获取元素 getElementsByTag
        Element element1 = doc.getElementsByTag("span").first();

        // 3、根据class获取元素 getElementsByClass
        Element element2 = doc.getElementsByClass("class").first();

        // 4、根据属性获取元素 getElementsByAttributes
        Element element3 = doc.getElementsByAttribute("abc").first();

        // 5、根据属性和属性值获取元素 getElementsByAttributeValue()
        Element element4 = doc.getElementsByAttributeValue("href", "http://www.itcast.cn").first();

        // 打印元素的内容
        System.out.println("获取到的元素内容：" + element.text());
    }

    /**
     * 从Element中获取data
     *
     * @throws Exception
     */
    @Test
    public void testData() throws Exception {
        // 解析文件，获取Document对象
        Document doc = Jsoup.parse(new File("文件地址"), "UTF-8");

        // 根据id获取元素
        Element element = doc.getElementById("id");

        String str = "";

        // 元素中获取数据
        // 1、从元素中获取id
        str = element.id();

        // 2、从元素中获取className
        str = element.className();
        /*Set<String> classSet = element.classNames();
        // 按照空格进行拆分
        for (String classSetItem : classSet) {
            System.out.println(classSetItem);
        }*/

        // 3、从元素中获取属性的值attr
        str = element.attr("id");
        str = element.attr("class");

        // 4、从元素中获取所有属性attributes
        Attributes attributes = element.attributes();
        System.out.println(attributes.toString());

        // 5、从元素中获取文本内容text
        str = element.text();

        // 打印获取到的内容
        System.out.println("获取到的数据是：" + str);
    }
}
