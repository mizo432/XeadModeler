package xeadModeler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * XEAD Modeler - Spring Boot Application Entry Point
 * 
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@SpringBootApplication
public class XeadModelerApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(XeadModelerApplication.class);
        // Headless modeを無効化 - Swing GUIアプリケーションのため
        app.setHeadless(false);
        app.run(args);
    }
}
