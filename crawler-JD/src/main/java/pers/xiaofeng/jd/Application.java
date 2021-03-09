package pers.xiaofeng.jd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @className: pers.xiaofeng.jd.Application
 * @description:
 * @author: xiaofeng
 * @create: 2021-03-08 17:08
 */
@SpringBootApplication
// 使用定时任务，需要先开启定时任务，需要添加注解
@EnableScheduling
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
