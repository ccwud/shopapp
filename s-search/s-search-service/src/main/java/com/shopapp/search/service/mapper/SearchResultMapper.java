package com.shopapp.search.service.mapper;


import com.shopapp.commons.pojo.SearchItem;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SearchResultMapper {
     List<SearchItem> findItemList();
}
