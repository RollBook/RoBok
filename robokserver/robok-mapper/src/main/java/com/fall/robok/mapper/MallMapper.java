package com.fall.robok.mapper;

import com.fall.robok.po.Book;
import com.fall.robok.po.Cart;
import com.fall.robok.po.MallSwiper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/4/13,
 * @time 17:27,
 */

@Mapper
@Repository
public interface MallMapper {

    List<MallSwiper> getMallSwiper();

    List<Book> getGoods();

    List<Book> searchGoods(String input);

    List<Book> searchGoodsByHow(String input);

    List<Book> searchGoodsByPrice(String input);

    Book getGoodById(String bookId);

    Integer addCart(Cart cart);
}
