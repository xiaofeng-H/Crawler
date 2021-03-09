package pers.xiaofeng.jd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.xiaofeng.jd.dao.ItemDao;
import pers.xiaofeng.jd.pojo.Item;
import pers.xiaofeng.jd.service.ItemService;

import java.util.List;

/**
 * @className: pers.xiaofeng.jd.service.impl.ItemServiceImpl
 * @description:
 * @author: xiaofeng
 * @create: 2021-03-08 17:05
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Override
    @Transactional  // 开启事务，提交事务
    public void save(Item item) {
        this.itemDao.save(item);
    }

    @Override
    public List<Item> findAll(Item item) {
        // 声明查询条件
        Example<Item> example = Example.of(item);

        // 根据查询条件进行查询数据
        List<Item> list = this.itemDao.findAll(example);

        return list;
    }
}
