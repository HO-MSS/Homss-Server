package com.homss.server.common.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PageConverter<T> {

    public Page<T> toPage(List<T> content, long totalSize, Pageable pageable) {
        return new PageImpl<T>(content, pageable, totalSize);
    }

}
