package com.ly.seckill;

import com.ly.seckill.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillApplicationTests {

    @Autowired
    private SeckillService seckillService;

    @Test
    public void contextLoads() {
        seckillService.seckill2(1L, 1000L);
    }

}
