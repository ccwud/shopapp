package com.shopapp.search.service.dao;

import com.shopapp.commons.pojo.SearchItem;
import com.shopapp.commons.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchResultDao {
    @Autowired
    private SolrServer solrServer;

    public SearchResult search(SolrQuery query) throws SolrServerException {
        QueryResponse queryResponse = solrServer.query(query);
        SolrDocumentList documentList = queryResponse.getResults();
        SearchResult searchResult = new SearchResult();
        searchResult.setRecourdCount(documentList.getNumFound());
        List<SearchItem> itemList = new ArrayList<>();
        //取高亮的结果
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        for (SolrDocument result:documentList) {
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String) result.get("id"));
            searchItem.setCategory_name((String) result.get("item_category_name"));
            searchItem.setSell_point((String) result.get("item_sell_point"));
            searchItem.setImage((String) result.get("item_image"));
            searchItem.setPrice((Long) result.get("item_price"));
            List<String> hResult = highlighting.get(result.get("id")).get("item_title");
            if (hResult!=null&&hResult.size()>0){
                searchItem.setTitle(hResult.get(0));
            }else {
                searchItem.setTitle((String) result.get("item_title"));
            }
            itemList.add(searchItem);
        }
        searchResult.setItemList(itemList);
        return searchResult;
    }
}
