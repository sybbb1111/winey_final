package com.green.winey_final.file;


import com.green.winey_final.file.model.FileEntity;
import com.green.winey_final.file.model.FileUpdDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.dir}") //롬복 쓰면 안되고 스프링꺼 써야함
    private String fileDir;
    private final FileMapper MAPPER;

    public Resource fileLoad(FileUpdDto dto) {
        String urlPath = "https://images.vivino.com/thumbs/";
        int count = MAPPER.count();

        for (int i = 1; i <= count; i++) {
            FileEntity entity = MAPPER.getFileEntityById(i);
            entity.setProductId(dto.getProductId());

            String path = entity.getImagePath();
            String fullPath = "https:" + path;
            String fileName = fullPath.replace(urlPath, "");

            String file_ext = fileName.substring(
                    fileName.lastIndexOf('.') + 1,
                    fileName.length());
            try {
                String realUrl = urlPath + fileName;
                BufferedImage image = ImageIO.read(new URL(realUrl));
                String savedFilePath = fileDir + "wine/" + i + "/" + fileName;
                String dbFilePath = "wine/" + i + "/" + fileName;

                File dic = new File(savedFilePath);
                if (!dic.exists()) {
                    dic.mkdirs();
                }
                ImageIO.write(image, file_ext, new File(savedFilePath));
                log.info("{}, 다운로드 완료", fileName);

                // DB에 파일 경로 업데이트
                entity.setProductId(Long.valueOf(i)); //Long > int 변환...
                entity.setImagePath(dbFilePath);
                MAPPER.updPic(entity);

                log.info("{}, 경로 업데이트 완료", entity.getImagePath());

            } catch (Exception e) {
                e.printStackTrace();
                log.info("failed");
            }
        }
        return null;
    }

}
