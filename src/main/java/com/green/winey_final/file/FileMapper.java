package com.green.winey_final.file;

import com.green.winey_final.file.model.FileEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    FileEntity getFileEntityById(int id);
    void updPic(FileEntity entity);
    int count();
}
