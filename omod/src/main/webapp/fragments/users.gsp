<%
    def id = config.id
%>
<%=ui.resourceLinks()%>


<style type="text/css">
.dt-buttons{
    float: right;
}
#apps{
    margin-bottom: 60px;
}

.buttons-html5{
    text-decoration: none;
    margin-left: 5px;
    margin-bottom: 10px;
    text-align: center;
    border-radius: 3px;
    background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #5b57a6), color-stop(100%, #5b57a6));
    background: -webkit-linear-gradient(top, #5b57a6, #5b57a6);
    background: -moz-linear-gradient(top, #5b57a6, #5b57a6);
    background: -o-linear-gradient(top, #5b57a6, #5b57a6);
    background: -ms-linear-gradient(top, #5b57a6, #5b57a6);
    background: linear-gradient(top, #5b57a6, #5b57a6);
    background-color: #5b57a6;
    border: #5b57a6 1px solid;
    padding: 8px 20px;
    display: inline-block;
    line-height: 1.2em;
    color: white;
    cursor: pointer;
    min-width: 0;
    max-width: 300px;
    text-decoration: none;
    padding: 5px 6px;
    min-width: 70px;
    font-size: 0.9em;
}
</style>



<script>
    jq = jQuery;
    jq(document).ready(function () {
        jq('#myTable').DataTable({
            dom: 'Bfrtip',
            buttons: [
                'copy', 'excel', 'pdf'
            ]
        });
    });

</script>


<div id="apps" align="center">

    <a id="demoapp-homepageLink-demoapp-homepageLink-extension" href="#"
       class="button app big">

        <i class="icon-user"></i>
        <br>

        Total Pateints
        <p><b>${ui.format(totalPatients)}</b></p>
    </a>

    <a id="coreapps-activeVisitsHomepageLink-coreapps-activeVisitsHomepageLink-extension"
       href="#" class="button app big">

        <i class=" icon-google-plus-sign "></i>
        <br>
        Lab Records
        <p><b>${ui.format(totallLaboratoryEncounter)}</b></p>
    </a>

    <a id="org-openmrs-module-coreapps-activeVisitsHomepageLink-org-openmrs-module-coreapps-activeVisitsHomepageLink-extension"
       href="#" class="button app big">

        <i class="icon-vitals"></i>
        <br>
        Pharmacy Records
        <p><b>${ui.format(totalPharmacyEncounter)}</b></p>
    </a>

    <a id="referenceapplication-registrationapp-registerPatient-homepageLink-referenceapplication-registrationapp-registerPatient-homepageLink-extension"
       href="#"
       class="button app big">

        <i class="icon-calendar"></i>
        <br>
        Care card Records
        <p><b>${ui.format(totalCareCardEncounter)}</b></p>
    </a>

</div>

<div class="container">

    <table id="myTable">
        <thead style="font-size: 13px;">
        <tr>
            <th>PateintyID</th>
            <th>Lab records</th>
            <th>Pharmacy Records</th>
            <th>ARTstartdate</th>
            <th>LastPickup</th>
            <th>firstDocumentedRegimen</th>
            <th>lastDocumentedRegimen</th>
        </tr>
        </thead>
        <tbody>
        <% if (patientLineList) { %>
        <% patientLineList.each { %>
        <tr>
            <td>${ui.format(it.PatientId)}</td>
            <td>${ui.format(it.countOfLabEncounter)}</td>
            <td>${ui.format(it.countOfPharmacyEncounter)}</td>
            <td>${ui.format(it.dateOfFirstEncounter)}</td>
            <td>${ui.format(it.dateOfLastEncounter)}</td>
            <td>${ui.format(it.firstDocumentedRegimen)}</td>
            <td>${ui.format(it.lastDocumentedRegimen)}</td>
        </tr>
        <% } %>
        <% } else { %>
        <tr>
            <td colspan="2">${ui.message("general.none")}</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>











