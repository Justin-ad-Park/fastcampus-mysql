package com.example.databasemysql.pmodomain.employeediscount;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;


/**
 * OM_EMPL_DISC_TRAN 테이블에 대량의 레코드를 삽입하는 클래스
 * OM_EMPL_DISC_TRAN_ORDER에도 함께 데이터 생성
 */
public class OmEmplDiscTranDataGeneratorV2 {

    /* 데이터베이스 연결 정보 설정(로그 기록 - 디버깅용) */
    //private static final String url = "jdbc:mysql://localhost:3306/fast_sns?rewriteBatchedStatements=true&profileSQL=true&logger=Slf4JLogger&maxQuerySizeToLog=999&characterEncoding=UTF-8&serverTimezone=Asia/Seoul"; // 데이터베이스 URL

    /* 데이터베이스 연결 정보 설정(로그 미기록 - 빠른 데이터 생성용) */
    //public static final String url = "jdbc:mysql://localhost:3306/fast_sns?characterEncoding=UTF-8&serverTimezone=Asia/Seoul"; // 데이터베이스 URL
    public static final String url = "jdbc:mysql://db-665ft-kr.vpc-pub-cdb.ntruss.com:3306/toy_project"; // 데이터베이스 URL
    private static final String user = "toy_dbuser"; // 데이터베이스 사용자 이름
    private static final String password = "project##77"; // 데이터베이스 비밀번호


    //-- 데이터 생성 조건 셋팅 영역 --------------------------------------
    // 1. OM_EMPL_DISC_TRAN 테이블에 대량의 레코드를 생성하기 위한 조건--------
    // OM_ORDER_ID 시작 번호 : 기존 데이터가 있을 경우 이어서 생성하기 위해, 기존 주문번호 +1 이상의 값을 설정
    private static final long startOrderId = 1;

    // 생성할 트랜잭션 수
    private static final long recordCount = 1000;

