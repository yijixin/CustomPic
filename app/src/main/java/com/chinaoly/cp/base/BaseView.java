package com.chinaoly.cp.base;

/**
 * @author Created by Administrator on 2017/11/8.
 */

public interface BaseView {
    /*******内嵌加载*******/

    /**
     * 加载数据展示
     * @param title
     */
    void showLoading(String title);

    /**
     * 停止加载
     */
    void stopLoading();

    /**
     * 错误信息提示
     * @param msg
     */
    void showErrorTip(String msg);
}
