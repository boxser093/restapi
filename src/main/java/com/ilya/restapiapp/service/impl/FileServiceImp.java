package com.ilya.restapiapp.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilya.restapiapp.model.File;
import com.ilya.restapiapp.repository.FileRepository;
import com.ilya.restapiapp.repository.impl.HibFileRepImpl;
import com.ilya.restapiapp.service.FileService;
import lombok.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class FileServiceImp implements FileService {
    private final Logger logger = LoggerFactory.getLogger(FileServiceImp.class);

    private String loggerMessage;

    public FileServiceImp() {
        this.fileFepository = new HibFileRepImpl();
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Getter
    private int fileMaxSize = 1024 * 1000;
    @Getter
    private int memMaxSize = 1024 * 1000;
    private FileRepository fileFepository;
    private Gson gson;

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
            logger.info("--- ## Start file upload process ## --- ");
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
            logger.info("--- ## End file upload process ## --- ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("--- ### File name upload ### ---");
        return fileName;
    }
    public String toGson(File file) {
        return gson.toJson(file);
    }
    public File fromJson(String bodyRequest) {
        return gson.fromJson(bodyRequest, File.class);
    }
    public String getBody(HttpServletRequest req) throws IOException {
        BufferedReader br = req.getReader();
        String str, result = "";
        while ((str = br.readLine()) != null) {
            result += str;
        }
        return result;
    }


}
