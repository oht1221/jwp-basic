package next;

import java.io.File;

import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServerLauncher {
    private static final Logger logger = LoggerFactory.getLogger(WebServerLauncher.class);
    private static final String WEB_APP_DIR_LOCATION = "webapp/";

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.addWebapp("/", new File(WEB_APP_DIR_LOCATION).getAbsolutePath());
        logger.info("configuring app with basedir: {}", new File("./" + WEB_APP_DIR_LOCATION).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}
