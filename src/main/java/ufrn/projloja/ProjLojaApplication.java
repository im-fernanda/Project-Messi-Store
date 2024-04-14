package ufrn.projloja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class ProjLojaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjLojaApplication.class, args);
    }

    public static class dbDocs {
        public static String dbURL = "jdbc:postgresql://localhost:5432/projTeste?serverTimezone=UTC";
        public static String username = "postgres";
        public static String password = "2005";
    }
}
