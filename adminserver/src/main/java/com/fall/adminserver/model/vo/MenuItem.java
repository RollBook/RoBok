package com.fall.adminserver.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author FAll
 * @date 2023/4/18 下午9:11
 * 页面菜单栏子项
 */
@Data
@AllArgsConstructor
public class MenuItem {
    private int id;                 // 子项id
    private String path;            // 子项路径
    private String itemName;        // 子项名称
    private List<MenuItem> children;// 下属子项
}
