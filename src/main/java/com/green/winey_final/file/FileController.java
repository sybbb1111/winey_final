package com.green.winey_final.file;


import com.green.winey_final.file.model.FileUpdDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "url이미지 로컬에 저장")
@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor
public class FileController {
    private final FileService SERVICE;

    @GetMapping
    public ResponseEntity<Resource> download(FileUpdDto dto) {
        Resource file = SERVICE.fileLoad(dto);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }


}
