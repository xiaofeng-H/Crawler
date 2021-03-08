package pers.xiaofeng.jd.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.xiaofeng.jd.pojo.Item;

/**
 * @className: pers.xiaofeng.jd.dao.ItemDao
 * @description:
 * @author: xiaofeng
 * @create: 2021-03-08 17:02
 */
public interface ItemDao extends JpaRepository<Item, Long> {
}
