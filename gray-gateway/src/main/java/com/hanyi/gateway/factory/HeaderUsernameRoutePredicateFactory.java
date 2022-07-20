package com.hanyi.gateway.factory;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * <p>
 * 自定义断言匹配规则，需要在yml文件中配置predicates相关属性才能生效，如：HeaderUsername=Jack
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/20 7:39 PM
 */
@Component
public class HeaderUsernameRoutePredicateFactory extends AbstractRoutePredicateFactory<HeaderUsernameRoutePredicateFactory.Config> {

    public static final String USERNAME = "Username";

    public HeaderUsernameRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public ShortcutType shortcutType() {
        return ShortcutType.GATHER_LIST;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("username");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        List<String> usernames = config.getUsername();
        return (GatewayPredicate) serverWebExchange -> {
            String username = serverWebExchange.getRequest().getHeaders().getFirst(USERNAME);
            if (CharSequenceUtil.isNotBlank(username)) {
                return usernames.contains(username);
            }
            return false;
        };
    }

    @Data
    public static class Config {
        private List<String> username;
    }

}
