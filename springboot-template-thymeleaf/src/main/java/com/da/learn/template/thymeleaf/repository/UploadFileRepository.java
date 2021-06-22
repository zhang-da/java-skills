package com.da.learn.template.thymeleaf.repository;

import com.da.learn.template.thymeleaf.bean.UploadFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileRepository extends ElasticsearchRepository<UploadFile, String> {

    List<UploadFile> findByFilenameLikeOrWriterLikeOrKeywordLikeOrAbstractsLikeOrContentLike(String filename, String writer, String keyword, String abstracts, String content);

}
