package sgu.j2ee.medifamily;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class MedifamilyApplication {

    public static void main(String[] args)  {
        SpringApplication.run(MedifamilyApplication.class, args);
    }

}
