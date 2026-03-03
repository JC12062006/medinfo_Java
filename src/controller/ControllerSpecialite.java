package controller;

import java.util.ArrayList;

import model.ModelSpecialite;

public class ControllerSpecialite {
	public static ArrayList<Specialite> selectAllSpecialite(){
		return ModelSpecialite.selectAllSpecialite();
	}
}
