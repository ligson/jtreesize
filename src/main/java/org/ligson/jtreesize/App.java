package org.ligson.jtreesize;


import org.ligson.jtreesize.core.SpringBootApplicationRunner;
import org.ligson.jtreesize.core.annotation.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.ligson.jtreesize"})
public class App {


    public static void main(String[] args) throws Exception {
        SpringBootApplicationRunner.run(App.class, args);

    }
}
