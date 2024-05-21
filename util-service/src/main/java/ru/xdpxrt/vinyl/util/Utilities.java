package ru.xdpxrt.vinyl.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Utilities {
    public static PageRequest fromSizePage(int from, int size) {
        return PageRequest.of(from / size, size);
    }

    public static PageRequest fromSizePage(int from, int size, String sort) {
        return PageRequest.of(from / size, size, Sort.by(sort));
    }
}