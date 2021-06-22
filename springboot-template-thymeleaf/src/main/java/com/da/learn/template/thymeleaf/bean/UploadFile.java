package com.da.learn.template.thymeleaf.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.web.multipart.MultipartFile;

@Data
@Document(indexName = "files")
public class UploadFile {
    @Id
    private String id;
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String filename;
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String keyword;
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String writer;
    private String date;
    private MultipartFile file;
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String abstracts;
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String content;


    public String toText() {
        return "文件名：" +
                this.filename +
                "\n" +
                "作者：" +
                this.writer +
                "\n" +
                "关键词：" +
                this.keyword +
                "\n" +
                "摘要：" +
                this.abstracts +
                "\n" +
                "内容：" +
                this.content +
                "\n";
    }
}
