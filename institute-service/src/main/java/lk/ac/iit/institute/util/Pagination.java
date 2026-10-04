package lk.ac.iit.institute.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public final class Pagination {
    private Pagination() {
    }

    public static <T> Page<T> page(List<T> values, Pageable pageable) {
        int start = (int) Math.min(pageable.getOffset(), values.size());
        int end = (int) Math.min((long) start + pageable.getPageSize(), values.size());
        return new PageImpl<>(values.subList(start, end), pageable, values.size());
    }
}
