package controller;

import java.util.ArrayList;

import model.ModelRdv;


public class ControllerRdv {

	public static ArrayList<Rdv> selectAllRdv(String filtre){
			
			return ModelRdv.selectAllRdv(filtre);
		}
	
	public static void insertRdv(Rdv unRdv) {
		
		
	}
	
	public static void deleteRdv(int idRdv) {
		ModelRdv.deleteRdv(idRdv);
	}
	
	public static void updateRdv(Rdv unRdv) {
		
		ModelRdv.updateRdv(unRdv);
	}
	
}
