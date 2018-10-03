package com.ly.ems.common.utils.file;


import com.ly.ems.core.exception.EMSBusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by tidus on 2017/12/6.
 */
public class DownloadUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadUtil.class);

    @Value("${admin.backend.temp-dir}")
    private static String tempDir;

    /**
     * 下载文件
     *
     * @param resourcePath 资源路径
     * @param fileName 保存的文件名
     * @throws EMSBusinessException
     * @throws IOException
     */
    public static void downloadResource(String resourcePath, String fileName) throws EMSBusinessException, IOException {
        // 0. prepare
        if (StringUtils.isEmpty(resourcePath)) {
            throw new EMSBusinessException("资源路径为空");
        }

        // 加载模板资源
        Resource resource = new ClassPathResource(resourcePath);

        // 输出
        outputResource(resource, fileName);
    }

    /**
     * 根据模板和数据生成excel并下载
     *
     * @param templatePath 模板路径
     * @param beanParams   数据
     * @param fileName     下载文件名
     * @throws EMSBusinessException,IOException
     */
    public static void downloadExcel(String templatePath, Map beanParams, String fileName) throws EMSBusinessException, IOException {
        // 0. prepare
        if (StringUtils.isEmpty(templatePath)) {
            throw new EMSBusinessException("模板路径为空");
        }

        // 加载模板资源
        Resource resource = new ClassPathResource(templatePath);
        InputStream templateStream = resource.getInputStream();

        // 生成workbook
        Workbook workbook = ExcelUtil.generateWorkbook(templateStream, beanParams);

        // 输出
        outputExcel(workbook, fileName);
    }


    /**
     * 输出文件
     * @param file
     * @param downloadName
     * @throws EMSBusinessException
     */
    private static void outputFile(File file, String downloadName) throws EMSBusinessException {
        if(file == null) {
            return;
        }
        FileInputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(file);
            HttpServletResponse response = getResponse(downloadName, inputStream.available());


            byte[] b = new byte[100];
            outputStream = response.getOutputStream();
            while (inputStream.read(b) != -1) {
                outputStream.write(b);
            }

        } catch (Exception e) {
            LOGGER.error("输出Excel出错", e);
            throw new EMSBusinessException("输出Excel出错");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 输出 resource
     * @param resource
     * @param downloadName
     * @throws EMSBusinessException
     * @throws IOException
     */
    private static void outputResource(Resource resource, String downloadName) throws EMSBusinessException {
        if(resource == null) {
            return;
        }
        InputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            inputStream = resource.getInputStream();
            HttpServletResponse response = getResponse(downloadName, inputStream.available());


            byte[] b = new byte[100];
            outputStream = response.getOutputStream();
            while (inputStream.read(b) != -1) {
                outputStream.write(b);
            }

        } catch (Exception e) {
            LOGGER.error("输出Excel出错", e);
            throw new EMSBusinessException("输出Excel出错");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * 输出 workbook
     * @param workbook
     * @param downloadName
     * @throws EMSBusinessException
     * @throws IOException
     */
    private static void outputExcel(Workbook workbook, String downloadName) throws EMSBusinessException {
        if(workbook == null) {
            return;
        }

        ServletOutputStream outputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);

            HttpServletResponse response = getResponse(downloadName, byteArrayOutputStream.size());
            outputStream = response.getOutputStream();
            workbook.write(outputStream);

        } catch (Exception e) {
            LOGGER.error("输出Excel出错", e);
            throw new EMSBusinessException("输出Excel出错");
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载大excel
     *
     * @param result
     * @param clazz
     * @param fileName
     * @throws IOException
     */
    public static void exportLargetExcel(List result, Class clazz, String fileName) throws IOException {

        Workbook workbook = ExcelUtil.getWorkbook(clazz, result);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);

        HttpServletResponse response = getResponse(fileName, byteArrayOutputStream.size());
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.flush();
        workbook.write(outputStream);
    }

    /**
     * 统一设置 HttpServletResponse
     * @param downloadName
     * @throws UnsupportedEncodingException
     */
    private static HttpServletResponse getResponse(String downloadName, int size) throws UnsupportedEncodingException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();

        // 设置文件名
        String finalFileName = FileUtil.getRequestFileName(request, downloadName);
        String contentDispositionValue = String.format("attachment; filename=\"%s\"", finalFileName);
        response.addHeader("Content-Disposition", contentDispositionValue);

        // 文件类型
        String suffix = FileUtil.getSuffix(finalFileName);
        if(StringUtils.equals(suffix, FileUtil.FILE_SUFFIX_XLS) || StringUtils.equals(suffix, FileUtil.FILE_SUFFIX_XLSX)) {
            response.setContentType("application/vnd.msexcel");
        }

        // 文件大小
        response.setHeader("Content-Length", String.valueOf(size));

        return response;
    }


}
