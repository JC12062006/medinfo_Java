package controller;

import java.util.ArrayList;
import model.ModelMedecin;

public class ControllerMedecin {

    public static void insertMedecin(Medecin unMedecin) {
        ModelMedecin.insertMedecin(unMedecin);
    }

    public static ArrayList<Medecin> selectAllMedecins() {
        return ModelMedecin.selectAllMedecins();
    }
}
