package com.fall.adminserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 楠檀,
 * @date 2023/5/12,
 * @time 9:38,
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {
    private String location;

    private String value;
}
