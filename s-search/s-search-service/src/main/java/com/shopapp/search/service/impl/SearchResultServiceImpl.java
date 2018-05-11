package com.shopapp.search.service.impl;

import com.shopapp.commons.pojo.SearchResult;
import com.shopapp.search.service.SearchResultService;
import com.shopapp.search.service.dao.SearchResultDao;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.testng.annotations.Test;

@Service
public class SearchResultServiceImpl implements SearchResultService {
    @Autowired
    private SearchResultDao searchResultDao;
    @Value("${DF_DEFAULT_FILED}")
    private String DF_DEFAULT_FILED;
    @Override
    public SearchResult findSearchResult(String keyword,int page,int rows) throws SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setQuery(keyword);
        query.set("df",DF_DEFAULT_FILED);
        query.setHighlight(true);
        int start = (page-1)*rows;
        query.setStart(start);
        query.setRows(rows);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style='color : red'>");
        query.setHighlightSimplePost("</em>");
        SearchResult result = searchResultDao.search(query);
        int totalPages = (int) (result.getRecourdCount()%rows==0?result.getRecourdCount()/rows:(result.getRecourdCount()/rows)+1);
        result.setTotalPages(totalPages);
        return result;
    }

}
