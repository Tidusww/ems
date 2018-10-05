package com.ly.ems.common.utils.file;

import com.ly.ems.core.exception.EMSBusinessException;
import com.ly.ems.core.exception.EMSRuntimeException;
import com.ly.ems.model.common.constant.FileTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by tidus on 2017/12/6.
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static final String PREFIX_EXCEL_TEMP = "export_temp_";
    public static final String PATH_EXCEL_TEMP = "/excel/";

    public static final String FILE_SUFFIX_XLS = ".xls";
    public static final String FILE_SUFFIX_XLSX = ".xlsx";

    /**
     * @param file          文件
     * @param limitSize     Byte
     * @param supportSuffix [".jpg", ".png", ".jpeg", ".gif"]
     * @return
     */
    public static String validateFile(MultipartFile file, Integer limitSize, List<String> supportSuffix) {
        String message = "";
        if (file == null || file.isEmpty()) {
            message = "文件不能为空";
            return message;
        }

        if (file.getSize() > limitSize) {
            message = "文件大小不能超过" + getFormattedSizeString(limitSize);
            return message;
        }

        String suffix = getSuffix(file.getOriginalFilename());
        if (!supportSuffix.contains(suffix) && !supportSuffix.contains(suffix.toUpperCase())) {
            message = "不支持的文件类型";
            return message;
        }

        return message;
    }

    /**
     * 将字节转换成更大的单位
     *
     * @param size 字节
     * @return
     */
    private static String getFormattedSizeString(Integer size) {
        List<String> sizeUnits = Arrays.asList("Byte", "KB", "MB", "GB");

        int unitIndex = 0;
        double formatSize = size;
        while (formatSize >= 1024.0 && unitIndex < sizeUnits.size() - 1) {
            unitIndex += 1;
            formatSize = formatSize / 1024.0;
        }

        return String.format("%.2f%s", formatSize, sizeUnits.get(unitIndex));
    }

    /**
     * 根据文件名 去掉后缀名
     *
     * @param name 文件名
     * @return "abcd"
     */
    public static String getFileName(String name) {
        String fileName;
        int suffixIndex = name.lastIndexOf(".");
        if (suffixIndex == -1) {
            return name;
        }
        fileName = name.substring(0, suffixIndex);
        return fileName;
    }

    /**
     * 根据文件名 获取后缀名
     *
     * @param name 文件名
     * @return ".png"
     */
    public static String getSuffix(String name) {
        String suffix;
        int suffixIndex = name.lastIndexOf(".");
        if (suffixIndex == -1) {
            return "";
        }
        suffix = name.substring(suffixIndex, name.length()).toLowerCase();
        return suffix;
    }

    /**
     * 根据文件名 生成唯一文件名
     *
     * @param name
     * @return timestamp_uuid.png
     */
    public static String getFileUniqueName(String name) {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        Long current = System.currentTimeMillis();
        String fileName = getFileName(name);
        String suffix = getSuffix(name);

        String uniqueName = fileName + "_" + current.toString() + "_" + uuid;
        if (!StringUtils.isEmpty(suffix)) {
            uniqueName = uniqueName + suffix;
        }
        return uniqueName;
    }

    /**
     * 根据名字 获取文件类型
     *
     * @param name
     * @return FileType
     */
    public static FileTypeEnum getFileType(String name) {
        List<String> imageSuffixes = Arrays.asList(new String[]{".jpg", ".png", ".jpeg", ".gif"});
        List<String> audioSuffixes = Arrays.asList(new String[]{".mp3"});
        List<String> videoSuffixes = Arrays.asList(new String[]{".mp4"});

        String suffix = getSuffix(name);
        if (imageSuffixes.contains(suffix)) {
            return FileTypeEnum.IMAGE;
        }
        if (audioSuffixes.contains(suffix)) {
            return FileTypeEnum.AUDIO;
        }
        if (videoSuffixes.contains(suffix)) {
            return FileTypeEnum.VIDEO;
        }

        return FileTypeEnum.UNKNOWN;
    }

    /**
     * 根据请求头返回不同编码的文件名
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @Author ZhongTianCai
     */
    public static String getRequestFileName(HttpServletRequest request, String originFileName)
            throws UnsupportedEncodingException {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            // win10 ie edge 浏览器 和其他系统的ie
            return URLEncoder.encode(originFileName, "UTF-8");
        }
        return new String(originFileName.getBytes("UTF-8"), "ISO-8859-1");
    }

    /**
     * 生成临时文件夹
     * @param dirFullPath
     * @return
     * @throws IOException
     */
    public static File createTempDir(String dirFullPath) throws IOException {
        File tempDir = new File(dirFullPath);
        boolean deleted = true;
        boolean created = true;

        if (!tempDir.exists()) {
            created = tempDir.mkdir();
        }else {
            if(!tempDir.isDirectory()) {
                deleted = tempDir.delete();
                created = tempDir.mkdir();
            }
        }

        if (!deleted || !created) {
            throw new IOException(String.format("创建临时文件夹失败：%s", dirFullPath));
        }
        return tempDir;
    }

    /**
     * 删除文件
     *
     * @param request
     * @param filePath
     */
    public static boolean deleteFile(HttpServletRequest request, String filePath) {
        String fullPath = request.getSession().getServletContext().getRealPath(filePath);
        return deleteFile(fullPath);
    }

    /**
     * 删除文件
     *
     * @param fileFullPath
     */
    public static boolean deleteFile(String fileFullPath) {
        File file = new File(fileFullPath);
        if (!file.exists()) {
            return false;
        }
        if (!file.isFile()) {
            return false;
        }
        return file.delete();
    }

    /**
     * 根据workbook生成文件，并返回下载地址
     * @param workbook
     * @param fileName
     * @return
     */
    public static String generateFileByWorkbook(Workbook workbook, String fileName) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String download_path = "/excel/export/" + getFileUniqueName(fileName);
        String targetFilePath = request.getSession().getServletContext().getRealPath(download_path);

        try {
            File tempFile = new File(targetFilePath);
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(targetFilePath));
            workbook.write(os);
            os.flush();
            os.close();

        } catch (Exception e) {
            LOGGER.error("生成文件失败", e);
            deleteFile(request, download_path);
            throw new EMSRuntimeException("生成Excel出错");
        }
        return download_path;
    }

}
