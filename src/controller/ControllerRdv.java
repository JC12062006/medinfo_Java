package controller;

import java.util.ArrayList;

import model.ModelRdv;


public class ControllerRdv {

public static ArrayList<Rdv> selectAllRdv(String filtre){
		
		return ModelRdv.selectAllRdv(filtre);
	}

public static void insertRdv(Object Rdv) {
	
}

public static void deleteRdv(int idRdv) {
	
}

public static void updateRdv(Object Rdv) {
	
}
	
}
