package controller;

import java.util.ArrayList;
import model.ModelMedecin;

public class ControllerMedecin {

    // Changement de void en boolean
    public static boolean insertMedecin(Medecin unMedecin) {
        return ModelMedecin.insertMedecin(unMedecin);
    }

    public static ArrayList<Medecin> selectAllMedecins() {
        return ModelMedecin.selectAllMedecins();
    }
}
