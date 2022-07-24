package com.hanyi.producer.common.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wenchangwei
 * @since 2022/7/23 9:28 PM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScanPackageRunner implements ApplicationRunner {

    private final ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> packageNameList = AutoConfigurationPackages.get(applicationContext);
        //com.hanyi.producer
        log.info("启动需要扫描的包路径集合：{}", packageNameList);
    }

}
