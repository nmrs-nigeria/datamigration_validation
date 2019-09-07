package org.openmrs.module.datamigration.util;

import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.datamigration.api.dao.DbConnection;
import org.openmrs.module.datamigration.util.Model.Migration;
import org.openmrs.module.datamigration.util.Model.SummaryDashboard;

import java.sql.*;
import java.text.ParseException;
import java.util.*;

public class FactoryUtils {
	
	private static String ENCOUNTER_TYPE_VIEW = "SELECT * FROM ENCOUNTER_TYPE_VIEW";
	
	private static String FACILITY_SELECT_QUERY = "SELECT * FROM facility LIMIT 1";
	
	/*This method does the utility connection for the patient*/
	public void PatientUtils(Migration delegate) throws ParseException {
		
		try {
			Location location = LocationUtil.InsertLocation(delegate.getFacility());
			if (location != null) {
				//handle patient
				Patient patient = PatientUtil.InsertPatient(delegate, location);
				
				//handle encounters and obs
				EncounterUtils.InsertEncounter(delegate, location, patient);
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public static List<EncounterType> getEncounterByEncounterTypeId(int HIV_Enrollment_Encounter_Type_Id) {
		
		//Context.getCohortService().getCohortByUuid()
		return Context.getEncounterService().getAllEncounterTypes();
		//.stream().filter(x->x.getEncounterTypeId() == HIV_Enrollment_Encounter_Type_Id ).collect(Collectors.toList());
	}
	
	public ArrayList<SummaryDashboard> getEncounters() {

		DbConnection dbcon = new DbConnection();
		Connection connection = dbcon.Connection();
		ArrayList<SummaryDashboard> summaryDashboardList = new ArrayList<>();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(ENCOUNTER_TYPE_VIEW);
			while (result.next()) {
				SummaryDashboard summaryDashboard = new SummaryDashboard();
				summaryDashboard.setEncounterTypeID(result.getInt(result.findColumn("EncounterTypeId")));
				summaryDashboard.setEncounterName(result.getString(result.findColumn("EncounterType")));
				summaryDashboard.setCountOfEncounter(result.getInt(result.findColumn("NumberOfEncounters")));
				summaryDashboardList.add(summaryDashboard);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return summaryDashboardList;
	}
	
	public static List<Patient> getPatients() {
		return Context.getPatientService().getAllPatients();
	}
}
