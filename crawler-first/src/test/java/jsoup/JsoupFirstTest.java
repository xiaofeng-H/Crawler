package jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.net.URL;

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
     * 用DOM方式从Document中获取Element
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

    /**
     * 用Selector从Document中获取Element
     *
     * @throws Exception
     */
    @Test
    public void testSelector() throws Exception {
        // 解析文件，获取Document对象
        Document doc = Jsoup.parse(new File("文件地址"), "UTF-8");

        // tagname：通过标签查找元素，比如：span
        Elements elements = doc.select("span");
        for (Element element : elements) {
            System.out.println(element.text());
        }

        // #id：通过ID查找元素，比如：#city_bj
        Element element1 = doc.select("#city_bj").first();
        System.out.println("获取到的结果是：" + element1.text());

        // .class：通过class名称查找元素，比如：.class_a
        Element element2 = doc.select(".class_a").first();
        System.out.println("获取到的结果是：" + element2.text());

        // [attribute]：利用属性查找元素：比如：[abc]
        Element element3 = doc.select("[abc]").first();
        System.out.println("获取到的结果是：" + element3.text());

        // [attribute]：以用属性值来查找元素，比如：[class=s_name]
        Element element4 = doc.select("[class=s_name]").first();
        System.out.println("获取到的结果是：" + element4.text());
    }

    /**
     * 用Selector（组合查询）从Document中获取Element
     *
     * @throws Exception
     */
    @Test
    public void testSelectorGroup() throws Exception {
        // 解析文件，获取Document对象
        Document doc = Jsoup.parse(new File("文件地址"), "UTF-8");

        Element element = null;

        // el#id：元素+ID 比如：h3#city_bj
        element = doc.select("h3#city_bj").first();

        // el.class：元素+class 比如：li.class_a
        element = doc.select("li.class_a").first();

        // el[attr]：元素+属性名 比如：span[abc]
        element = doc.select("span[abc]").first();

        // 任意组合：比如：span[abc].s_name
        element = doc.select("span[abc].s_name").first();

        // ancestor child：查找某个元素下子元素，比如：.city_con li 查找"city-con"下的所有li
        Elements elements = doc.select(".city_con li");

        // parent > child：查找某个父元素下的直接子元素，比如：
        // .city_con > ul > li 查找city_con第一级（直接子元素）的ul，再找所有ul下的第一级li
        elements = doc.select(".city_con > ul > li");

        // 查找父元素下面的所有直接子元素：parent > *
        elements = doc.select(".city_con > ul > *");

        for (Element element1 : elements) {
            System.out.println("遍历的结果是：" + element);
        }

        // 输出结果
        System.out.println("获取到的结果是：" + element.text());
    }

}
