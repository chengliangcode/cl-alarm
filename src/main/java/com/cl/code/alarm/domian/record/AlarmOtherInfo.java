package com.cl.code.alarm.domian.record;

import com.cl.code.alarm.domian.item.AlarmItem;
import lombok.Data;

/**
 * 预警其他信息
 *
 * @author chengliang
 * @since 1.0.0
 */
@Data
public class AlarmOtherInfo<T> {

    /**
     * 分组标识
     */
    private String groupTag;

    /**
     * json
     */
    private String json;

    /**
     * 信息
     */
    private T info;

    public static <T> AlarmOtherInfo<T> of(String groupTag, String json) {
        AlarmOtherInfo<T> alarmOtherInfo = new AlarmOtherInfo<>();
        alarmOtherInfo.setGroupTag(groupTag);
        alarmOtherInfo.setJson(json);
        return alarmOtherInfo;
    }

    public static <T> AlarmOtherInfo<T> def(AlarmItem alarmItem) {
        return of(alarmItem.getAlarmType(), null);
    }

}
