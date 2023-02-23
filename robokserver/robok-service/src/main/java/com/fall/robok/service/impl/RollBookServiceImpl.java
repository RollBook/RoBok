package com.fall.robok.service.impl;

import com.fall.robok.mapper.IndexImgMapper;
import com.fall.robok.model.Img;
import com.fall.robok.service.IRollBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author FAll
 * @date 2022/9/24 14:36
 */

@Service
public class RollBookServiceImpl implements IRollBookService {

    private final IndexImgMapper indexImgMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RollBookServiceImpl(IndexImgMapper indexImgMapper, RedisTemplate redisTemplate) {
        this.indexImgMapper = indexImgMapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * @author FAll
     * @description 获取首页轮播图
     * @return: java.util.ArrayList<java.lang.String>
     * @date 2022/9/24 15:45
     */
    @Override
    public ArrayList<Img> getAllIndexSwiper() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        ArrayList<Img> imgs = new ArrayList<>();

        // 尝试从redis缓存中获取轮播图url
        Object objects = valueOperations.get("img");
        if (objects instanceof ArrayList<?>) {
            ArrayList<?> arrayList = (ArrayList<?>) objects;
            for (Object o : arrayList) {
                if (o instanceof Img) {
                    imgs.add((Img) o);
                }
            }
        } else {
            // 如果没有命中缓存，则从数据库中获取
            imgs.addAll(indexImgMapper.getAllIndexSwiper());

            // 获取到轮播图url后存入缓存中
            valueOperations.set("img", imgs);
        }

        return imgs;
    }

}
