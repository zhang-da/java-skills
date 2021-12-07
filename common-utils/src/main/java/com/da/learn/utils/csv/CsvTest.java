package com.da.learn.utils.csv;


import com.opencsv.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvTest {
    public static void main(String[] args) throws Exception {

        CSVParser csvParser = new CSVParserBuilder().withSeparator('|').build();
        CSVReader csvReader = new CSVReaderBuilder(Files.newBufferedReader(Paths.get("D:\\Temp\\test2.txt"), StandardCharsets.UTF_8)).withCSVParser(csvParser).build();
        String lines[];
        while ((lines = csvReader.readNext()) != null) {
            Arrays.asList(lines).forEach(System.out::println);
        }


        List<String[]> datas = new ArrayList<>();
        datas.add(new String[]{"姓名", "性别", "年龄"});
        datas.add(new String[]{"云天明", "男", "17"});
        datas.add(new String[]{"韩菱纱", "女", "16"});
        CSVWriter writer = new CSVWriter(Files.newBufferedWriter(Paths.get("D:\\Temp\\test3.txt"), StandardCharsets.UTF_8),
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.NO_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        writer.writeAll(datas);
        writer.flush();


        CsvToBean<Test> csvToBean = new CsvToBeanBuilder<Test>(Files.newBufferedReader(Paths.get("D:\\Temp\\test2.txt"), StandardCharsets.UTF_8))
                .withType(Test.class)
                .withSeparator('|').build();
        List<Test> list = csvToBean.parse();
        list.forEach(System.out::println);




        StatefulBeanToCsv<Test> beanToCsv = new StatefulBeanToCsvBuilder<Test>(Files.newBufferedWriter(Paths.get("D:\\Temp\\test4.txt"), StandardCharsets.UTF_8))
                .withSeparator('|').withQuotechar('\u0000').build();
        beanToCsv.write(list);

        Writer writer2 = new FileWriter("D:\\Temp\\test5.txt");
        StatefulBeanToCsv beanToCsv2 = new StatefulBeanToCsvBuilder(writer2).build();
        beanToCsv2.write(list);
        writer2.close();

        Writer writer3 = new StringWriter();
        StatefulBeanToCsv beanToCsv3 = new StatefulBeanToCsvBuilder(writer3).build();
        beanToCsv3.write(list);
        writer3.close();

    }
}
