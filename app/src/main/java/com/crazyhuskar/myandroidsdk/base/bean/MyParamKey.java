package com.crazyhuskar.myandroidsdk.base.bean;

/**
 * 传递数据键
 */
public enum  MyParamKey {

    /**** 单个文件(Java File)返回 ****/
    File,
    /**** 多个文件(Java File)返回 ****/
    Files,
    /**** 多个文件(框架中的AppFile)返回 ****/
    AppFiles,
    /**** 条码扫描结果返回 ****/
    QRCodeScanResult,
    /**** NFC扫描结果返回 ****/
    NFCScanResult,
    /**** 快速适配器第1代文字模板数据 ****/
    HGFastItemWordData,
    /**** 快速适配器第1代自定义模板数据 ****/
    HGFastItemCustomizeData,
    /**** URL ****/
    URL,
    /**** 位置 ****/
    Position,
    /**** 多个位置 ****/
    Positions,
    /**** 标题 ****/
    Title,
    /**** 最大数量 ****/
    MaxCount,
    /**** 文件过滤格式 ****/
    FileFilter,
    /**** 已选中的文件 ****/
    SelectedFile,
    /**** 输入的内容 ****/
    InputValue,
    /**** 年 ****/
    DateYear,
    /**** 月 ****/
    DateMonth,
    /**** 日 ****/
    DateDay,
    /**** 时 ****/
    DateHour,
    /**** 分 ****/
    DateMinute,
    /**** 秒 ****/
    DateSecond,
    /**** 时间戳 ****/
    DateTimeInMillis,
    /**** 界面的工作模式 ****/
    ViewTag,
    /**** 界面跳转请求码 ****/
    RequestCode,
    /**** 框架Test界面数据 ****/
    HGTestList,
    /**** IP配置数据 ****/
    IPConfig,
    /**** 文件预览下载请求方式 ****/
    FileReadDownloadHttpMethod,
    //
    ;

    @Override
    public String toString() {
        return "hg.param.key." + name();
    }
}
