package com.fall.robok.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author FAll
 * @date 2022/9/24 15:12
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Img implements Serializable {

    // 序列化版本ID
    private static final long serialVersionUID = 8841433872811285796L;

    String indexSwiper0;

    String indexSwiper1;

    String indexSwiper2;

    String indexSwiper3;

    String indexSwiper4;

    String indexSwiper5;

    String indexSwiper6;

    public ArrayList<String> getImgs() {
        ArrayList<String> imgs = new ArrayList<>();
        imgs.add(indexSwiper0);
        imgs.add(indexSwiper1);
        imgs.add(indexSwiper2);
        imgs.add(indexSwiper3);
        imgs.add(indexSwiper4);
        imgs.add(indexSwiper5);
        return imgs;
    }

}
