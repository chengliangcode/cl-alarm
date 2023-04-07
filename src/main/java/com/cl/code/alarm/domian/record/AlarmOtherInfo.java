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
public class AlarmOtherInfo<B> {

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
    private B info;

    public static <B> AlarmOtherInfo<B> of(String groupTag, String json) {
        AlarmOtherInfo<B> alarmOtherInfo = new AlarmOtherInfo<>();
        alarmOtherInfo.setGroupTag(groupTag);
        alarmOtherInfo.setJson(json);
        return alarmOtherInfo;
    }

    public static <B> AlarmOtherInfo<B> def(AlarmItem alarmItem) {
        return of(alarmItem.getAlarmType(), null);
    }

}
