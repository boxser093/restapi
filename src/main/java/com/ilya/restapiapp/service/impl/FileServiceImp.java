package com.ilya.restapiapp.service.impl;

import com.ilya.restapiapp.model.File;
import com.ilya.restapiapp.repository.FileRepository;
import com.ilya.restapiapp.repository.impl.HibFileRepImpl;
import com.ilya.restapiapp.service.FileService;
import lombok.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;


public class FileServiceImp implements FileService {

    public FileServiceImp() {
        this.fileFepository = new HibFileRepImpl();
    }

    @Getter
    private int fileMaxSize = 1024 * 1000;
    @Getter
    private int memMaxSize = 1024 * 1000;
    private FileRepository fileFepository;

    @Getter
    private final String filePath = "E:/Ilya/IDEAProject/restapi/src/main/resources/uploads/";

    @Override
    public File getById(Integer id) {
        File result = fileFepository.getById(id);
        return result;
    }

    @Override
    public List<File> getAll() {
        List<File> files = fileFepository.getAll();
        return files;
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean result = fileFepository.deleteById(id);
        return result;
    }

    @Override
    public File create(File file) {
        file.setFilePath(filePath);
        File save = fileFepository.save(file);
        return save;
    }

    @Override
    public File update(File file) {
        file = fileFepository.update(file);
        return file;
    }

    public String downLoadFile(HttpServletRequest req) {
        java.io.File file;
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setRepository(new java.io.File(getFilePath()));
        diskFileItemFactory.setSizeThreshold(getMemMaxSize());
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        upload.setSizeMax(getFileMaxSize());
        String fileName = "";
        try {
            List fileItems = upload.parseRequest(req);
            Iterator iterator = fileItems.iterator();
            while (iterator.hasNext()) {
                FileItem fileItem = (FileItem) iterator.next();
                if (!fileItem.isFormField()) {

                    fileName = fileName + fileItem.getName();
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new java.io.File(getFilePath() +
                                fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new java.io.File(getFilePath() +
                                fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fileItem.write(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

}
