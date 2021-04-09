package com.milelu.common.utils.file;

import com.milelu.common.constant.Constants;
import com.milelu.common.core.domain.FileInfo;
import com.milelu.common.utils.CommonUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * 文件处理工具类
 *
 * @author milelu
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";
    private static final String FILE_NAME_FORMAT_STRING = "yyyy/MM-dd/HH-mm-ssSSSS";
    public static final String ORDERFIELD_FILENAME = "fileName";
    private static final String ORDERFIELD_FILESIZE = "fileSize";
    private static final String ORDERFIELD_CREATEDATE = "createDate";
    private static final String ORDERFIELD_MODIFIEDDATE = "modifiedDate";
    private static final FileInfoComparator FILENAME_COMPARATOR = new FileInfoComparator();
    private static final FileInfoComparator FILESIZE_COMPARATOR = new FileInfoComparator(ORDERFIELD_FILESIZE);
    private static final FileInfoComparator CREATEDATE_COMPARATOR = new FileInfoComparator(ORDERFIELD_CREATEDATE);
    private static final FileInfoComparator MODIFIEDDATE_COMPARATOR = new FileInfoComparator(ORDERFIELD_MODIFIEDDATE);

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 获取目录下文件列表
     *
     * @param dirPath
     * @param orderField
     * @return file info list
     */
    public static List<FileInfo> getFileList(String dirPath, String orderField) {
        List<FileInfo> fileList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dirPath))) {
            for (Path entry : stream) {
                Path fileNamePath = entry.getFileName();
                if (null != fileNamePath) {
                    String fileName = fileNamePath.toString();
                    if (!fileName.endsWith(".data") && !Constants.INCLUDE_DIRECTORY.equalsIgnoreCase(fileName)&&!fileName.equals("fragment")) {
                        BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                        fileList.add(new FileInfo(fileName, attrs.isDirectory(), attrs));
                    }
                }
            }
            if (null == orderField) {
                orderField = ORDERFIELD_FILENAME;
            }
            Comparator<FileInfo> comparator;
            switch (orderField) {
                case ORDERFIELD_MODIFIEDDATE:
                    comparator = MODIFIEDDATE_COMPARATOR;
                    break;
                case ORDERFIELD_CREATEDATE:
                    comparator = CREATEDATE_COMPARATOR;
                    break;
                case ORDERFIELD_FILESIZE:
                    comparator = FILESIZE_COMPARATOR;
                    break;
                default:
                    comparator = FILENAME_COMPARATOR;
            }
            Collections.sort(fileList, comparator);
        } catch (IOException e) {
        }
        return fileList;
    }

    public static class FileInfoComparator implements Comparator<FileInfo> {
        private String mode = ORDERFIELD_FILENAME;

        public FileInfoComparator() {
        }

        public FileInfoComparator(String mode) {
            if (null != mode) {
                this.mode = mode;
            }
        }

        @Override
        public int compare(FileInfo o1, FileInfo o2) {
            if (o1.isDirectory() && !o2.isDirectory()) {
                return -1;
            } else if (!o1.isDirectory() && o2.isDirectory()) {
                return 1;
            } else {
                int result = 0;
                switch (mode) {
                    case ORDERFIELD_MODIFIEDDATE:
                        result = o2.getLastModifiedTime().compareTo(o1.getLastModifiedTime());
                        break;
                    case ORDERFIELD_CREATEDATE:
                        result = o2.getCreationTime().compareTo(o1.getCreationTime());
                        break;
                    case ORDERFIELD_FILESIZE:
                        result = Long.compare(o2.getSize(), o1.getSize());
                        break;
                    default:
                        result = o1.getFileName().toLowerCase().compareTo(o2.getFileName().toLowerCase());
                }
                return result;
            }
        }

    }

    /**
     * 写入文件
     *
     * @param filePath
     * @param content
     * @return whether to create successfully
     * @throws IOException
     */
    public static void createFile(String filePath, String content) throws IOException {
        File file = new File(filePath);
        if (CommonUtils.isNotEmptyObject(file)) {
            org.apache.commons.io.FileUtils.writeStringToFile(file, content, Constants.UTF8);
        }
    }
}
