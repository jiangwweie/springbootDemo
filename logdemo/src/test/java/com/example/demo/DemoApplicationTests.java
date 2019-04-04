package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {


    public final static Logger log = LoggerFactory.getLogger(DemoApplicationTests.class);


    @Test
    public void testlog(){
        log.trace("lombok info log!");
        log.debug("lombok debug log!");
        log.info("lombok info log!");
        log.warn("lombok warn log!");
        log.error("lombok error log!");
    }

}

