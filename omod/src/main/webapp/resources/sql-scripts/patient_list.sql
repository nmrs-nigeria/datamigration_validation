
SELECT
 
pid1.`patient_id` AS `patient_id`, 
pid1.identifier AS `identifier`,
person.gender AS `Sex`,
CONCAT(pn.`family_name`, " ", pn.`given_name`) personName,

DATE_FORMAT(( SELECT  DATE(`value_datetime`) FROM `obs` ob JOIN encounter e ON ob.encounter_id=e.encounter_id
     WHERE ob.`concept_id` IN (159599)
     AND ob.`value_datetime` <= NOW()
     AND ob.`person_id` =  patient.`patient_id` 
     AND e.encounter_type=25
     AND ob.voided=0
     AND e.voided=0
     LIMIT 1),'%Y-%m-%d') AS `dateOfFirstEncounter`,


MAX(IF(obs.concept_id=165708,DATE_FORMAT(@Clinic_Visit_Lastdate:=sinner.last_date,'%Y-%m-%d'),NULL)) AS `dateOfLastEncounter`,
 
MAX(
   IF(obs2.concept_id=164506,cn2.`name`,
   IF(obs2.concept_id=164513,cn2.`name`,
   IF(obs2.concept_id=164507,cn2.name,
   IF(obs2.concept_id=164514,cn2.name,
   IF(obs2.concept_id=165702,cn2.name,
   IF(obs2.concept_id=165703,cn2.name,NULL
   ))))))) AS `firstDocumentedRegimen`, 

( SELECT  cn.`name` FROM `obs` ob  JOIN `concept_name` cn ON cn.`concept_id` = ob.value_coded JOIN encounter e ON ob.encounter_id=e.encounter_id
     WHERE ob.`concept_id` IN (164506,164513,165702,164507,164514,165703)  AND cn.`locale` = 'en' AND cn.`locale_preferred` = 1 
     AND ob.`obs_datetime` <= NOW()
     AND ob.`person_id` =  patient.`patient_id` 
     AND e.encounter_type=13
     AND ob.voided=0
     AND e.voided=0
     ORDER BY ob.obs_datetime DESC LIMIT 1) AS `LastdocumentedRegimen`,
     (SELECT
     COUNT(`encounter`.`encounter_type`)
   FROM `encounter`
   WHERE ((`encounter`.`encounter_type` = 13)
          AND (`encounter`.`patient_id` = `patient`.`patient_id`))) AS `encounterTypePhamacy`,
  (SELECT
     COUNT(`encounter`.`encounter_type`)
   FROM `encounter`
   WHERE ((`encounter`.`encounter_type` = 11)
          AND (`encounter`.`patient_id` = `patient`.`patient_id`))) AS `encounterTypeLab`

FROM patient
INNER JOIN
  (SELECT 
obs.person_id,
obs.concept_id AS concept_id,
 MAX(obs.obs_datetime) AS last_date, 
MIN(obs.obs_datetime) AS first_date
FROM obs WHERE obs.voided=0 AND obs.obs_datetime<=NOW() AND concept_id IN(159599,165708,159368,164506,164513,164507,164514,165702,165703,165050,
856,164980,165470,160540,165242,165469,166043,164505,1652,161364,630,103166) GROUP BY obs.person_id,obs.concept_id ) AS sinner
ON (sinner.person_id=patient.patient_id )


LEFT JOIN patient_identifier pid1 ON(pid1.patient_id=patient.patient_id AND patient.voided=0 AND pid1.identifier_type=4)
LEFT JOIN patient_identifier pid2 ON(pid2.patient_id=patient.patient_id AND patient.voided=0 AND pid2.identifier_type=5)
LEFT JOIN `person_name` pn ON(pn.`person_id`=patient.patient_id AND patient.voided=0 AND pn.`preferred`=1)
LEFT JOIN `person_address` pa ON(pa.`person_id`=patient.patient_id AND patient.voided=0 AND pa.`preferred`=1)
LEFT JOIN `person_attribute` part ON(part.`person_id`=patient.patient_id AND patient.voided=0 AND part.`person_attribute_type_id`=8)
INNER JOIN person ON(person.person_id=patient.patient_id)
INNER JOIN obs ON(obs.person_id=patient.patient_id AND obs.concept_id=sinner.concept_id AND obs.obs_datetime=sinner.last_date AND obs.voided=0 )
INNER JOIN obs obs2 ON(obs2.person_id=patient.patient_id AND obs2.concept_id=sinner.concept_id AND obs2.obs_datetime=sinner.first_date AND obs2.voided=0 )

LEFT JOIN concept_name cn1 ON(obs.value_coded=cn1.concept_id AND cn1.locale='en' AND cn1.locale_preferred=1)
LEFT JOIN concept_name cn2 ON(obs2.value_coded=cn2.concept_id AND cn2.locale='en' AND cn2.locale_preferred=1)

GROUP BY patient.`patient_id`
