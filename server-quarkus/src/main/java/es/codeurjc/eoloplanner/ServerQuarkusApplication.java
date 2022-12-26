package es.codeurjc.eoloplanner;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class ServerQuarkusApplication {
    public static void main(String ... args) {
        System.out.println("Running main method");
        Quarkus.run(args);
    }
}
