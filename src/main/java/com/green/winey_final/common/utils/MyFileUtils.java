package com.green.winey_final.common.utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

public class MyFileUtils {
    //확장자 리턴하는 메소드
    public static String getExt(String fileNm) { // abcd.123.hhh.jpg
        return fileNm.substring(fileNm.lastIndexOf(".") + 1);
    }
    //파일명만 리턴하는 메소드
    public static String getFileNm(String fileNm) {
        return fileNm.substring(0, fileNm.lastIndexOf("."));
    }
    // UUID 이용, 랜덤값 파일명 리턴
    public static String makeRandomFileNm(String fileNm) {
        return UUID.randomUUID() + "." +  getExt(fileNm);
    }
    //절대경로 리턴
    public static String getAbsolutePath(String src) {
        return Paths.get(src).toFile().getAbsolutePath();
    }

    //폴더 삭제
    public static void delFolder(String path) {
        File file = new File(path);
        if(file.exists() && file.isDirectory()) { //파일이냐? 폴더냐 확인 있다면 true 반환
            File[] fileArr = file.listFiles(); // listFiles() : 디렉토리의 파일목록(디렉토리 포함)을 File배열로 반환
            for(File f : fileArr) {
                if(f.isDirectory()) { //재귀 처리
                    delFolder(f.getPath()); // 폴더면 delFolder() 다시 호출
                } else { //폴더가 아니고 파일이니깐 파일 삭제
                    f.delete();
                }
            }
        }
        file.delete();
    }
}
