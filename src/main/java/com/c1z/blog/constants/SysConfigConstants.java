package com.c1z.blog.constants;

/**
 * blog系统变量
 */
public enum SysConfigConstants {
    SYS_AUTHOR("sysAuthor","开发者"),
    SYS_AUTHOR_IMG("sysAuthorImg","开发者头像"),
    DEFAULT_CATEGORY("1","默认分类"),
    DEFAULT_TAG("1","默认标题");

    private final String configField;
    private final String configName;

    SysConfigConstants(String configField, String configName) {
        this.configField = configField;
        this.configName = configName;
    }

    public String getConfigField() {
        return configField;
    }

    public String getConfigName() {
        return configName;
    }
}
