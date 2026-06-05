package controller;

import java.util.ArrayList;
import model.ModelCreneau;

public class ControllerCreneau {

    public static ArrayList<Creneau> selectAllCreneaux(String filtre) {
        return ModelCreneau.selectAllCreneaux(filtre);
    }
    
    public static boolean genererPlanning(int idMedecin, int idSalle, String dateDebut, String dateFin, int dureeMinutes) {
        return ModelCreneau.genererPlanning(idMedecin, idSalle, dateDebut, dateFin, dureeMinutes);
    }
}
