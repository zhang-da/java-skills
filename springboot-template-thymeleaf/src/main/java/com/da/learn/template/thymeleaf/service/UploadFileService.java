package com.da.learn.template.thymeleaf.service;

import com.da.learn.template.thymeleaf.bean.UploadFile;
import com.da.learn.template.thymeleaf.repository.UploadFileRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UploadFileService {

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public void save(UploadFile uploadFile) {
//        uploadFile.setId(UUID.randomUUID().toString());
        uploadFileRepository.save(uploadFile);
    }


    public List<UploadFile> search(String param) {
        return uploadFileRepository.findByFilenameLikeOrWriterLikeOrKeywordLikeOrAbstractsLikeOrContentLike(param, param, param, param, param);
    }


    /**
     * 根据搜索结果创建
     *
     * @return
     */
    private UploadFile createFileBean(Map<String, Object> smap, Map<String, HighlightField> hmap) {
        UploadFile file = new UploadFile();
        if (hmap.get("filename") != null) {
            file.setFilename(hmap.get("filename").fragments()[0].toString());
        } else if (smap.get("filename") != null) {
            file.setFilename(smap.get("filename").toString());
        }
        if (hmap.get("writer") != null) {
            file.setWriter(hmap.get("writer").fragments()[0].toString());
        } else if (smap.get("writer") != null) {
            file.setWriter(smap.get("writer").toString());
        }
        if (hmap.get("keyword") != null) {
            file.setKeyword(hmap.get("keyword").fragments()[0].toString());
        } else if (smap.get("keyword") != null) {
            file.setKeyword(smap.get("keyword").toString());
        }
        if (hmap.get("abstracts") != null) {
            file.setAbstracts(hmap.get("abstracts").fragments()[0].toString());
        } else if (smap.get("abstracts") != null) {
            file.setAbstracts(smap.get("abstracts").toString());
        }
        if (hmap.get("content") != null) {
            file.setContent(hmap.get("content").fragments()[0].toString());
        } else if (smap.get("content") != null) {
            file.setContent(smap.get("content").toString());
        }
        if (smap.get("id") != null) {
            file.setId(smap.get("id").toString());
        }
        return file;
    }


    public List<UploadFile> searchHighlight(String param) {
        if (StringUtils.isEmpty(param) || StringUtils.isEmpty(param.trim())) {
            return search(param);
        }

        String preTag = "<font color='#dd4b39'>";
        String postTag = "</font>";

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.boolQuery()
                        .should(QueryBuilders.matchQuery("filename", param))
                        .should(QueryBuilders.matchQuery("keyword", param))
                        .should(QueryBuilders.matchQuery("writer", param))
                        .should(QueryBuilders.matchQuery("abstracts", param))
                        .should(QueryBuilders.matchQuery("content", param))
        )
                //多字段高亮
                .withHighlightFields(
                        new HighlightBuilder.Field("filename").preTags(preTag).postTags(postTag),
                        new HighlightBuilder.Field("keyword").preTags(preTag).postTags(postTag),
                        new HighlightBuilder.Field("writer").preTags(preTag).postTags(postTag),
                        new HighlightBuilder.Field("abstracts").preTags(preTag).postTags(postTag),
                        new HighlightBuilder.Field("content").preTags(preTag).postTags(postTag)
                )
                .build();


        Page<UploadFile> results = elasticsearchTemplate.queryForPage(searchQuery, UploadFile.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<UploadFile> chunk = new ArrayList<>();
                for (SearchHit searchHit : response.getHits().getHits()) {
                    // 普通查询结果
                    Map<String, Object> smap = searchHit.getSourceAsMap();
                    // 高亮查询结果
                    Map<String, HighlightField> hmap = searchHit.getHighlightFields();
                    chunk.add(createFileBean(smap, hmap));
                }
                AggregatedPage<T> result = new AggregatedPageImpl<>((List<T>) chunk, pageable, response.getHits().getTotalHits());
                return result;
            }

            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                List<UploadFile> chunk = new ArrayList<>();
                // 普通查询结果
                Map<String, Object> smap = searchHit.getSourceAsMap();
                // 高亮查询结果
                Map<String, HighlightField> hmap = searchHit.getHighlightFields();
                chunk.add(createFileBean(smap, hmap));
                return null;
            }
        });

        return results.toList();
    }
}
