package com.faergestad.drivepark.Model;

public class Taxi {
    // Startpriser
    private final double STARTPRIS_HVERDAG_DAG = 50;
    private final double STARTPRIS_HVERDAG_KVELD = 55;
    private final double STARTPRIS_HVERDAG_NATT = 55;
    private final double STARTPRIS_HELG_DAG = 55;
    private final double STARTPRIS_HELG_NATT = 73;
    private final double STARTPRIS_HOYTID = 79;
    // Avstandstakst Kr/Km
    private final double HVERDAG_DAG_PRKM = 13.6;
    private final double HVERDAG_KVELD_PRKM = 16.5;
    private final double HVERDAG_NATT_PRKM = 16.5;
    private final double HELG_DAG_PRKM = 16.5;
    private final double HELG_NATT_PRKM = 15.59;
    private final double HOYTID_PRKM = 19.98;
    // Tidstakst Kr/Minutt
    private final double HVERDAG_DAG_TIDSTAKST = 7.08;
    private final double HVERDAG_KVELD_TIDSTAKST = 8.5;
    private final double HVERDAG_NATT_TIDSTAKST = 8.50;
    private final double HELG_DAG_TIDSTAKST = 8.50;
    private final double HELG_NATT_TIDSTAKST = 9.98;
    private final double HOYTID_TIDSTAKST = 9.98;

    public Taxi() {
    }

    public static double taxiPris(double pickup, double dropoff, int antallPassasjerer, boolean trengerStorBil) {

        // TODO fyll inn logikk

        return 0;
    }

}
