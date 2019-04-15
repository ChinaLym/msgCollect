package com.msgc.utils.csv;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CsvUtil {

    private static int DEFAULT_IGNORE_LINE = 0;

    private static String DEFAULT_SEPARATOR = ",";

    public static List<List<String>> read(String fileName) throws IOException {
        return read(fileName, DEFAULT_IGNORE_LINE, DEFAULT_SEPARATOR);
    }

    /**
     * 读取指定位置的 csv文件
     * @param fileName 文件全路径
     * @return 读取到的内容
     * @throws IOException
     */
    public static List<List<String>> read(String fileName, int ignoreLine, String separator) throws IOException {
        DataInputStream  din = new DataInputStream (new FileInputStream(fileName));
        BufferedReader reader = new BufferedReader(new InputStreamReader(din,"utf-8"));
        for (int i = 0; i < ignoreLine; i++) {
            reader.readLine();
        }
        List<List<String>> resultList = new LinkedList<>();
        String line;
        while((line=reader.readLine())!=null) {
            resultList.add(Arrays.asList(line.split(separator)));
        }
        return resultList;
    }

    /**
     * CSV文件生成方法
     * @param head
     * @param dataList
     * @param outPutPath
     * @param filename
     * @return
     */
    public static File write(List<Object> head, List<List<Object>> dataList,
                                     String outPutPath, String filename) {

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "GB2312"), 1024);
            // 写入文件头部
            writeRow(head, csvWtriter);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    /**
     * 写一行数据方法
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }
}
