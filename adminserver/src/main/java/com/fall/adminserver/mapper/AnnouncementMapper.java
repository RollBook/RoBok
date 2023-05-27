package com.fall.adminserver.mapper;

import com.fall.adminserver.model.Announcement;
import com.fall.adminserver.model.Event;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/5/12,
 * @time 9:41,
 */

@Mapper
@Repository
public interface AnnouncementMapper {

    List<Announcement> getAnnouncement(String location);

    Integer updateAnnouncement(Announcement announcement);

    List<Event> getEvent();

}
