package com.fall.adminserver.service;

import com.fall.adminserver.model.Announcement;
import com.fall.adminserver.model.Event;
import com.github.pagehelper.PageInfo;

/**
 * @author 楠檀,
 * @date 2023/5/12,
 * @time 9:48,
 */
public interface AnnouncementService {

    PageInfo<Announcement> getAnnouncementList(Integer pageNum, Integer pageSize);

    PageInfo<Announcement> getAnnouncementByLocation(String location,Integer pageNum, Integer pageSize);

    Integer updateAnnouncement(Announcement announcement);

    PageInfo<Event> getEventList(Integer pageNum, Integer pageSize);

}
