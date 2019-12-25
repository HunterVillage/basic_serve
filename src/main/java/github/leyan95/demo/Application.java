package github.leyan95.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wujianchuan
 */
@SpringBootApplication(scanBasePackages = "github.leyan95")
public class Application {

    public static void main(final String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
