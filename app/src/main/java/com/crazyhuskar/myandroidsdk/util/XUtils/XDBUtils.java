package com.crazyhuskar.myandroidsdk.util.XUtils;

import com.crazyhuskar.myandroidsdk.SystemConfig;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * XUtils数据库工具类
 * Created by HG
 */
public class XDBUtils {

    /**
     * 数据库版本更新监听
     **/
    private static MyDbUpgradeListener myDbUpgradeListener = null;

    /**
     * 获取数据库管理器
     *
     * @return
     */
    public static DbManager getDbManager() throws DbException {

        if (myDbUpgradeListener == null) {
            myDbUpgradeListener = new MyDbUpgradeListener();
        }

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig().setDbName(SystemConfig.DATABASE_NAME)// 创建数据库的名称
                .setDbVersion(SystemConfig.DB_VERSION)// 数据库版本号
                .setDbUpgradeListener(myDbUpgradeListener);// 数据库更新操作

        return x.getDb(daoConfig);
    }

    /**
     * 数据库版本更新监听
     *
     * @author HG
     */
    private static class MyDbUpgradeListener implements DbManager.DbUpgradeListener {

        /**
         * 版本更新操作
         */
        @Override
        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
            // TODO: ...
            // db.addColumn(...);
            // db.dropTable(...);
            // ...
        }
    }

}
