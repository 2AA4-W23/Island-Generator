import ca.mcmaster.cas.se2aa4.a2.visualizer.Visualize;

import org.apache.commons.cli.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        Visualize.createSVG(args);
    }
}
