package com.fall.robok.service.impl;

import com.fall.robok.mapper.MallMapper;
import com.fall.robok.po.Book;
import com.fall.robok.po.MallSwiper;
import com.fall.robok.service.IMallService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/4/13,
 * @time 17:25,
 */
@Service
public class MallServiceImpl implements IMallService {

    private final MallMapper mallMapper;

    @Autowired
    public MallServiceImpl(MallMapper mallMapper){
        this.mallMapper = mallMapper;
    }

    /**
     * @author Tan
     * @description 获取商城首页轮播图
     * @param
     * @return: java.util.List<com.fall.robok.po.MallSwiper>
     * @date  21:08
     */
    @Override
    public List<MallSwiper> getMallSwiper() {
        return mallMapper.getMallSwiper();
    }


    @Override
    public List<Book> getGoods(Integer nowPage) {
        Page<Object> page = PageHelper.startPage(nowPage, 10);
        return mallMapper.getGoods();
    }

    @Override
    public List<Book> searchBooks(String input) {
        return mallMapper.searchGoods(input);
    }

    @Override
    public List<Book> searchBooksByHow(String input) {
        return mallMapper.searchGoodsByHow(input);
    }

    @Override
    public List<Book> searchBooksByPrice(String input) {
        return mallMapper.searchGoodsByPrice(input);
    }

    @Override
    public Book getGoodById(String bookId) {
        return mallMapper.getGoodById(bookId);
    }


}
