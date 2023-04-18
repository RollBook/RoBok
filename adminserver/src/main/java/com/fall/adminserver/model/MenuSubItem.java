package com.fall.adminserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author FAll
 * @date 2023/4/18 下午10:52
 * 菜单子项下属
 */
@Data
@NoArgsConstructor
public class MenuSubItem implements Serializable {
    private int id;         // id

    private String path;    // 路径

    private String itemName;// 名称

    private int authority;  // 所需权限

    /**
     * @author FAll
     * @description 重写equals
     * @param o 对象
     * @return: boolean
     * @date 2023/4/18 下午11:50
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuSubItem item = (MenuSubItem) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(path, item.path) &&
                Objects.equals(itemName, item.itemName) &&
                Objects.equals(authority, item.authority);
    }

    /**
     * @author FAll
     * @description 重写hashCode
     * @return: int
     * @date 2023/4/18 下午11:50
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, path, itemName, authority);
    }

}
