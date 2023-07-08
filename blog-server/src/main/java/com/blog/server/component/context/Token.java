package com.blog.server.component.context;

/**
 * @author yanzhihao
 * @since 2023/4/27
 */
public interface Token {

    String TOKEN = "authorization";

    enum Claim {

        UserId("user_id");
        private final String key;

        Claim(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    Long getUserid();
}
