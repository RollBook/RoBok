package com.fall.robok.service.impl;

import com.fall.robok.mapper.BookMapper;
import com.fall.robok.mapper.IndexImgMapper;
import com.fall.robok.mapper.OrderMapper;
import com.fall.robok.po.Book;
import com.fall.robok.po.Img;
import com.fall.robok.po.Order;
import com.fall.robok.service.IRollBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FAll
 * @date 2022/9/24 14:36
 */

@Service
public class RollBookServiceImpl implements IRollBookService {

    private final IndexImgMapper indexImgMapper;

    private final RedisTemplate<Object, Object> redisTemplate;

    private final BookMapper bookMapper;

    private final OrderMapper orderMapper;
    @Autowired
    public RollBookServiceImpl(IndexImgMapper indexImgMapper, RedisTemplate<Object,Object>
            redisTemplate,BookMapper bookMapper,OrderMapper orderMapper) {
        this.indexImgMapper = indexImgMapper;
        this.redisTemplate = redisTemplate;
        this.bookMapper = bookMapper;
        this.orderMapper = orderMapper;
    }

    /**
     * @author FAll
     * @description 获取首页轮播图
     * @return: java.util.ArrayList<java.lang.String>
     * @date 2022/9/24 15:45
     */
    @Override
    public ArrayList<String> getAllIndexSwiper() {
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        ArrayList<String> imgs = new ArrayList<>();

        // 尝试从redis缓存中获取轮播图url
        Object objects = valueOperations.get("img");
        if (objects instanceof ArrayList<?>) {
            ArrayList<?> arrayList = (ArrayList<?>) objects;
            for (Object o : arrayList) {
                if (o instanceof String) {
                    imgs.add((String) o);
                }
            }
        } else {
            // 如果没有命中缓存，则从数据库中获取
            Img allIndexSwiper = indexImgMapper.getAllIndexSwiper();
            imgs = allIndexSwiper.getImgs();

            // 获取到轮播图url后存入缓存中
            valueOperations.set("img", imgs);
        }
        return imgs;
    }

    @Override
    public List<Book> getSaveBook(String openid) {
        return bookMapper.getSaveBook(openid);
    }

    @Override
    public Integer saveBook(String bookId) {
        return bookMapper.saveBook(bookId);
    }

    public List<Order> getPickOrder(String openid){
        return orderMapper.getPickOrder(openid);
    }

    @Override
    public Integer pickBook(String orderId) {
        return orderMapper.pickBook(orderId);
    }

}
