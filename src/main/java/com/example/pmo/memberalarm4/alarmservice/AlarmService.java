package com.example.pmo.memberalarm4.alarmservice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class AlarmService {
    protected HashMap<String, GetAlarmParam> paramsMapper = new HashMap<>();

    abstract protected String messageTemplate();

    abstract protected void setParamsMapper();

    // New method to be implemented by subclasses to load specific data
    abstract protected void loadData(Long id);

    public String getAlarmMessage(Long id) {
        loadData(id);
        setParamsMapper();

        String modifiedMessage = this.messageTemplate();
        String key;
        GetAlarmParam<String> getAlarmParam;


        Iterator<Map.Entry<String, GetAlarmParam>> iterator = paramsMapper.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, GetAlarmParam> entry = iterator.next();
            key = entry.getKey();
            getAlarmParam = entry.getValue();

            modifiedMessage = modifiedMessage.replace(key, getAlarmParam.getValue());
        }

        return modifiedMessage;
    }


}