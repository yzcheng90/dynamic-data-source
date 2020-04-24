package com.example.database.controller;

import com.example.database.config.DynamicDataSourceConfig;
import com.example.database.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author czx
 * @title: TestController
 * @projectName dynamic-data-source
 * @description: TODO
 * @date 2020/4/2416:13
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    private DynamicDataSourceConfig dynamicDataSourceConfig;

    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @Author czx
     * @Description //TODO 每次添加或修改数据源时，记得调用该方法刷新
     * @Date 17:07 2020/4/24
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping(value = "/refresh",method = RequestMethod.GET)
    public void refresh(){
        dynamicDataSourceConfig.reset();
        log.info("-----数据源设置成功！");
    }

    @SneakyThrows
    @RequestMapping("/test")
    public String test(){
        return objectMapper.writeValueAsString(userService.list());
    }

}
