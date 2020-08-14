package com.crazyhuskar.myandroidsdk.util.bean.eventBus;
/**
 * 意图代码
 */
public enum  MyEventActionCode {


    /**** NFC扫描结果 ****/
    NFC_SCAN_RESULT,
    /**** Tag扫描结果 ****/
    TAG_SCAN_RESULT,
    /**** 删除图片 ****/
    REMOVE_IMAGE,
    /**** 文件选择 ****/
    FILE_SELECTOR,
    /**** 二维码扫描结果 ****/
    QR_CODE_SCAN_RESULT,
    /**** 网络状态——断网 ****/
    NETWORK_STATUS_BREAK,
    /**** 网络状态——联网 ****/
    NETWORK_STATUS_LINK,
    /**** IP配置更新（添加、修改） ****/
    IP_CONFIG_UPDATE,
    /**** 修改了IP配置（切换） ****/
    IP_CONFIG_CHANGED,
    /**** 热补丁安装失败 ****/
    TINKER_NO_RESULT,
    /**** 热补丁安装失败 ****/
    TINKER_INSTALL_SUCCESS,
}
