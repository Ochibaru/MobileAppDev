package com.myfitnesstracker.service;
import java.util.Scanner;

// class if needed to convert metric to imperial and vice versa, still needs to be tested fully for no errors
public class MetricImperialConverter {

    public long readInput() {
        Scanner reader = new Scanner(System.in);

        if (reader.hasNextLong()) {
            long number = reader.nextLong();
            if (reader.hasNext("\\b+(mil|in|inch|ft|foot|feet|yd|yard|ml|mile)\\b+")) {
                String unit = reader.findInLine("\\b+(mil|in|inch|ft|foot|feet|yd|yard|ml|mile)\\b+");
                double mm = toMm(number, unit);
                System.out.println(number + " " + unit + " is:");
                System.out.println(String.format("%f", mm) + " mm");
                System.out.println(String.format("%f", mm / 10) + " cm");
                System.out.println(String.format("%f", mm / 1000) + " m");
                System.out.println(String.format("%f", mm / 1000000) + " km");
            } else if (reader.hasNext("\\b+(mm|milimeter|cm|centimeter|m|meter|km|kilometer)\\b+")) {
                String unit = reader.findInLine("\\b+(mm|milimeter|cm|centimeter|m|meter|km|kilometer)\\b+");
                double mil = toMil(number, unit);
                System.out.println(number + " " + unit + " is:");
                System.out.println(String.format("%.2g", mil) + " mil");
                System.out.println(String.format("%.2g", mil / 1000) + " inch");
                System.out.println(String.format("%.2g", mil / 12000) + " ft");
                System.out.println(String.format("%.2g", mil / 36000) + " yard");
                System.out.println(String.format("%.2g", mil / 63360000) + " mile");
            } else {
                System.out.println("Invalid input");
            }
        } else {
            System.out.println("Invalid input");
        }

        return;
    }


    // convert any metric system with unit specified in second parameter to mil
    public static double toMil(long metric, String unit) {
        double mm;

        if(unit.matches("\\b+(cm|centimeter)\\b+")) {
            mm = metric*10;
        }
        else if(unit.matches("\\b+(m|meter)\\b+")) {
            mm = metric*1000;
        }
        else if(unit.matches("\\b+(km|kilometer)\\b+")) {
            mm = metric*1000000;
        }
        else {
            mm = metric;
        }

        return mm * 39.3701;
    }

    // convert any imperial system with unit specified in second parameter to mm
    public static double toMm(long imperial, String unit) {
        double mil;

        if(unit.matches("\\b+(in|inch)\\b+")) {
            mil = imperial*1000;
        }
        else if(unit.matches("\\b+(ft|foot|feet)\\b+")) {
            mil = imperial*12000;
        }
        else if(unit.matches("\\b+(yd|yard)\\b+")) {
            mil = imperial*36000;
        }
        else if(unit.matches("\\b+(ml|mile)\\b+")) {
            mil = imperial*63360000;
        }
        else {
            mil = imperial;
        }

        return mil * 0.0254;
    }
}
}
