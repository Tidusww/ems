package com.ly.ems.common.utils;

import com.ly.ems.core.exception.EMSBusinessException;
import com.ly.ems.core.exception.EMSRuntimeException;
import com.ly.ems.model.common.constant.FileTypeEnum;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        Long current = new Long(System.currentTimeMillis());
        String suffix = getSuffix(name);

        String uniqueName = current.toString() + "_" + uuid;
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

    /**
     * 下载文件
     *
     * @param filePath
     *            下载路径
     * @param fileName
     *            保存的文件名
     * @param request
     * @param response
     * @throws EMSBusinessException
     * @throws IOException
     */
    public static void downloadFile(String filePath, String fileName, HttpServletRequest request,
                                    HttpServletResponse response) throws EMSBusinessException, IOException {
        // 0. prepare
        if (StringUtils.isEmpty(filePath)) {
            throw new EMSBusinessException("下载路径为空");
        }
        String downloadPath = request.getSession().getServletContext().getRealPath(filePath);
        File downloadFile = new File(downloadPath);
        if (!downloadFile.exists()) {
            throw new EMSBusinessException("下载文件不存在:" + filePath);
        }
        if (StringUtils.isEmpty(fileName)) {
            fileName = "未命名文件" + getSuffix(filePath);
        }

        // 1. 输出
        outputFile(filePath, fileName);
    }

    /**
     * 导出excel
     *
     * @param templetPath
     *            模板路径
     * @param beanParams
     *            数据
     * @param fileName
     *            下载文件名
     * @param request
     * @param response
     * @throws EMSBusinessException,IOException
     */
    public static void downloadExcel(String templetPath, Map beanParams, String fileName, HttpServletRequest request,
                                     HttpServletResponse response) throws EMSBusinessException, IOException {
        // 0. prepare
        if (StringUtils.isEmpty(templetPath)) {
            throw new EMSBusinessException("模板路径为空");
        }
        String srcFilePath = request.getSession().getServletContext().getRealPath(templetPath);
        File srcFile = new File(srcFilePath);
        if (!srcFile.exists()) {
            throw new EMSBusinessException("模板文件不存在: " + srcFilePath);
        }
        if (StringUtils.isEmpty(fileName)) {
            fileName = "未命名文件.xlsx";
        }

        // 1. 生成
        XLSTransformer transformer = new XLSTransformer();
        final String DOWNLOAD_EXCEL_TEMP_FILE_PATH = "/export/" + getFileUniqueName("temp.xlsx");
        String destFilePath = request.getSession().getServletContext().getRealPath(DOWNLOAD_EXCEL_TEMP_FILE_PATH);
        try {
            File tempFile = new File(destFilePath);
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }
            long s = System.currentTimeMillis();
            transformer.transformXLS(srcFilePath, beanParams, destFilePath);
            System.out.println("导出耗时" + (System.currentTimeMillis() - s));
        } catch (Exception e) {
            deleteFile(request, DOWNLOAD_EXCEL_TEMP_FILE_PATH);
            throw new EMSBusinessException("生成Excel出错");
        }

        // 2. 输出
        outputFile(DOWNLOAD_EXCEL_TEMP_FILE_PATH, fileName);
    }

    /**
     * 根据workbook导出excel
     *
     * @param workbook
     *            excel workbook
     * @param fileName
     *            下载文件名
     * @throws EMSBusinessException,IOException
     */
    public static void downloadExcel(Workbook workbook, String fileName) throws EMSBusinessException, IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        if (StringUtils.isEmpty(fileName)) {
            fileName = "未命名文件.xlsx";
        }
        // 0. prepare
        if (workbook == null) {
            throw new EMSBusinessException("excel工作簿为空");
        }


        // 1. 输出到本地临时文件
        final String DOWNLOAD_EXCEL_TEMP_FILE_PATH = generateFile(workbook, request,"temp.xlsx");

        // 2. 输出
        outputFile(DOWNLOAD_EXCEL_TEMP_FILE_PATH, fileName);
    }

    public static String generateFile(Workbook workbook, HttpServletRequest request,String fileName) {
        String DOWNLOAD_EXCEL_TEMP_FILE_PATH = "/export/" + getFileUniqueName(fileName);
        String destFilePath = request.getSession().getServletContext().getRealPath(DOWNLOAD_EXCEL_TEMP_FILE_PATH);

        try {
            File tempFile = new File(destFilePath);
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(destFilePath));
            workbook.write(os);
            os.flush();
            os.close();

        } catch (Exception e) {
            deleteFile(request, DOWNLOAD_EXCEL_TEMP_FILE_PATH);
            throw new EMSBusinessException("生成Excel出错");
        }
        return DOWNLOAD_EXCEL_TEMP_FILE_PATH;
    }

    /**
     * 输出文件
     */
    public static void outputFile(String downloadFilePath, String fileName) throws EMSBusinessException, IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();

        FileInputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            String finalFileName = getFileName(request, fileName);

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", finalFileName);
            response.addHeader(headerKey, headerValue);
            response.setContentType("application/vnd.msexcel");

            String downloadPath = request.getSession().getServletContext().getRealPath(downloadFilePath);
            inputStream = new FileInputStream(downloadPath);
            response.addHeader("Content-Length", String.valueOf(inputStream.getChannel().size()));

            byte[] b = new byte[100];
            outputStream = response.getOutputStream();
            while (inputStream.read(b) != -1) {
                outputStream.write(b);
            }

        } catch (Exception e) {
            throw new EMSBusinessException("输出Excel出错");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            //先别删除
            //deleteFile(request, downloadFilePath);
        }
    }

    /**
     * 根据模板和数据生成excel并返回
     *
     * @param templetPath
     *            模板路径
     * @param beanParams
     *            数据
     * @throws EMSBusinessException,IOException
     */
    public static Workbook generateExcel(String templetPath, Map beanParams) throws EMSBusinessException, IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        // 0. prepare
        if (StringUtils.isEmpty(templetPath)) {
            throw new EMSBusinessException("模板路径为空");
        }
        String srcFilePath = request.getSession().getServletContext().getRealPath(templetPath);
        File srcFile = new File(srcFilePath);
        if (!srcFile.exists()) {
            throw new EMSBusinessException("模板文件不存在: " + srcFilePath);
        }

        // 1. 生成workbook
        Workbook workbook;
        XLSTransformer transformer = new XLSTransformer();
        try {
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(srcFilePath));
            workbook = transformer.transformXLS(is, beanParams);
            is.close();
        } catch (Exception e) {
            throw new EMSBusinessException("生成Excel出错");
        }

        return workbook;

    }

    /**
     * 根据请求头返回不同编码的文件名
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @Author ZhongTianCai
     */
    public static String getFileName(HttpServletRequest request, String originFileName) throws UnsupportedEncodingException {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            // win10 ie edge 浏览器 和其他系统的ie
            return URLEncoder.encode(originFileName, "UTF-8");
        }
        return new String(originFileName.getBytes("UTF-8"), "ISO-8859-1");
    }

    /**
     * 删除文件
     * @param request
     * @param filePath
     */
    public static boolean deleteFile(HttpServletRequest request, String filePath){
        String fullPath = request.getSession().getServletContext().getRealPath(filePath);
        return deleteFile(fullPath);
    }
    /**
     * 删除文件
     * @param fileFullPath
     */
    public static boolean deleteFile(String fileFullPath){
        File file = new File(fileFullPath);
        if(!file.exists()){
            return false;
        }
        if(!file.isFile()){
            return false;
        }
        return file.delete();
    }

    public static boolean getDataFromExcelFile(InputStream excelStream, InputStream xmlStream, Map beans) {
        XLSReader mainReader = null;
        try {
            mainReader = ReaderBuilder.buildFromXML(xmlStream);
            XLSReadStatus readStatus = mainReader.read(excelStream, beans);
            return readStatus.isStatusOK();
        } catch (Exception e) {
            e.printStackTrace();
            throw new EMSRuntimeException("从Excel读取数据出错");
        }
    }

}
