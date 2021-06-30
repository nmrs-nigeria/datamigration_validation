/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.datamigration.fragment.controller;

import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.datamigration.api.dao.DbConnection;
import org.openmrs.module.datamigration.util.FactoryUtils;
import org.openmrs.module.datamigration.util.Model.PatientLineList;
import org.openmrs.module.datamigration.util.Model.SummaryDashboard;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  * Controller for a fragment that shows all users  
 */
public class UsersFragmentController {
	
	public void controller(FragmentModel model, @SpringBean("userService") UserService service) {

		DbConnection connection = new DbConnection();
		FactoryUtils factoryUtils = new FactoryUtils();
		Map<String, Integer> map = new HashMap<String, Integer>();
		List<SummaryDashboard> summaryDashboardList = factoryUtils.getEncounters();
		List<PatientLineList> patientLineList =  factoryUtils.getPatientsLineList();

		model.addAttribute("patientLineList", patientLineList);

		map.put("totalPatients",Context.getPatientService().getAllPatients().size());
		SummaryDashboard summaryDashboard = summaryDashboardList.stream().filter(x->x.getEncounterTypeID().equals(11)).findFirst().orElse(null);
		if(summaryDashboard != null)
			map.put("totallLaboratoryEncounter", summaryDashboard.getCountOfEncounter());
		else
			map.put("totallLaboratoryEncounter", 0);
		summaryDashboard = summaryDashboardList.stream().filter(x->x.getEncounterTypeID().equals(13)).findFirst().orElse(null);
		if(summaryDashboard != null)
			map.put("totalPharmacyEncounter", summaryDashboard.getCountOfEncounter());
		else
			map.put("totalPharmacyEncounter", 0);
		summaryDashboard = summaryDashboardList.stream().filter(x->x.getEncounterTypeID().equals(12)).findFirst().orElse(null);
		if(summaryDashboard != null)
			map.put("totalCareCardEncounter", summaryDashboard.getCountOfEncounter());
		else
			map.put("totalCareCardEncounter", 0);
		model.mergeAttributes(map);
		
	}
}
