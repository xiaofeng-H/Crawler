package pers.xiaofeng.jd.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pers.xiaofeng.jd.pojo.Item;
import pers.xiaofeng.jd.service.ItemService;
import pers.xiaofeng.jd.util.HttpUtils;

import java.util.Date;
import java.util.List;


/**
 * @className: pers.xiaofeng.jd.task.ItemTask
 * @description:
 * @author: xiaofeng
 * @create: 2021-03-08 17:54
 */
@Component
public class ItemTask {

    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private ItemService itemService;

    private static final ObjectMapper mapper = new ObjectMapper();

    // 当下载任务完成后，间隔多长时间进行下一次的任务
    @Scheduled(fixedDelay = 100 * 1000)
    public void itemTask() throws Exception {
        // 声明需要解析的初始地址
        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&wq=%E6%89%8B%E6%9C%BA&s=57&click=0&page=";

        // 按照页码对手机搜索结果进行遍历解析
        for (int i = 1; i < 10; i += 2) {
            String html = httpUtils.doGetHtml(url + i);
            // 解析页面，获取商品数据并存储
            this.parse(html);
        }

        System.out.println("手机数据抓取完成！");
    }

    /**
     * 解析页面，获取商品数据并存储
     *
     * @param html
     */
    private void parse(String html) throws Exception {
        System.out.println("html信息为：" + html);
        // 解析html，获取Document对象
        Document document = Jsoup.parse(html);

        // 获取spu
        Elements spuEles = document.select("div#J_goodsList > ul > li");
        for (Element spuEle : spuEles) {
            // 获取spu
            long spu = Long.parseLong(spuEle.attr("data-spu"));

            // 获取sku信息
            Elements skuEles = spuEle.select("li.ps-item");
            for (Element skuEle : skuEles) {
                // 获取sku
                long sku = Long.parseLong(skuEle.select("[data-sku]").attr("data-sku"));

                // 根据sku查询商品数据
                Item item = new Item();
                item.setSku(sku);
                /*List<Item> list = this.itemService.findAll(item);

                if (list.size() > 0) {
                    // 如果存在，就进行下一次循环，该商品不保存，因为已存在
                    continue;
                }*/

                // 设置商品的spu
                item.setSpu(spu);

                // 获取商品详情的url
                String itemUrl = "https://item.jd.com/" + sku + ".html";
                item.setUrl(itemUrl);

                // 获取商品的图片
                String picUrl = "https:" + skuEle.select("img[data-sku]").first().attr("data-lazy-img");
                picUrl = picUrl.replace("/n9/", "/n1/");
                String picName = this.httpUtils.doGetImage(picUrl);
                item.setPic(picName);

                // 获取商品的价格
                String priceJson = this.httpUtils.doGetHtml("https://p.3.cn/prices/mgets?sku=J_" + sku);
                double price = mapper.readTree(priceJson).get(0).get("p").asDouble();

                // 获取商品的标题
                String itemInfo = this.httpUtils.doGetHtml(item.getUrl());
                String title = Jsoup.parse(itemInfo).select("div.sku-name").text();
                item.setTitle(title);

                item.setCreated(new Date());
                item.setUpdated(item.getCreated());

                // 保存商品数据到数据库中
                //this.itemService.save(item);
                // 输出信息
                System.out.println("==========>商品信息为：" + item);
            }
        }
    }
}
