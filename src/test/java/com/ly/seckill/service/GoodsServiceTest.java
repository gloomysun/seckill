package com.ly.seckill.service;

import com.ly.seckill.SeckillApplication;
import com.ly.seckill.vo.SeckillGoodsVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeckillApplication.class)
public class GoodsServiceTest {


    @Autowired
    private GoodsService goodsService;

    @Test
    public void testReduceStock() {
        for(int i =0;i<100;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    goodsService.reduceStock(10001111L);
                }
            }).start();
        }
    }
}