package com.tuixiaofei.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author : tuixiaofei
 * @Date: 2024/9/8 13:13
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "spzx.auth")
public class UserProperties {

    private List<String> noAuthUrls;
}
