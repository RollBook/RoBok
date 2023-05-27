package com.fall.robok.service;

/**
 * @author 楠檀,
 * @date 2023/5/25,
 * @time 16:24,
 */
public interface IMachineService {

    String lightControl(String mes);

    String lockControl(String mes);

    String beepControl(String mes);

    String machineControl(String mes);


}
