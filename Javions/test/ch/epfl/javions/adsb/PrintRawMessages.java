package ch.epfl.javions.adsb;

import ch.epfl.javions.adsb.RawMessage;
import ch.epfl.javions.demodulation.AdsbDemodulator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class PrintRawMessages {
    public static void main(String[] args) throws IOException {
        String f = "/Users/manucristini/EPFLBA2/CS108/Projets/Javions/resources/samples_20230304_1442.bin";
        long start2 = System.currentTimeMillis();
        try (InputStream s = new FileInputStream(f)) {
            AdsbDemodulator d = new AdsbDemodulator(s);
            RawMessage m;
            int count10 = 0;
            int count11 = 0;
            int count20 = 0;
            int count21 = 0;
            while ((m = d.nextMessage()) != null){
                AircraftIdentificationMessage m1 = AircraftIdentificationMessage.of(m);
                if (m1 != null) ++count11;
                AirbornePositionMessage m2 = AirbornePositionMessage.of(m);
                if (m2 != null) ++count21;
                if (m.typeCode() > 0 && m.typeCode() < 5){
                    System.out.println(m1);
                    ++count10;
                }
                if ((m.typeCode() > 8 && m.typeCode() < 19)||(m.typeCode() > 19 && m.typeCode() < 23)){
                    System.out.println(m2);
                    ++count20;
                }
            }
            System.out.println("Nb of AircraftIdentificationMessages with typecode control : " +count10);
            System.out.println("Nb of AircraftIdentificationMessages without typecode control : " +count11);
            System.out.println("Nb of AirbornePositionMessages with typecode control : " +count20);
            System.out.println("Nb of AirbornePositionMessages without typecode control : " +count21);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Elapsed Time in milli seconds: "+ (end2-start2));
    }
}