package org.zerobs.polla.utilities;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.zerobs.polla.entities.db.User;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class Utils {
    private Utils() {
    }

    public static <T> List<T> cleanList(List<T> list) {
        return ListUtils.emptyIfNull(list).stream().filter(Objects::nonNull).collect(toList());
    }

    public static List<String> cleanStringList(List<String> list) {
        return ListUtils.emptyIfNull(list).stream().filter(StringUtils::isNotBlank).collect(toList());
    }

    public static Jwt getPrincipal() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUserId() {
        return new User(getPrincipal()).getId();
    }
}
