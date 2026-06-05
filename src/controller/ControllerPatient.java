package controller;
import java.util.ArrayList;
import model.ModelPatient;

public class ControllerPatient {
	  
    // Changement de void en boolean
	public static boolean insertPatient(Patient p) {
	    return ModelPatient.insertPatient(p);
	}
	  
    public static ArrayList<Patient> selectAllPatientsFiltre(String filtre){
		return ModelPatient.selectAllPatientsFiltre(filtre);
	}
}