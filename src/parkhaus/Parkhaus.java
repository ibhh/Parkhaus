package parkhaus;

import javax.swing.JFrame;

/**
 *
 * @author Simon
 */
public class Parkhaus {

    private String Parkhaus_Name;
    private int Hoehe_in_cm, Stellpleatze;

    public Parkhaus(String Parkhaus_Name, int Hoehe_in_cm, int Stellpleatze) {
        this.Parkhaus_Name = Parkhaus_Name;
        this.Hoehe_in_cm = Hoehe_in_cm;
        this.Stellpleatze = Stellpleatze;
    }

    public void setStellpleatze(int Stellpleatze) {
        this.Stellpleatze = Stellpleatze;
    }

    public int getStellpleatze() {
        return Stellpleatze;
    }

    public String getParkhaus_Name() {
        return Parkhaus_Name;
    }

    public int getHoehe_in_cm() {
        return Hoehe_in_cm;
    }
}
