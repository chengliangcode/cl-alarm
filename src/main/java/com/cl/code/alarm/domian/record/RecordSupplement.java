package com.cl.code.alarm.domian.record;

import com.cl.code.alarm.domian.item.AlarmItem;
import lombok.Data;

@Data
public class RecordSupplement {

    /**
     * 分组标识
     */
    private String groupTag;

    /**
     * json
     */
    private String json;

    public static RecordSupplement of(String groupTag, String json) {
        RecordSupplement recordSupplement = new RecordSupplement();
        recordSupplement.setGroupTag(groupTag);
        recordSupplement.setJson(json);
        return recordSupplement;
    }

    public static RecordSupplement def(AlarmItem alarmItem) {
        return of(alarmItem.getAlarmType(), null);
    }

}
