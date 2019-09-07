package org.openmrs.module.datamigration.util.Model;

import java.util.Date;

public class LineList {
	
	String PateintyID;
	
	String patientFirstName;
	
	String patientLasttName;
	
	String patientPhoneNumber;
	
	String duration;
	
	Date dateOfFirstEncounter;
	
	Date dateOfLastEncounter;
	
	Date DateOfFirstARVPickup;
	
	Date firstDocumentedRegimen;
	
	Date lastDocumentedRegimen;
	
	Date lastDocumentedViralLoadResult;
	
	Date ARTstartdate;
	
	public String getPateintyID() {
		return PateintyID;
	}
	
	public void setPateintyID(String pateintyID) {
		PateintyID = pateintyID;
	}
	
	public String getPatientFirstName() {
		return patientFirstName;
	}
	
	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}
	
	public String getPatientLasttName() {
		return patientLasttName;
	}
	
	public void setPatientLasttName(String patientLasttName) {
		this.patientLasttName = patientLasttName;
	}
	
	public String getPatientPhoneNumber() {
		return patientPhoneNumber;
	}
	
	public void setPatientPhoneNumber(String patientPhoneNumber) {
		this.patientPhoneNumber = patientPhoneNumber;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public Date getDateOfFirstEncounter() {
		return dateOfFirstEncounter;
	}
	
	public void setDateOfFirstEncounter(Date dateOfFirstEncounter) {
		this.dateOfFirstEncounter = dateOfFirstEncounter;
	}
	
	public Date getDateOfLastEncounter() {
		return dateOfLastEncounter;
	}
	
	public void setDateOfLastEncounter(Date dateOfLastEncounter) {
		this.dateOfLastEncounter = dateOfLastEncounter;
	}
	
	public Date getDateOfFirstARVPickup() {
		return DateOfFirstARVPickup;
	}
	
	public void setDateOfFirstARVPickup(Date dateOfFirstARVPickup) {
		DateOfFirstARVPickup = dateOfFirstARVPickup;
	}
	
	public Date getFirstDocumentedRegimen() {
		return firstDocumentedRegimen;
	}
	
	public void setFirstDocumentedRegimen(Date firstDocumentedRegimen) {
		this.firstDocumentedRegimen = firstDocumentedRegimen;
	}
	
	public Date getLastDocumentedRegimen() {
		return lastDocumentedRegimen;
	}
	
	public void setLastDocumentedRegimen(Date lastDocumentedRegimen) {
		this.lastDocumentedRegimen = lastDocumentedRegimen;
	}
	
	public Date getLastDocumentedViralLoadResult() {
		return lastDocumentedViralLoadResult;
	}
	
	public void setLastDocumentedViralLoadResult(Date lastDocumentedViralLoadResult) {
		this.lastDocumentedViralLoadResult = lastDocumentedViralLoadResult;
	}
	
	public Date getARTstartdate() {
		return ARTstartdate;
	}
	
	public void setARTstartdate(Date ARTstartdate) {
		this.ARTstartdate = ARTstartdate;
	}
}
