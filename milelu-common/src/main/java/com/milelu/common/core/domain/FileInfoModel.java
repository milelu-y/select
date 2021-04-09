package com.milelu.common.core.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

/**
 *
 * FileInfo 文件信息封装类
 *
 */
@Data
@Accessors(chain = true)
public class FileInfoModel {

    public String fileName;
    public boolean directory;
    public Date lastModifiedTime;
    public Date lastAccessTime;
    public Date creationTime;
    public long size;

    public FileInfoModel(){

    }

    /**
     * @param fileName
     * @param directory
     * @param attrs
     */
    public FileInfoModel(String fileName, boolean directory, BasicFileAttributes attrs) {
        this.fileName = fileName;
        this.directory = directory;
        this.lastModifiedTime = new Date(attrs.lastModifiedTime().toMillis());
        this.lastAccessTime = new Date(attrs.lastAccessTime().toMillis());
        this.creationTime = new Date(attrs.creationTime().toMillis());
        this.size = attrs.size();
    }
}
