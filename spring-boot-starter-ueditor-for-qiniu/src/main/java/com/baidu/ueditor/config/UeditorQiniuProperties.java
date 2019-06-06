package com.baidu.ueditor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ueditor 七牛 配置
 * @author huxudong
 * @date 2019-02
 */
@ConfigurationProperties(prefix = "ue", ignoreInvalidFields = true)
@Data
public class UeditorQiniuProperties {

    /**
     * config.json 字符串
     */
    private String config = "{}";

    /**
     * ueditor服务器统计请求接口路径
     */
    private String serverUrl;

    private String accessKey;

    private String secretKey;

    /**
     * 七牛机房  华东：zone0 华北：zone1 华南：zone2 北美：zoneNa0
     */
    private String zone = "autoZone";

    private String bucket;

    private String baseUrl;

    private String cdn;

    private String uploadDirPrefix = "ueditor/file/";
}
