package com.cl.code.alarm.domain.service;


import com.alibaba.fastjson2.JSON;
import com.cl.code.alarm.BusinessAlarmTrigger;
import com.cl.code.alarm.business.BusinessChangeEvent;
import com.cl.code.alarm.core.*;
import com.cl.code.alarm.infrastructure.AlarmItemRepository;
import com.cl.code.alarm.monitor.ChangeFactors;
import com.cl.code.alarm.monitor.Factor;
import com.cl.code.alarm.notify.NotifyChannel;
import com.cl.code.alarm.notify.NotifyChannels;
import com.cl.code.alarm.notify.NotifyTargetItem;
import com.cl.code.alarm.notify.NotifyTargets;
import com.cl.code.alarm.rule.AlarmRules;
import com.cl.code.alarm.util.UnmodifiableList;
import com.cl.code.el.expression.GreaterEqualExpression;
import com.cl.code.el.param.ConstantParam;
import com.cl.code.el.param.VariableParam;

import java.util.Collections;
import java.util.List;

public class BusinessAlarmServiceTest {

    public static void main(String[] args) {

        AlarmType alarmType = () -> "关联合同预警";

        AlarmStrategyFactory.registerStrategy(alarmType, new AlarmStrategy() {
            @Override
            public Object getValue(VariableParam param, Long businessId) {
                return 190;
            }
        });

        AlarmItemEntity alarmItemEntity = new AlarmItemEntity(1L, alarmType);
        alarmItemEntity.setChangeFactors(new ChangeFactors(Collections.singletonList(new Factor() {
            @Override
            public String getName() {
                return "项目服务类别";
            }
        })));
        alarmItemEntity.setAlarmRules(new AlarmRules(new GreaterEqualExpression(VariableParam.of("程良的身高"), ConstantParam.of(180))));
        alarmItemEntity.setNotifyTargets(new NotifyTargets(Collections.singletonList(new NotifyTargetItem("1", "1"))));
        alarmItemEntity.setNotifyChannels(new NotifyChannels(Collections.singletonList(new NotifyChannel() {
            @Override
            public String getName() {
                return "邮件";
            }
        })));


        String json = JSON.toJSONString(alarmItemEntity);
        System.out.println(json);
        AlarmItemEntity alarmItemEntity1 = JSON.parseObject(json, AlarmItemEntity.class);

        BusinessAlarmTrigger businessAlarmTrigger = new BusinessAlarmTrigger(new AlarmItemRepository() {
            @Override
            public List<AlarmItem> getAlarmItemByChangeFactor(Factor factor) {
                return null;
            }

            @Override
            public void addAlarmItem(AlarmItem alarmItem) {

            }

            @Override
            public void updateAlarmItem(AlarmItem alarmItem) {

            }

            @Override
            public void deleteAlarmItem(UnmodifiableList<Long> alarmItemIds) {

            }
        }, null, null);

        businessAlarmTrigger.trigger(new BusinessChangeEvent(new Factor() {
            @Override
            public String getName() {
                return "项目服务类别";
            }
        }, Collections.singletonList(1L)));


    }
}