    // OM_ORDER_DT 생성 범위 설정
    private static final LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
    private static final LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59, 59);



    // 2. OM_EMPL_DISC_TRAN_ORDER 테이블에 그룹별 임직원 포인트 사용 내역을 생성하기 위한 조건--------
    // 트랜잭션에 랜덤 생성할 임직원 할인 레코드의 최대 수
    private static final int maxDataRows = 5;

    // 할인 그룹 ID 증가 범위 : 랜덤 생성 시 중복을 피해 GrpId가 생성되도록, 숫자가 클수록 값의 분산이 커짐
    private static final int discGrpIdBoundFactor = 3;

    // 임직원 사번 범위
    private static final int employeeIdMin = 1;
    private static final int employeeIdMax = 5000;


    // 할인 금액 범위 (100단위로)
    private static final int redeemPointsMin = 1000;
    private static final int redeemPointsMax = 20000;
    private static final int redeemPointsStep = 100;
    //-- 데이터 생성 조건 셋팅 영역 끝 --------------------------------------//



    public static void main(String[] args) {


        // 삽입 SQL 문 (필요한 컬럼만 명시)
        final String insertSQL = "INSERT INTO OM_EMPL_DISC_TRAN (OM_ORDER_ID, OM_ORDER_DT, MALL_ID, OD_ORDER_ID, CREATE_DT) VALUES (?, ?, ?, ?, ?)";

        // 랜덤 객체 생성
        Random random = new Random();

        /* try with resources 구문을 사용하여 자원을 자동으로 해제 */
        try (
                // 데이터베이스 연결
                Connection conn = DriverManager.getConnection(url, user, password);
                // PreparedStatement 생성
                PreparedStatement pstmt = conn.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            // 자동 커밋 비활성화 (배치 처리를 위해)
            conn.setAutoCommit(false);

            for (int i = 0; i < recordCount; i++) {
                // OM_ORDER_ID 생성 (설정된 초기값부터 순차적으로 증가)
                String omOrderId = String.valueOf(startOrderId + i);

                /**
                 * OM_ORDER_DT 랜덤 생성 : startDate와 endDate 사이의 랜덤 날짜 생성
                 *  실제 데이터는 날짜가 순차적으로 증가해야 하나 성능 테이스용 더미 데이터이기 때문에 날짜를 랜덤으로 생성함
                  */
                LocalDateTime randomOrderDate = getRandomDateBetween(random, startDate, endDate);

                // CREATE_DT 현재 시간으로 설정
                Timestamp createDt = new Timestamp(System.currentTimeMillis());

                // PreparedStatement에 파라미터 설정
                pstmt.setString(1, omOrderId);               // OM_ORDER_ID
                pstmt.setTimestamp(2, Timestamp.valueOf(randomOrderDate)); // OM_ORDER_DT
                pstmt.setString(3, "B2E");                    // MALL_ID
                pstmt.setNull(4, Types.BIGINT);               // OD_ORDER_ID (NULL)
                pstmt.setTimestamp(5, createDt);              // CREATE_DT

                // 배치에 추가
                int affectedRows = pstmt.executeUpdate();

                // 삽입된 데이터의 자동 생성된 키(PK) 확인
                Long tranId = getKey(affectedRows, pstmt);

                /**
                 * OM_EMPL_DISC_TRAN_ORDER 테이블에 브랜드별 차감 내역 생성
                 */
                generateOrderData(conn, tranId, Timestamp.valueOf(randomOrderDate));

                // 배치 사이즈가 1000일 때 한 번에 실행
                if ((i + 1) % 100 == 0) {
                    pstmt.executeBatch();
                    conn.commit();
                    System.out.println((i + 1) + "개의 레코드가 삽입되었습니다.");
                }
            }

            // 남은 배치 실행
            pstmt.executeBatch();
            conn.commit();
            System.out.println("총 " + recordCount + "개의 레코드가 성공적으로 삽입되었습니다.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static long getKey(int affectedRows, PreparedStatement pstmt) throws SQLException {
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
        }

        return 0;
    }

    // startDate와 endDate 사이의 랜덤 날짜를 생성하는 메소드
    private static LocalDateTime getRandomDateBetween(Random random, LocalDateTime startDate, LocalDateTime endDate) {
        long seconds = startDate.until(endDate, ChronoUnit.SECONDS);
        long randomSeconds = (long) (random.nextDouble() * seconds);
        return startDate.plusSeconds(randomSeconds);
    }

    private static void generateOrderData(Connection conn, long transId, Timestamp omOrderDt) {
        // 삽입 SQL 문
        String insertSQL = "INSERT INTO OM_EMPL_DISC_TRAN_ORDER (UR_EMPLOYEE_CD, PS_EMPL_DISC_GRP_ID, OM_EMPL_DISC_TRAN_ID, REDEEM_POINTS, OM_ORDER_DT, CREATE_DT) VALUES (?, ?, ?, ?, ?, ?)";

        // 랜덤 객체 생성
        Random random = new Random();

        int dataRows = random.nextInt(maxDataRows); // 랜덤 생성할 데이터 수

        int discGrpId = 0;

        for(int i = 0; i < dataRows; i++) {

            // 할인 그룹 ID 랜덤 생성
            discGrpId += random.nextInt(discGrpIdBoundFactor) + 1;

            /* 생성된 데이터 확인용 */
            // System.out.println("discGrpId: " + discGrpId + ", transId: " + transId + ", omOrderDt: " + omOrderDt);

            /* try with resources 구문을 사용하여 자원을 자동으로 해제 */
            try (
                    // PreparedStatement 생성
                    PreparedStatement insertPstmt = conn.prepareStatement(insertSQL);
            ) {
                // 임직원 사번 랜덤 생성
                String employeeId = String.valueOf(random.nextInt(employeeIdMax - employeeIdMin + 1) + employeeIdMin);

                // 할인 금액 랜덤 생성 (100단위로)
                int redeemPoints = redeemPointsMin + (random.nextInt((redeemPointsMax - redeemPointsMin) / redeemPointsStep + 1) * redeemPointsStep);

                // PreparedStatement에 파라미터 설정
                insertPstmt.setString(1, employeeId);          // UR_EMPLOYEE_CD
                insertPstmt.setInt(2, discGrpId);              // PS_EMPL_DISC_GRP_ID
                insertPstmt.setLong(3, transId);                // OM_EMPL_DISC_TRAN_ID
                insertPstmt.setInt(4, redeemPoints);           // REDEEM_POINTS
                insertPstmt.setTimestamp(5, omOrderDt);        // OM_ORDER_DT
                insertPstmt.setNull(6, Types.TIMESTAMP); // CREATE_DT (null)

                // 배치에 추가
                insertPstmt.addBatch();

                try {
                    insertPstmt.executeBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
