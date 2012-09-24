package parkhaus;

import java.io.Serializable;

/**
 *
 * @author Simon
 */
public class Parkhaus implements Serializable{

    private String Parkhaus_Name;
    private int Hoehe_in_cm, Stellplaetze;

    public Parkhaus(String Parkhaus_Name, int Hoehe_in_cm, int Stellplaetze) {
        this.Parkhaus_Name = Parkhaus_Name;
        this.Hoehe_in_cm = Hoehe_in_cm;
        this.Stellplaetze = Stellplaetze;
    }

    public int getStellpleatze() {
        return Stellplaetze;
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

    public void setStellplaetze(int Stellplaetze) {
        this.Stellplaetze = Stellplaetze;
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
