package parkhaus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public Auto(String ID, int Parkplatznummer, String Fahrer) {
        this.ID = ID;
        this.Parkplatznummer = Parkplatznummer;
        this.Fahrer = Fahrer;
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
    
    
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeChars(ID);
        oos.write(Parkplatznummer);
        oos.writeChars(Fahrer);
        oos.writeBoolean(parking);
    }

    private void readObjekt(ObjectInputStream ois) throws IOException {
        ID = ois.readUTF();
        Parkplatznummer = ois.readInt();
        Fahrer = ois.readUTF();
        parking = ois.readBoolean();
    }
}
