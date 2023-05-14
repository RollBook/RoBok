package com.fall.adminserver.service.impl;

import com.fall.adminserver.mapper.AnnouncementMapper;
import com.fall.adminserver.model.Announcement;
import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.Event;
import com.fall.adminserver.service.AnnouncementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.CaseFormat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author 楠檀,
 * @date 2023/5/12,
 * @time 9:48,
 */

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementMapper announcementMapper;

    public AnnouncementServiceImpl(AnnouncementMapper announcementMapper){
        this.announcementMapper = announcementMapper;
    }
    @Override
    public PageInfo<Announcement> getAnnouncementList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Announcement> announcementList = announcementMapper.getAnnouncement(null);
        return new PageInfo<>(announcementList,5);
    }

    @Override
    public PageInfo<Announcement> getAnnouncementByLocation(String location, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Announcement> announcementList = announcementMapper.getAnnouncement('%'+location+'%');
        return new PageInfo<>(announcementList,5);
    }

    @Override
    public Integer updateAnnouncement(Announcement announcement) {
        return announcementMapper.updateAnnouncement(announcement);
    }

    @Override
    public PageInfo<Event> getEventList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Event> eventList = announcementMapper.getEvent();
        return new PageInfo<>(eventList,5);
    }
}
