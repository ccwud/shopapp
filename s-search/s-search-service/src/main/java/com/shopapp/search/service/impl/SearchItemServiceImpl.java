package com.shopapp.search.service.impl;

import com.shopapp.commons.pojo.SearchItem;
import com.shopapp.commons.utils.TaotaoResult;
import com.shopapp.search.service.SearchItemService;
import com.shopapp.search.service.mapper.SearchResultMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {
    @Autowired
    private SearchResultMapper searchResultMapper;
    @Autowired
    private SolrServer solrServer;
    @Override
    public TaotaoResult getItemList() {
        List<SearchItem> itemList = searchResultMapper.findItemList();
        try {
            for (SearchItem item : itemList) {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_sell_point", item.getSell_point());
                document.addField("item_price", item.getPrice());
                document.addField("item_image", item.getImage());
                document.addField("item_category_name", item.getCategory_name());
                solrServer.add(document);
            }
            solrServer.commit();
            return TaotaoResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "添加失败");
        }
    }
}
