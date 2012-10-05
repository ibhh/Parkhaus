package parkhaus;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;

/**
 *
 * @author Simon
 */
public class Parkhaus implements Serializable {

    private String Parkhaus_Name;
    private int Hoehe_in_cm;
    private int MaxStellplaetze;
    private Auto erstesAuto = null;
    private ArrayList<Stellplatz> Stellplaetze = new ArrayList<Stellplatz>();

    public Parkhaus(String Parkhaus_Name, int Hoehe_in_cm, int MaxStellplaetze) {
        this.Parkhaus_Name = Parkhaus_Name;
        this.Hoehe_in_cm = Hoehe_in_cm;
        this.MaxStellplaetze = MaxStellplaetze;
    }

    public ListModel convertMaptoList() {
        DefaultListModel list = new DefaultListModel();
        JList themenAuswahl = new JList(list);
        Stellplatz[] Stellplatz_temp = new Stellplatz[Stellplaetze.size()];
        Stellplatz_temp = Stellplaetze.toArray(Stellplatz_temp);
        for (Stellplatz stellplatz : Stellplatz_temp) {
            list.addElement(stellplatz.getNR());
        }
        return list;
    }

    public void setErstesAuto(Auto erstesAuto) {
        this.erstesAuto = erstesAuto;
    }

    public Auto getErstesAuto() {
        return erstesAuto;
    }

    public int getStellpleatze() {
        return Stellplaetze.size();
    }

    public int getFreeStellplaetze() {
        return MaxStellplaetze - getStellpleatze();
    }

    public int neuerStellplatz(int breite, int laenge, int nummer) {
        if (getFreeStellplaetze() > 0) {
            if (MainClass.debug) {
                System.out.println("Freie Stellplätze: " + getFreeStellplaetze());
            }
            Stellplatz stellplatz = new Stellplatz(breite, laenge, nummer);
            Stellplatz[] Stellplatz_temp = new Stellplatz[Stellplaetze.size()];
            Stellplatz_temp = Stellplaetze.toArray(Stellplatz_temp);
            for (Stellplatz stellplatzneu : Stellplatz_temp) {
                if (stellplatz.getNR() == stellplatzneu.getNR()) {
                    if (MainClass.debug) {
                        System.out.println("Existiert bereits!");
                    }
                    return 0;
                }
            }
            Stellplaetze.add(stellplatz);
            MainClass.getDaten().aktualisieren();
            return 1;
        }
        if (MainClass.debug) {
            System.out.println("keine Frei!");

        }
        return -1;
    }

    public String getParkhaus_Name() {
        return Parkhaus_Name;
    }

    public int getHoehe_in_cm() {
        return Hoehe_in_cm;
    }

    public void setParkhaus_Name(String Parkhaus_Name) {
        this.Parkhaus_Name = Parkhaus_Name;
    }

    public void setHoehe_in_cm(int Hoehe_in_cm) {
        this.Hoehe_in_cm = Hoehe_in_cm;
    }

    public int getSummeAllerLaengen() {
        Stellplatz[] Stellplatz_temp = new Stellplatz[getStellpleatze()];
        Stellplatz_temp = Stellplaetze.toArray(Stellplatz_temp);
        int a = 0;
        for (Stellplatz stellplatz : Stellplatz_temp) {
            a += stellplatz.getLange_in_cm();
        }
        return a;
    }
//    private void writeObject(ObjectOutputStream oos) throws IOException {
//        oos.writeUTF(Parkhaus_Name);
//        oos.write(Hoehe_in_cm);
//        oos.write(Stellplaetze);
//    }
//
//    private void readObjekt(ObjectInputStream ois) throws IOException {
//        Parkhaus_Name = ois.readUTF();
//        Hoehe_in_cm = ois.readInt();
//        Stellplaetze = ois.readInt();
//    }
}
