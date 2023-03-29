package com.fall.robok.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author FAll
 * @date 2023/3/23 17:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookOfSeller {

    @NotEmpty(message = "openid不能为空")
    String openId;

    @NotEmpty(message = "书本名不能为空")
    String bookName;

    @Min(value = 0,message = "书本价格必须为大于0的数字")
    @NotNull(message = "书本价格不能为空")
    Double price;

    Integer pressId;

    @NotNull(message = "书本状态不能为空")
    Integer status;

    Integer tagId;

    @Nullable
    String description;

    @NotNull(message = "时间戳不能为空")
    String timestamp;

}
