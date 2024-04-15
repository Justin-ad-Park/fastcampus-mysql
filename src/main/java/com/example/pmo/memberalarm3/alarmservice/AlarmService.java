package com.example.pmo.memberalarm3.alarmservice;

import java.util.List;

public abstract class AlarmService {

    abstract protected String messageTemplate();

    abstract protected List<GetAlarmParam> getGetters();

    abstract protected List<String> getParams();

    // New method to be implemented by subclasses to load specific data
    abstract protected void loadData(Long id);

    public String getAlarmMessage(Long id) {
        loadData(id);

        List<String> params = this.getParams();
        List<GetAlarmParam> getters = this.getGetters();

        if (params.size() != getters.size()) {
            throw new RuntimeException("Parameter count mismatch.");
        }

        String modifiedMessage = this.messageTemplate();
        for (int i = 0; i < params.size(); i++) {
            String value = (String) getters.get(i).getValue();
            modifiedMessage = modifiedMessage.replace(params.get(i), value);
        }

        return modifiedMessage;
    }


}