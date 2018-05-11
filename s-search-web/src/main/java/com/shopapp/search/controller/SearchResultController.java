package com.shopapp.search.controller;

import com.shopapp.commons.pojo.SearchResult;
import com.shopapp.search.service.SearchResultService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchResultController {
    @Autowired
    private SearchResultService searchResultService;

    @Value("${SEARCH_RESULT_ROWS}")
    private int rows;

    @RequestMapping("/search")
    public String getSearchResult(String keyword,Model model,
                                  @RequestParam(value = "page",defaultValue = "1") int page) throws Exception {
        keyword = new String(keyword.getBytes("ISO-8859-1"),"UTF-8");
        SearchResult searchResult = searchResultService.findSearchResult(keyword, page, rows);
        model.addAttribute("page",page);
        model.addAttribute("query",keyword);
        model.addAttribute("totalPages",searchResult.getTotalPages());
        model.addAttribute("recourdCount",searchResult.getRecourdCount());
        model.addAttribute("itemList",searchResult.getItemList());
        return "search";
    }
}
