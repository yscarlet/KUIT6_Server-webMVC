package jwp;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.File;
import java.util.logging.Logger;

@SpringBootApplication
public class WebServerLauncher {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebServerLauncher.class, args);
    }
}
