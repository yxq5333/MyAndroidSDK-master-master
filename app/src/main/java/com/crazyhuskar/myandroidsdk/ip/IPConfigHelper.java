package com.crazyhuskar.myandroidsdk.ip;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.crazyhuskar.myandroidsdk.R;
import com.crazyhuskar.myandroidsdk.base.MyBaseActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilBean;
import com.crazyhuskar.myandroidsdk.util.MyUtilToast;
import com.crazyhuskar.myandroidsdk.util.bean.eventBus.MyEvent;
import com.crazyhuskar.myandroidsdk.util.bean.eventBus.MyEventActionCode;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.MyDialogFragment;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.base.BindViewHolder;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.listener.OnBindViewListener;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.listener.OnViewClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * IP配置助手
 * <p>
 * Created by Hollow Goods 2016-11-21.
 */
public class IPConfigHelper {

    /**** 删除标识——默认配置——不可删除 ****/
    public static final int DELETE_FLAG_DEFAULT_CONFIG = 1;
    /**** 删除标识——选中配置——不可删除 ****/
    public static final int DELETE_FLAG_CHECKED_CONFIG = 2;
    /**** 删除标识——可删除 ****/
    public static final int DELETE_FLAG_CAN_DELETE = 3;

    private IPConfigHelper() {
    }

    private static IPConfigHelper instance;

    public static IPConfigHelper create() {

        if (instance == null) {
            instance = new IPConfigHelper();
            instance.defaultIPConfigData.clear();
            instance.customizeIPConfigData.clear();
            instance.allIPConfigData.clear();
        }

        return instance;
    }

    /**
     * 默认IP配置集合
     */
    private ArrayList<IPConfig> defaultIPConfigData = new ArrayList<>();
    /**
     * 自定义IP配置集合
     */
    private ArrayList<IPConfig> customizeIPConfigData = new ArrayList<>();
    /**
     * 所有IP配置集合
     */
    private ArrayList<IPConfig> allIPConfigData = new ArrayList<>();
    /**
     * 选中的IP配置
     */
    private IPConfig checkedIPConfig;

    /**
     * 初始化选中的IP配置
     */
    private void initCheckedIPConfig() {

        if (!MyUtilBean.isCollectionEmpty(defaultIPConfigData)) {
            IPConfigFlagDBHelper ipConfigFlagDBHelper = new IPConfigFlagDBHelper();
            boolean flag = false;

            IPConfigFlag ipConfigFlag = ipConfigFlagDBHelper.findFirst();
            if (ipConfigFlag == null) {
                checkedIPConfig = defaultIPConfigData.get(0);
            } else {
                for (IPConfig t : allIPConfigData) {
                    if (IPConfigFlag.equals(t, ipConfigFlag)) {
                        flag = true;
                        checkedIPConfig = t;
                        break;
                    }
                }

                if (!flag) {
                    checkedIPConfig = defaultIPConfigData.get(0);
                }
            }

            if (!flag) {
                ipConfigFlag = IPConfigFlag.copy(checkedIPConfig);
                ipConfigFlagDBHelper.updateAll(ipConfigFlag);
            }
        }
    }

    /**
     * 初始化IP配置集合
     *
     * @param defaultIPConfigs IPConfig... 默认的IP配置集合
     */
    public void initIPConfigs(IPConfig... defaultIPConfigs) {

        if (!MyUtilBean.isArrayEmpty(defaultIPConfigs)) {
            defaultIPConfigData.addAll(Arrays.asList(defaultIPConfigs));
            allIPConfigData.addAll(defaultIPConfigData);
        }

        List<IPConfig> temp = new IPConfigDBHelper().findAll();

        if (!MyUtilBean.isCollectionEmpty(temp)) {
            customizeIPConfigData.addAll(temp);
            allIPConfigData.addAll(temp);
        }

        initCheckedIPConfig();
    }

    /**
     * 获取默认IP配置
     *
     * @return ArrayList<IPConfig>
     */
    public ArrayList<IPConfig> getDefaultIPConfigData() {
        return defaultIPConfigData;
    }

    /**
     * 获取自定义IP配置
     *
     * @return ArrayList<IPConfig>
     */
    public ArrayList<IPConfig> getCustomizeIPConfigData() {
        return customizeIPConfigData;
    }

    /**
     * 获取所有IP配置（默认+自定义）
     *
     * @return ArrayList<IPConfig>
     */
    public ArrayList<IPConfig> getAllIPConfigData() {
        return allIPConfigData;
    }

    /**
     * 添加自定义IP配置
     *
     * @param ipConfig IPConfig
     */
    public void addCustomizeIPConfig(IPConfig ipConfig) {

        for (IPConfig t : allIPConfigData) {
            if (IPConfig.equals(t, ipConfig)) {
                return;
            }
        }

        customizeIPConfigData.add(ipConfig);
        allIPConfigData.add(ipConfig);

        new IPConfigDBHelper().save(ipConfig);
    }

