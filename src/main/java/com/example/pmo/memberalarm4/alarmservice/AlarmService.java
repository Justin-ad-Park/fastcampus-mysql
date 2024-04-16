package com.example.pmo.memberalarm4.alarmservice;

import com.example.pmo.memberalarm4.MemberAlarmEnum4;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class AlarmService {

    protected static final String COMP_ID = "{CompID}";
    protected static final String COMP_VER = "{CompVer}";

    private static final String TEMPLATE_HEADER = String.format("""
            {
            "CompID": "%1$s",
            "Ver": "%2$s",
            "Params": [
            """, COMP_ID, COMP_VER);
    private static final String TEMPLATE_FOOTER = """
            ]
            }
            """;


    protected HashMap<String, GetAlarmParam> paramsMapper = new HashMap<>();

    private MemberAlarmEnum4 memberAlarmEnum;

    abstract protected String templateParams();

    protected String getAlarmTemplate() {
        return TEMPLATE_HEADER + templateParams() + TEMPLATE_FOOTER;
    }

    abstract protected void setParamsMapper();

    // New method to be implemented by subclasses to load specific data
    abstract protected void loadData(Long id);

    /**
     * <pre>
     *     TODO 1. 알람 메시지 생성 로직으로 변경
     *  <Font color=green>Note : 알람 메시지를 Frontend용 API에 리턴하기 좋은 JSON 포멧으로 저장하는 로직으로 변경한다.</Font>
     *  아래 코드는 샘플 설명을 위해 간단하게 문자열을 만들어서 리턴하도록 작성했지만,
     *  실제 사용에서는 각 ID값으로 필요한 데이터를 조회해서 Frontend 용도에 맞게
     *  JSON 데이터를 생성해 두는 방식을 염두하고 개발함
     *
     *      TODO 2. JSON 포맷에 콤포넌트 고유 ID를 포함하면 하위 호환성 유지 가능할 것
     *  JSON 저장 시 Frontend의 component 고유정보(콤포넌트종류, 버전) 정보와 함께 저장하면,
     *  콤포넌트 업그레이드 및 JSON 포맷 변경에 대응이 가능한 것으로 생각됨
     *  이 부분은 Frontend와 논의 필요
     *
     *  {
     *      "CompID" : "MemberGrade",
     *      "CompVer" : "1",    // 마이너 업그레이드는 별도의 변경없이 처리
     *      "UserName" : "홍길동",
     *      "UserGrade" : "1"
     *   }
     *
     *   콤포넌트 업그레이드 방법
     *      -- 메이저 업그레이드는 Frontend 콤포넌트 우선 배포
     *      -- JSON 포맷이 변경되어 기존 JSON 데이터로는 콤포넌트 렌더링이 불가능한 경우, 신규 콤포넌트가 추가되는 경우
     *      -- 기존 Backend 데이터는 기존 Component로 렌더링
     *      -- 새로운 콤포넌트 배포 후부터 신규 데이터는 새로운 Component Ver 사용
     *  </pre>
      */
    public String getAlarmMessage(Long id) {
        loadData(id);
        setParamsMapper();

        String modifiedMessage = this.getAlarmTemplate();

        // 콤포넌트 ID, 콤포넌트 버전 정보를 치환한다.
        modifiedMessage = modifiedMessage.replace(COMP_ID, memberAlarmEnum.getCompID());
        modifiedMessage = modifiedMessage.replace(COMP_VER, memberAlarmEnum.getCompVer());

        // dto 객체의 값으로 메시지(JSON)를 치환한다.
        Iterator<Map.Entry<String, GetAlarmParam>> iterator = paramsMapper.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, GetAlarmParam> entry = iterator.next();

            modifiedMessage = modifiedMessage.replace(entry.getKey(), (String)entry.getValue().getValue());
        }

        return modifiedMessage;
    }

    public void setEnum(MemberAlarmEnum4 memberAlarmEnum) {
        this.memberAlarmEnum = memberAlarmEnum;
    }
}