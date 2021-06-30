package org.openmrs.module.datamigration.util;

import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.datamigration.api.dao.DbConnection;
import org.openmrs.module.datamigration.exception.CustomException;
import org.openmrs.module.datamigration.util.Model.Migration;
import org.openmrs.module.datamigration.util.Model.PatientLineList;
import org.openmrs.module.datamigration.util.Model.SummaryDashboard;
import org.openmrs.module.reporting.serializer.ReportingSerializer;
import org.openmrs.ui.framework.resource.ResourceFactory;
import org.openmrs.ui.framework.resource.ResourceProvider;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.sql.*;
import java.util.*;

public class FactoryUtils {
	
	private static String ENCOUNTER_TYPE_VIEW = "" + "SELECT\n" + "  e.encounter_type AS EncounterTypeId,\n" + "  (SELECT\n"
	        + "     et.name\n" + "   FROM encounter_type et\n"
	        + "   WHERE (et.encounter_type_id = e.encounter_type)) AS EncounterType,\n"
	        + "  COUNT(e.encounter_type) AS NumberOfEncounters\n" + "FROM encounter e\n" + "GROUP BY e.encounter_type";
	
	private static String PATIENT_LINE_LIST = "";
	
	public void PatientUtils(Migration delegate) {
		try {
			Location location = LocationUtil.InsertLocation(delegate.getFacility());
			//handle patient
			Patient patient = PatientUtil.InsertPatient(delegate, location);
			
			//handle encounters and obs
			EncounterUtils.InsertEncounter(delegate, location, patient);
			
			delegate.setUuid(patient.getUuid());
		}
		catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	public static List<EncounterType> getEncounterByEncounterTypeId(int HIV_Enrollment_Encounter_Type_Id) {
		return Context.getEncounterService().getAllEncounterTypes();
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
	
	public static List<PatientLineList> getPatientsLineList() {
		ArrayList<PatientLineList> pateintLineList = new ArrayList<PatientLineList>();
		DbConnection dbcon = new DbConnection();
		Connection connection = dbcon.Connection();
		
		String providerName = "datamigration";
		
		String reportsPath = "sql-scripts/";
		try {
			final ResourceFactory resourceFactory = ResourceFactory.getInstance();
			
			final ResourceProvider resourceProvider = resourceFactory.getResourceProviders().get(providerName);
			
			final File reportsDir = resourceProvider.getResource(reportsPath);
			System.out.print(reportsDir);
			File file = ResourceUtils.getFile(reportsDir + "/patient_list.sql");
			PATIENT_LINE_LIST = new String(Files.readAllBytes(file.toPath()));
			
		}
		catch (Exception ex) {
			System.out.print("File not found");
		}
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(PATIENT_LINE_LIST);
			while (result.next()) {
				PatientLineList Patient = new PatientLineList();
				Patient.setPatientId(result.getString(result.findColumn("identifier")));
				Patient.setPatientName(result.getString(result.findColumn("personName")));
				Patient.setCountOfLabEncounter(result.getInt(result.findColumn("encounterTypeLab")));
				Patient.setCountOfPharmacyEncounter(result.getInt(result.findColumn("encounterTypePhamacy")));
				Patient.setDateOfFirstEncounter(result.getDate(result.findColumn("dateOfFirstEncounter")));
				Patient.setDateOfLastEncounter(result.getDate(result.findColumn("dateOfLastEncounter")));
				Patient.setFirstDocumentedRegimen(result.getString(result.findColumn("firstDocumentedRegimen")));
				Patient.setLastDocumentedRegimen(result.getString(result.findColumn("LastdocumentedRegimen")));
				pateintLineList.add(Patient);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return pateintLineList;
	}
}
