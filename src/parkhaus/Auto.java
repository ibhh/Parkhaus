package parkhaus;

import java.io.Serializable;

/**
 *
 * @author Simon
 */
public class Auto implements Serializable{
    private String ID;
    private int Parkplatznummer;
    private String Fahrer;
    private boolean parking;
    private Auto naechster = null;

    public Auto(String ID, int Parkplatznummer, String Fahrer) {
        this.ID = ID;
        this.Parkplatznummer = Parkplatznummer;
        this.Fahrer = Fahrer;
    }

    public Auto getNaechster() {
        return naechster;
    }

    public void setNaechster(Auto naechster) {
        this.naechster = naechster;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public boolean isParking() {
        return parking;
    }

    public String getFahrer() {
        return Fahrer;
    }

    public String getID() {
        return ID;
    }

    public void setParkplatznummer(int Parkplatznummer) {
        this.Parkplatznummer = Parkplatznummer;
    }

    public int getParkplatznummer() {
        return Parkplatznummer;
    }
}
