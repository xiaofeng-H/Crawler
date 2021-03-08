package pers.xiaofeng.jd.service;

import pers.xiaofeng.jd.pojo.Item;

import java.util.List;

/**
 * @className: pers.xiaofeng.jd.service.ItemService
 * @description:
 * @author: xiaofeng
 * @create: 2021-03-08 17:03
 */
public interface ItemService {

    /**
     * 保存商品
     *
     * @param item
     */
    public void save(Item item);

    /**
     * 根据条件查询商品
     *
     * @param item
     * @return
     */
    public List<Item> findAll(Item item);

}
