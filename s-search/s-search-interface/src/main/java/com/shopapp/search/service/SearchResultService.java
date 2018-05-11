package com.shopapp.search.service;

import com.shopapp.commons.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;

public interface SearchResultService {
    SearchResult findSearchResult(String keyword,int page,int rows) throws SolrServerException;

}
