package com.ly.ems.common.utils.file;


import com.ly.ems.core.exception.EMSBusinessException;
import com.ly.ems.core.exception.EMSRuntimeException;
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
     * 1.下载文件
     *
     * @param filePath 文件路径
     * @param fileName 保存的文件名
     * @throws EMSBusinessException
     * @throws IOException
     */
    public static void downloadFile(String filePath, String fileName) {
        // 0. prepare
        if (StringUtils.isEmpty(filePath)) {
            throw new EMSRuntimeException("文件路径为空");
        }

        // 加载文件
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String targetFilePath = request.getSession().getServletContext().getRealPath(filePath);
        File file = new File(targetFilePath);

        // 输出
        outputFile(file, fileName);
    }

    /**
     * 2.根据模板和数据生成excel并下载
     *
     * @param templatePath 模板路径
     * @param beanParams   数据
     * @param fileName     下载文件名
     * @throws EMSBusinessException,IOException
     */
    public static void downloadExcel(String templatePath, Map beanParams, String fileName) throws EMSBusinessException {
        // 0. prepare
        if (StringUtils.isEmpty(templatePath)) {
            throw new EMSBusinessException("模板路径为空");
        }

        // 加载模板资源
        Resource resource = new ClassPathResource(templatePath);
        InputStream templateStream = null;
        try {
            templateStream = resource.getInputStream();
        } catch (IOException e) {
            LOGGER.error("加载模板失败", e);
            throw new EMSBusinessException("加载模板失败");
        }

        // 生成workbook
        Workbook workbook = ExcelUtil.generateWorkbook(templateStream, beanParams);

        // 输出
        outputExcel(workbook, fileName);
    }

    /**
     * 3.下载大excel（通过导出定义类）
     *
     * @param clazz
     * @param resultList
     * @param fileName
     * @throws IOException
     */
    public static void downloadBigExcel(Class<?> clazz, List<?> resultList, String fileName, Map<String, String> externalParam) {
        // 生成workbook
        Workbook workbook = ExcelUtil.generateBigWorkbook(clazz, resultList, externalParam);
        // 输出
        outputExcelWithTempFile(workbook, fileName);
    }

    /**
     * 1.下载文件
     *
     * @param resourcePath 资源路径
     * @param fileName     保存的文件名
     * @throws EMSBusinessException
     * @throws IOException
     */
    public static void downloadResource(String resourcePath, String fileName) throws EMSBusinessException {
        // 0. prepare
        if (StringUtils.isEmpty(resourcePath)) {
            throw new EMSBusinessException("资源路径为空");
        }

        // 加载资源
        Resource resource = new ClassPathResource(resourcePath);

        // 输出
        outputResource(resource, fileName);
    }


    /**
     * 输出文件
     *
     * @param file
     * @param downloadName
     * @throws EMSBusinessException
     */
    private static void outputFile(File file, String downloadName) {
        if (file == null) {
            return;
        }
        FileInputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(file);
            HttpServletResponse response = getResponse(downloadName, String.valueOf(inputStream.available()));


            byte[] b = new byte[1024];
            outputStream = response.getOutputStream();
            while (inputStream.read(b) != -1) {
                outputStream.write(b);
            }

        } catch (Exception e) {
            LOGGER.error("输出Excel出错", e);
            throw new EMSRuntimeException("输出Excel出错");
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
     *
     * @param workbook
     * @param downloadName
     * @throws EMSBusinessException
     * @throws IOException
     */
    private static void outputExcel(Workbook workbook, String downloadName) throws EMSBusinessException {
        if (workbook == null) {
            return;
        }

        ServletOutputStream outputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);

            HttpServletResponse response = getResponse(downloadName, String.valueOf(byteArrayOutputStream.size()));
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
     * 输出 workbook（以临时文件的方式）
     *
     * @param workbook
     * @param downloadName
     * @throws EMSBusinessException
     * @throws IOException
     */
    private static void outputExcelWithTempFile(Workbook workbook, String downloadName) {
        if (workbook == null) {
            return;
        }
        File tempFile = null;
        try {
            File dir = FileUtil.createTempDir(String.format("%s%s", System.getProperty("java.io.tmpdir"), FileUtil.PATH_EXCEL_TEMP));
            tempFile = File.createTempFile(FileUtil.PREFIX_EXCEL_TEMP, FileUtil.FILE_SUFFIX_XLSX, dir);
            FileOutputStream os = new FileOutputStream(tempFile);
            workbook.write(os);
            os.close();
            workbook.close();

            outputFile(tempFile, downloadName);
        } catch (Exception e) {
            LOGGER.error("输出Excel出错", e);
            throw new EMSRuntimeException("输出Excel出错");
        } finally {
            if (tempFile != null) {
                boolean deleted = tempFile.delete();
                if(!deleted) {
                    LOGGER.error("临时文件删除失败：%s",tempFile.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 输出 resource
     *
     * @param resource
     * @param downloadName
     * @throws EMSBusinessException
     * @throws IOException
     */
    private static void outputResource(Resource resource, String downloadName) throws EMSBusinessException {
        if (resource == null) {
            return;
        }
        InputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            inputStream = resource.getInputStream();
            HttpServletResponse response = getResponse(downloadName, String.valueOf(inputStream.available()));


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
     * 统一设置 HttpServletResponse
     *
     * @param downloadName
     * @throws UnsupportedEncodingException
     */
    private static HttpServletResponse getResponse(String downloadName, String size) throws UnsupportedEncodingException {
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
        if (StringUtils.equals(suffix, FileUtil.FILE_SUFFIX_XLS) || StringUtils.equals(suffix, FileUtil.FILE_SUFFIX_XLSX)) {
            response.setContentType("application/vnd.msexcel");
        }

        // 文件大小
        response.setHeader("Content-Length", size);

        return response;
    }


}
