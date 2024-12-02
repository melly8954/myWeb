package com.melly.myweb.board.file;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileCtrlService {
//    @Value("${spring.servlet.multipart.location}")
    private String uploadDir = "/home/files";

    private void checkDirectory(String directory) throws IOException {
        // Path: 파일 시스템의 경로를 나타내는 객체
        // Paths: Path 객체를 생성하는 정적 메서드를 제공하는 클래스
        // .get(): Paths 클래스의 정적 메서드로, 문자열로 된 경로를 Path 객체로 변환하는 메서드
        Path path = Paths.get(directory);
        if ( !Files.exists(path) ) {
            // 파일을 저장할 디렉토리가 존재하는지 확인하고, 없으면 해당 디렉토리를 생성
            Files.createDirectories(path);
        }
    }

    public Boolean saveFile(MultipartFile file, String tbl, String destFileName) throws IOException {
        if ( tbl == null || tbl.isEmpty() || file == null ) {
            return false;
        }
        this.checkDirectory(uploadDir + "/" + tbl);
        // destFileName은 저장할 파일의 이름, copy로 저장
        Files.copy( file.getInputStream(), Path.of(uploadDir + "/" + tbl + "/" + destFileName) );
        return true;
    }

    public byte[] downloadFile(String tbl, String uniqName, String fileType){
        // 파일을 읽어서 byte 배열로 반환
        // 파일을 직접 반환하는 방식, HTTP 응답에 파일을 포함시키는 방법과는 다르게 파일 내용을 byte[]로 가져온다.
        byte[] bytes = null;
        try{
            this.checkDirectory(uploadDir + "/" + tbl);
            Path path = Paths.get(uploadDir + "/" + tbl + "/" + uniqName);
            bytes = Files.readAllBytes(path);
        }catch (IOException ex){
            log.error(ex.toString());
        }
        return bytes;
    }

    public Boolean deleteFile(String tbl, String uniqName, String fileType){
        try{
            this.checkDirectory(uploadDir + "/" + tbl);
            Path path = Paths.get(uploadDir + "/" + tbl + "/" + uniqName);
            Files.delete(path);
        }catch (IOException ex){
            log.error(ex.toString());
            return false;
        }
        return true;
    }
}
