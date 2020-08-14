package com.crazyhuskar.myandroidsdk.ip;

import com.crazyhuskar.myandroidsdk.db.MyDBHelper;
import com.crazyhuskar.myandroidsdk.util.XUtils.LogUtils;
import com.crazyhuskar.myandroidsdk.util.XUtils.XDBUtils;

import org.xutils.ex.DbException;

import java.util.Objects;

/**
 * IP配置数据库助手
 * <p>
 * Created by YXQ 2020-08-12.
 */
class IPConfigDBHelper extends MyDBHelper<IPConfig> {

    public void update(IPConfig ipConfig) {
        try {
            Objects.requireNonNull(XDBUtils.getDbManager()).update(ipConfig);
        } catch (DbException e) {
            LogUtils.Log(e.getMessage());
        }
    }

}
