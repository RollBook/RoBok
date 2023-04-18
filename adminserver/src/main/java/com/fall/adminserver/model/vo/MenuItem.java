package com.fall.adminserver.model.vo;


import com.fall.adminserver.model.MenuSubItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author FAll
 * @date 2023/4/18 下午9:11
 * 页面菜单栏子项
 */
@Data
@AllArgsConstructor
public class MenuItem implements Serializable {
    private int id;                 // 子项id
    private String path;            // 子项路径
    private String itemName;        // 子项名称
    private List<MenuSubItem> children;// 下属子项

    public void appendChildren( List<MenuSubItem> childrenToAppend ) {
        HashSet<MenuSubItem> childrenSet = new HashSet<>(children);
        childrenSet.addAll(childrenToAppend);
        this.setChildren(new ArrayList<>(childrenSet));
    }
}
