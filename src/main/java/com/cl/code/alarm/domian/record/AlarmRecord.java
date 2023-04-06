package com.cl.code.alarm.domian.record;

/**
 * 预警记录
 *
 * @author chengliang
 * @since 1.0.0
 */
public interface AlarmRecord {

    /**
     * 得到预警记录id
     *
     * @return {@link Long}
     */
    Long getAlarmRecordId();

    /**
     * 得到预警项id
     *
     * @return {@link Long}
     */
    Long getAlarmItemId();

    /**
     * 获得商业标识
     *
     * @return {@link Long}
     */
    Long getBusinessId();

    /**
     * 得到预警时间
     *
     * @return {@link Long}
     */
    Long getAlarmTime();

    /**
     * 得到预警类型
     *
     * @return {@link String}
     */
    String getAlarmType();

    /**
     * 得到组标签
     *
     * @return {@link String}
     */
    String getGroupTag();

    /**
     * 得到json
     *
     * @return {@link String}
     */
    String getJson();

}
