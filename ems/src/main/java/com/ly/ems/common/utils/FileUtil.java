package com.ly.ems.common.utils;

import com.ly.ems.model.common.constant.FileTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by tidus on 2017/12/6.
 */
public class FileUtil {



    /**
     *
     * @param file
     * @param limitSize Byte
     * @param supportSuffix [".jpg", ".png", ".jpeg", ".gif"]
     * @return
     */
    public static String validateFile(MultipartFile file, Integer limitSize, List<String> supportSuffix) {
        String message = "";
        if (file == null || file.isEmpty()) {
            message = "文件不能为空";
        }

        if (file.getSize() > limitSize) {
            message = "文件大小不能超过"+getFormattedSizeString(limitSize);
        }

        String suffix = getSuffix(file.getOriginalFilename());
        if (!supportSuffix.contains(suffix) && !supportSuffix.contains(suffix.toUpperCase())) {
            message = "不支持的文件类型";
        }

        return message;
    }

    /**
     * 将字节转换成更大的单位
     * @param size
     * @return
     */
    public static String getFormattedSizeString(Integer size) {
        List<String> sizeUnits = Arrays.asList(new String[] { "Byte", "KB", "MB", "GB" });

        int unitIndex = 0;
        double formatSize = size;
        while (formatSize >= 1024.0 && unitIndex < sizeUnits.size()-1) {
            unitIndex += 1;
            formatSize = formatSize / 1024.0;
        }

        return String.format("%.2f%s", formatSize, sizeUnits.get(unitIndex));

    }

    /**
     * 根据文件名 获取后缀名
     *
     * @param name
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
     * @param name
     * @return timestamp_uuid.png
     */
    public static String getFileUniqueName(String name) {
        UUID uuid = UUID.fromString(name);
        Long current = new Long(System.currentTimeMillis());
        String suffix = getSuffix(name);

        String uniqueName = current.toString() + "_" + uuid.toString();
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
        List<String> imageSuffixes = Arrays.asList(new String[] { ".jpg", ".png", ".jpeg", ".gif" });
        List<String> audioSuffixes = Arrays.asList(new String[] { ".mp3" });
        List<String> videoSuffixes = Arrays.asList(new String[] { ".mp4" });

        String suffix = getSuffix(name);
        if(imageSuffixes.contains(suffix)) {
            return FileTypeEnum.IMAGE;
        }
        if(audioSuffixes.contains(suffix)){
            return FileTypeEnum.AUDIO;
        }
        if(videoSuffixes.contains(suffix)){
            return FileTypeEnum.VIDEO;
        }

        return FileTypeEnum.UNKNOWN;
    }

}
