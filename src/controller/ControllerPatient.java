package controller;
import java.util.ArrayList;
import model.ModelPatient;

public class ControllerPatient {
  
	public static void insertPatient(Patient p) {
	    ModelPatient.insertPatient(p);
	}
	  
    public static ArrayList<Patient> selectAllPatientsFiltre(String filtre){
		
		return ModelPatient.selectAllPatientsFiltre(filtre);
	}
}
