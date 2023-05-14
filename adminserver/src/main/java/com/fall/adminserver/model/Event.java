package com.fall.adminserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 楠檀,
 * @date 2023/5/13,
 * @time 0:16,
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private String bookId;

    private String imageSrc;

    private String value;
}
