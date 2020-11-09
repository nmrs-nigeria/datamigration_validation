package org.openmrs.module.datamigration.util;

import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.datamigration.util.Model.Migration;
import org.openmrs.module.datamigration.util.Model.ObsChildren;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static org.openmrs.module.datamigration.util.Operations.isNullOrEmpty;

public abstract class EncounterUtils {
	
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void InsertEncounter(Migration delegate, Location location, Patient patient) throws Exception {
        for (org.openmrs.module.datamigration.util.Model.Encounter e : delegate.getEncounters()) {

            try {

                Date encounterDate = getDate(e.getEncounterDate());
                Visit visit = Context.getVisitService().getVisitsByPatient(patient).stream()
                        .filter(m -> m.getStartDatetime() != null && m.getStartDatetime()
                                .equals(encounterDate))
                        .findFirst().orElse(null);

                if (visit == null) {
                    //create the visit
                    visit = new Visit(patient, Context.getVisitService().getVisitType(1), encounterDate);
                    visit.setStopDatetime(encounterDate);
                    visit.setLocation(location);
                    visit.setPatient(patient);
                }
                visit = Context.getVisitService().saveVisit(visit);

                //provider
                Provider provider;
                Person person = new Person();
                Set<PersonName> personNames = new TreeSet<>();
                personNames.add(new PersonName("Admin", "A", "Admin"));
                person.setNames(personNames);

                //check if person exists in db
                Context.getPersonService().savePerson(person);

                //check if the provider is already in the db;
                provider = Context.getProviderService().getAllProviders().stream().filter(m -> m.getPerson().getFamilyName().equals("Admin")
                        && m.getPerson().getGivenName().equals("Admin")).findFirst().orElse(null);
                if(provider == null){
                    provider = new Provider();
                    provider.setPerson(person);
                    Context.getProviderService().saveProvider(provider);
                }

                Encounter encounter;
                //false is to exclude voided encounters.
                encounter = Context.getEncounterService().getEncountersByVisit(visit, false).stream()
                        .filter(m -> m.getVisit().getStartDatetime().equals(encounterDate) && m.getEncounterType().getEncounterTypeId() == e.getEncounterTypeId()).findFirst().orElse(null);
                if (encounter == null) {
                    encounter = new Encounter();
                    encounter.setVisit(visit);
                    encounter.setForm(Context.getFormService().getForm(e.getFormTypeId()));
                    encounter.setEncounterType(Context.getEncounterService().getEncounterType(
                            e.getEncounterTypeId()));
                    //encounter.setObs(obsSet);
                    encounter.setLocation(location);
                    encounter.setPatient(patient);
                    encounter.setEncounterDatetime(encounterDate);
                    encounter.setProvider(Context.getEncounterService().getEncounterRole(2), provider);
                }

                Context.getEncounterService().saveEncounter(encounter);

                for (org.openmrs.module.datamigration.util.Model.Obs _o : e.getObs()) {
                    if (Context.getConceptService().getConcept(_o.getConceptId()) != null) {
                        if (_o.getObsChildren().size() > 0) {
                            Obs obs = ObsUtil.InsertObs(_o, encounter, location, patient);
                            Obs groupObs = Context.getObsService().saveObs(obs, "");
                            //inserting obs children
                            for (ObsChildren obsChild : _o.getObsChildren()) {
                                Obs obschild = ObsChildrenUtil.InsertObsChild(obsChild, groupObs, encounter, location, patient);
                                Context.getObsService().saveObs(obschild, "");
                            }
                        } else {
                            Obs obs = ObsUtil.InsertObs(_o, encounter, location, patient);
                            Context.getObsService().saveObs(obs, "");
                        }
                    }
                }

                // return encounter;
            } catch (Exception ex) {
                throw ex;
            }
        }
    }
	
	public static Date getDate(String date) throws Exception {
		if (date != null) {
			try {
				return dateFormat.parse(date);
			}
			catch (Exception e) {
				dateFormat2.parse(date);
			}
		}
		return null;
	}
}