    /**
     * 刷新IP配置
     * 为了添加后，想要删除IP配置而刷新以获取tableId
     */
    public void refreshIPConfig() {

        allIPConfigData.clear();
        if (!MyUtilBean.isCollectionEmpty(defaultIPConfigData)) {
            allIPConfigData.addAll(defaultIPConfigData);
        }

        List<IPConfig> temp = new IPConfigDBHelper().findAll();

        if (!MyUtilBean.isCollectionEmpty(temp)) {
            customizeIPConfigData.addAll(temp);
            allIPConfigData.addAll(temp);
        }
    }

    /**
     * 是否可删除IP配置
     *
     * @param ipConfig IPConfig
     * @return int
     */
    public int canDeleteIPConfig(IPConfig ipConfig) {

        if (defaultIPConfigData.contains(ipConfig)) {
            return DELETE_FLAG_DEFAULT_CONFIG;
        }

        if (ipConfig == checkedIPConfig) {
            return DELETE_FLAG_CHECKED_CONFIG;
        }

        return DELETE_FLAG_CAN_DELETE;
    }

    /**
     * 删除自定义IP配置
     *
     * @param ipConfig IPConfig
     */
    public void deleteCustomizeIPConfig(IPConfig ipConfig) {
        if (canDeleteIPConfig(ipConfig) == DELETE_FLAG_CAN_DELETE) {
            customizeIPConfigData.remove(ipConfig);
            allIPConfigData.remove(ipConfig);
            new IPConfigDBHelper().delete(ipConfig);
        }
    }

    /**
     * 更新IP配置
     *
     * @param ipConfig IPConfig
     */
    public void updateCustomizeIPConfig(IPConfig ipConfig) {
        new IPConfigDBHelper().update(ipConfig);
        if (ipConfig == checkedIPConfig) {
            changeIPConfig(ipConfig);

            MyEvent backEvent = new MyEvent(MyEventActionCode.IP_CONFIG_CHANGED);
            EventBus.getDefault().post(backEvent);
        }
    }

    /**
     * 获取当前IP配置
     *
     * @return IPConfig
     */
    public IPConfig getNowIPConfig() {
        return checkedIPConfig == null ? new IPConfig() : checkedIPConfig;
    }

    /**
     * 切换IP配置
     *
     * @param ipConfig IPConfig
     */
    public void changeIPConfig(IPConfig ipConfig) {

        checkedIPConfig = ipConfig;

        IPConfigFlag ipConfigFlag = IPConfigFlag.copy(checkedIPConfig);
        new IPConfigFlagDBHelper().updateAll(ipConfigFlag);
    }

    /**
     * 显示IP配置
     *
     * @param
     */
    public void showIPConfig(final MyBaseActivity baseActivity) {
//        MyUtilActivity.getInstance().startActivity(activity,IPConfigListActivity.class);
        new MyDialogFragment.Builder(baseActivity.getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_ip_address)    //设置弹窗展示的xml布局
                .setOnBindViewListener(new OnBindViewListener() {   //通过BindViewHolder拿到控件对象,进行修改
                    @Override
                    public void bindView(BindViewHolder bindViewHolder) {
                        bindViewHolder.setText(R.id.et_ip, getNowIPConfig().getIp());
                        bindViewHolder.setText(R.id.et_port, getNowIPConfig().getPort());
                    }
                }).addOnClickListener(R.id.dialogfragmen_ok, R.id.dialogfragmen_cancel,
                R.id.rb_wan,R.id.rb_lan,R.id.tv_ip_history,R.id.rg_netType).setOnViewClickListener(new OnViewClickListener() {
            @Override
            public void onViewClick(BindViewHolder bindViewHolder, View view, MyDialogFragment myDialogFragment) {
                int id = view.getId();
                if (id == R.id.dialogfragmen_cancel) {
                    myDialogFragment.dismiss();
                } else if (id == R.id.dialogfragmen_ok) {
                    updateCustomizeIPConfig(new IPConfig().setIp(bindViewHolder.getView(R.id.et_ip).toString()).setPort(bindViewHolder.getView(R.id.et_port).toString()));
                    MyUtilToast.showShort(baseActivity,"您配置的IP地址为:" + getNowIPConfig().getIp() + ":" + getNowIPConfig().getPort());
                    myDialogFragment.dismiss();
                }else if(id==R.id.rb_wan){

                }else if(id==R.id.rb_lan){

                }else if(id==R.id.tv_ip_history){

                }else if(id==R.id.rg_netType){

                }
            }
        }).create().show();
    }

}
