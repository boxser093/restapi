package com.ilya.restapiapp.mappers;

import com.ilya.restapiapp.dto.FileDto;
import com.ilya.restapiapp.model.File;
import java.util.List;
import java.util.stream.Collectors;


public class FIleMapper implements GenericMapper<File, FileDto>{

    @Override
    public FileDto map(File file) {
        if(file == null) return null;
        return FileDto.builder()
                .id(file.getId())
                .name(file.getName())
                .filePath(file.getFilePath())
                .build();
    }

    @Override
    public List<FileDto> map(List<File> files) {
        if(files==null || files.size()==0) return null;
        return files.stream().map(this::map).collect(Collectors.toList());
    }
}
