package controller;

import java.util.ArrayList;
import model.ModelCreneau;

public class ControllerCreneau {

    public static ArrayList<Creneau> selectAllCreneaux(String filtre) {
        return ModelCreneau.selectAllCreneaux(filtre);
    }
}
