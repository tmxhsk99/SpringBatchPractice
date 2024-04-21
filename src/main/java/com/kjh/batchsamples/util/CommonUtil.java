package com.kjh.batchsamples.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {
    public static String getCurDay_yyyyMMdd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    public static String getCurDay_yyyyMMddHHmmss() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     * 어제 날짜를 구한다.
     * @return
     */
    public static String getYesterDay_yyyyMMdd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); // 현재 날짜에서 하루를 뺌
        return sdf.format(calendar.getTime());
    }
    /**
     * yyyyMMddHHmmss 형식을 yyyy-MM-dd HH:mm:ss 형식으로 변환한다.
     *
     * @param yyyyMMddHHmmss
     * @return String yyyy-MM-dd HH:mm:ss 형식
     */
    public static String yyyyMMddHHmmssToISO8601(String yyyyMMddHHmmss) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(yyyyMMddHHmmss, inputFormatter);
        String formattedDnt = dateTime.format(outputFormatter);

        return formattedDnt;
    }

    public static String removeDash(String str) {
        return str.replaceAll("-", "");
    }

    public static String getCurTime_HHmmss() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        return sdf.format(new Date());
    }

    /**
     *  n자리 String 랜덤 숫자 반환
     * @param numberSize
     * @return
     */
    public static String getRandomNum(int numberSize) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < numberSize; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

    /**
     * filePath가 디렉터리인지 파일인지 확인하고
     * 디렉터리 라면 내부에 모든 파일을 삭제하고, 파일이라면 해당 파일만 삭제
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            // 파일이 존재하지 않느 경우 return 처리 한다.
            if (!Files.exists(path)) {
                return;
            }

            if (Files.isDirectory(path)) {
                Files.walk(path)
                        .map(Path::toFile)
                        .forEach(File::delete);
            } else {
                Files.delete(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
