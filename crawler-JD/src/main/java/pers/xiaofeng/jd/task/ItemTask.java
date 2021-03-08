package pers.xiaofeng.jd.task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pers.xiaofeng.jd.util.HttpUtils;


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
    private void parse(String html) {
        // 解析html，获取Document对象
        Document document = Jsoup.parse(html);

        // 获取spu
        Elements spuEles = document.select("div#J_goodsList > ul > li");
        for (Element spuEle : spuEles) {
            // 获取spu
            long spu = Long.parseLong(spuEle.attr("data-spu"));
            System.out.println("spu:" + spu);

            // 获取sku信息
            Elements skuEles = spuEle.select("li.ps-item");
            for (Element skuEle : skuEles) {
                // 获取sku
                long sku = Long.parseLong(skuEle.select("[data-sku").attr("data-sku"));
                System.out.println("sku" + sku);
            }
        }
    }
}
