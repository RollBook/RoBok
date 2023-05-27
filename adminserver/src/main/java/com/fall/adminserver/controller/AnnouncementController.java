package com.fall.adminserver.controller;

import com.fall.adminserver.model.Announcement;
import com.fall.adminserver.model.Event;
import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.service.AnnouncementService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author 楠檀,
 * @date 2023/5/12,
 * @time 9:53,
 */

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService){
        this.announcementService = announcementService;
    }

    @Operation(summary = "获取公告列表")
    @GetMapping("/get_announcement_list")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<Announcement>> getAnnouncementList(@RequestParam("pageNum")Integer pageNum,
                                                              @RequestParam("pageSize")Integer pageSize) {

        return ResponseRecord.success(announcementService.getAnnouncementList(pageNum,pageSize));
    }

    @Operation(summary = "根据位置获取公告列表")
    @GetMapping("/get_announcement_by_location")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<Announcement>> getAnnouncementByLocation(@RequestParam("location")String location,
                                                                        @RequestParam("pageNum")Integer pageNum,
                                                                      @RequestParam("pageSize")Integer pageSize) {

        return ResponseRecord.success(announcementService.getAnnouncementByLocation(location,pageNum,pageSize));
    }

    @Operation(summary = "修改公告")
    @PostMapping("/update_announcement")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<Integer> updateAnnouncement(@Valid @RequestBody Announcement announcement){
        return Optional.ofNullable(announcementService.updateAnnouncement(announcement))
                .map(e->(ResponseRecord.success("修改成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

    @Operation(summary = "获取活动列表")
    @GetMapping("/get_event_list")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<Event>> getEventList(@RequestParam("pageNum")Integer pageNum,
                                                               @RequestParam("pageSize")Integer pageSize) {

        return ResponseRecord.success(announcementService.getEventList(pageNum,pageSize));
    }
}
