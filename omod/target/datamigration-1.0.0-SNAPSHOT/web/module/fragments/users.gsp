<%
    def id = config.id
%>
<%= ui.resourceLinks() %>



<div id="apps">

    <a id="demoapp-homepageLink-demoapp-homepageLink-extension" href="/openmrs/nigeriaemr/customreport.page" class="button app big">

        <i class="icon-user"></i>
        <br>

        Total Pateints
        <p><b>${ ui.format(totalPatients) }</b></p>
    </a>

    <a id="coreapps-activeVisitsHomepageLink-coreapps-activeVisitsHomepageLink-extension" href="/openmrs/coreapps/findpatient/findPatient.page?app=coreapps.findPatient" class="button app big">

        <i class=" icon-google-plus-sign "></i>
        <br>
          Lab Records
        <p><b>${ ui.format(totallLaboratoryEncounter) }</b></p>
    </a>

    <a id="org-openmrs-module-coreapps-activeVisitsHomepageLink-org-openmrs-module-coreapps-activeVisitsHomepageLink-extension" href="/openmrs/coreapps/activeVisits.page?app=coreapps.activeVisits" class="button app big">

        <i class="icon-vitals"></i>
        <br>
         Pharmacy Records
        <p><b>${ ui.format(totalPharmacyEncounter) }</b></p>
    </a>

    <a id="referenceapplication-registrationapp-registerPatient-homepageLink-referenceapplication-registrationapp-registerPatient-homepageLink-extension" href="/openmrs/registrationapp/registerPatient.page?appId=referenceapplication.registrationapp.myRegisterPat" class="button app big">

        <i class="icon-calendar"></i>
        <br>
         Care card Records
        <p><b>${ ui.format(totalCareCardEncounter) }</b></p>
    </a>

</div>
<div class="container">

    <table>
        <thead style="font-size: 13px;">
        <tr>
            <th>PateintyID</th>
            <th>Lab records</th>
            <th>Pharmacy Records</th>
            <th>ARTstartdate</th>
            <th>LastPickup</th>
            <th>firstRegimen</th>
            <th>lastRegimen</th>
            <th>duration</th>
            <th>lastViralLoadResult</th>
            <th>ViralLoad Result Date</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>10007F</td>
            <td>3</td>
            <td>4</td>
            <td>2 May 2019</td>
            <td>2 May 2019</td>
            <td>TDF-3TC-EFV</td>
            <td>TDF-3TC-EFV</td>
            <td>30</td>
            <td>3100</td>
            <td>2 May 2019</td>
        </tr>
        </tbody>
    </table>
</div>











