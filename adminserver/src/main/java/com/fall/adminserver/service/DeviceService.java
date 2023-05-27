package com.fall.adminserver.service;

/**
 * @author 楠檀,
 * @date 2023/5/25,
 * @time 14:04,
 */
public interface DeviceService {

    String machineDevice(String pressId);

    String lightControl(String mes);

    String lockControl(String mes);

    String beepControl(String mes);
}
