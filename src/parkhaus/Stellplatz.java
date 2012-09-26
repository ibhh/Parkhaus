package parkhaus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Simon
 */
public class Stellplatz implements Serializable {

    private int Lange_in_cm, Breite_in_cm, NR;
    private boolean belegt = false;

    public Stellplatz(int Lange_in_cm, int Breite_in_cm, int NR) {
        this.Lange_in_cm = Lange_in_cm;
        this.Breite_in_cm = Breite_in_cm;
        this.NR = NR;
    }

    public void setBelegt(boolean belegt) {
        this.belegt = belegt;
    }

    public int getNR() {
        return NR;
    }

    public boolean isBelegt() {
        return belegt;
    }

    public int getBreite_in_cm() {
        return Breite_in_cm;
    }

    public int getLange_in_cm() {
        return Lange_in_cm;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.write(NR);
        oos.write(Lange_in_cm);
        oos.write(Breite_in_cm);
        oos.writeBoolean(belegt);
    }

    private void readObjekt(ObjectInputStream ois) throws IOException {
        NR = ois.readInt();
        Lange_in_cm = ois.readInt();
        Breite_in_cm = ois.readInt();
        belegt = ois.readBoolean();
    }
}
