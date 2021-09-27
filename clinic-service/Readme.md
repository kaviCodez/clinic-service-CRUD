Clinic Program CRUD operations

1. Info:
	(i) CRUD operations and mapping tables for clinic program
	(ii) lombok sl4j integrated as part of logging mechanism
	(iii) Auditing enabled for tables
	(iv) Exception handlers and Test cases are integrated 
	(v) H2 In memory database enabled - UI - http://localhost:9002/h2-console
	(vi) Swagger enabled for API documentation - UI - http://localhost:9002/swagger-ui.html 
	

2. Execution steps:

	1. It can be executed in 2 different ways,
		(i) Run as java application in any IDE or console
		(ii) Build as maven project
			(a) Execute "mvn clean install" goal
			(b) In the target directory clinic-assignment-1.0.0-SNAPSHOT will be generated.
	 Run the jar file using the below command,"java -jar clinic-assignment-1.0.0-SNAPSHOT.jar"


3. Diagnosis Table CRUD to store Diagnosis information

Assumptions: Diagnosis Table is considered as a special table holding the common information which can be used by both doctor and user.
a). From doctor perspective the values in the diagnosis table is considered as "Doctor's specialization"
b). From Patient perspective the values in the diagnosis table is considered as "Diagnosis Type" ie the illness for which the treatment to be given for the patient.


(i) POST API:  http://localhost:9002/diagnosistype/creatediagnosistype

Request Body:   
{
	"typeName":"ortho"
}

Response Body:
{
    "typeId": 1,
    "typeName": "ortho"
}

(ii) PUT API:  http://localhost:9002/diagnosistype/updatediagnosistype

Request Body:
{
    "typeId":1,
	"typeName":"orthopedic"
}

Response Body:
{
    "typeId": 1,
    "typeName": "orthopedic"
}

(iii) GET API:
Get Diagnosis Type based on ID passed by the client
(A) http://localhost:9002/diagnosistype/getdiagnosistypebyid/1

Response Body:
{
    "typeId": 1,
    "typeName": "orthopedic"
}

Get All Diagnosis Type saved in DB
(B) http://localhost:9002/diagnosistype/getalldiagnosistype

Response Body:
[
    {
        "typeId": 1,
        "typeName": "orthopedic"
    },
    {
        "typeId": 2,
        "typeName": "cardio"
    },
    {
        "typeId": 3,
        "typeName": "ent"
    }
]

(iv) Delete API: http://localhost:9002/diagnosistype/delete/1

Response: HTTP Status ACCEPTED

Doctor Table and Doctor specialization mapping Table:

Save Doctor with the specialization from the diagnosis Table. This post operation saves data to doctor table and to doctor specialization table which will have doctorId and diagnosis table id 

(i) POST API: http://localhost:9002/doctors/createdoctor/

doctorSpecialization is a list of diagnosis type and hence multiple diagnosis can be associated with the doctor

Request Body: 

{
	"doctorName":"doc1",
	"qualification":"mbbs",
    "doctorSpecialization":[
        {
        "diagType" :{  
        "typeId" :3,
        "typeName":"ortho"
        }
        }
    ]
}

Response Body:

{
    "@doctorId": 1,
    "doctorId": 6,
    "doctorName": "doc1",
    "qualification": "mbbs",
    "doctorSpecialization": [
        {
            "doctorSpecializationId": 7,
            "diagType": {
                "typeId": 3,
                "typeName": "ortho"
            }
        }
    ]
}

(ii) PUT API : http://localhost:9002/doctors/updatedoctor/

Request Body:

{
    "doctorId":6,
	"doctorName":"doctor1",
	"qualification":"mbbs",
    "doctorSpecialization":[
        {
        "diagType" :{  
        "typeId" :3,
        "typeName":"ortho"
        }
        }
    ]
}

Response Body:

{
    "@doctorId": 1,
    "doctorId": 6,
    "doctorName": "doctor1",
    "qualification": "mbbs",
    "doctorSpecialization": [
        {
            "doctorSpecializationId": 8,
            "diagType": {
                "typeId": 3,
                "typeName": "ortho"
            }
        }
    ]
}

(iii) GET API:
(A) Get Doctor based on ID passed by the client
http://localhost:9002/doctors/getdoctorbyid/4

{
    "@doctorId": 1,
    "doctorId": 6,
    "doctorName": "doctor1",
    "qualification": "mbbs",
    "doctorSpecialization": [
        {
            "doctorSpecializationId": 8,
            "diagType": {
                "typeId": 3,
                "typeName": "ortho"
            }
        }
    ]
}
(B) Get All Doctors saved in DB
http://localhost:9002/doctors/getalldoctors

[
    {
        "@doctorId": 1,
        "doctorId": 6,
        "doctorName": "doctor1",
        "qualification": "mbbs",
        "doctorSpecialization": [
            {
                "doctorSpecializationId": 8,
                "diagType": {
                    "typeId": 3,
                    "typeName": "ortho"
                }
            }
        ]
    }
]

(iv)Delete API:
http://localhost:9002/doctors/deletedoctor/4

Response: HTTP Status ACCEPTED

Patient Table, Patient Diagnosis Mapping Table and Doctor Patient Mapping Table mapping Table:

Assumptions: For a new Patient registration all the diagnosis information is presented to the patient in patient registration page and when the patient selects on the diagnosis type based on the type selected the doctor specialist for the diagnosis will be visible in the UI. Hence Patient json payload will have patient info,patient diagnosis info along with doctor info


This post operations saves data to Patient table, patient diagnosis mapping table and doctor patient mapping table

(i) POST API: http://localhost:9002/patients/createpatient/


Request Body: 

{
	"patientName":"patient1",
	"address":"india",
    "email":"patient1@gmail.com",
    "patientDiagnosis":[
        {
        "diagType" :{  
        "typeId" :3,
        "typeName":"ortho"
        }
        }
    ],
    "doctorPatient":[
        {
        "doctor":{
            "doctorId":6,
            "doctorName":"doctor1",
            "qualification":"mbbs"
        }
        }

    ]
}

Response Body:

{
    "@patientId": 1,
    "patientId": 9,
    "patientName": "patient1",
    "address": "india",
    "email": "patient1@gmail.com",
    "patientDiagnosis": [
        {
            "patientDiagId": 11,
            "diagType": {
                "typeId": 3,
                "typeName": "ortho"
            }
        }
    ],
    "doctorPatient": [
        {
            "@doctorPatientId": 2,
            "doctorPatientId": 10,
            "doctor": {
                "@doctorId": 3,
                "doctorId": 6,
                "doctorName": "doctor1",
                "qualification": "mbbs",
                "doctorSpecialization": null
            },
            "patient": null
        }
    ]
}

(ii) PUT API : http://localhost:9002/doctors/updatedoctor/

Request Body:

{
    "patientId":9,
	"patientName":"patient1",
	"address":"india",
    "email":"patient1@outlook.com",
    "patientDiagnosis":[
        {
        "diagType" :{  
        "typeId" :3,
        "typeName":"ortho"
        }
        }
    ],
    "doctorPatient":[
        {
        "doctor":{
            "doctorId":6,
            "doctorName":"doctor1",
            "qualification":"mbbs"
        }
        }

    ]
}

Response Body:

{
    "@patientId": 1,
    "patientId": 9,
    "patientName": "patient1",
    "address": "india",
    "email": "patient1@outlook.com",
    "patientDiagnosis": [
        {
            "patientDiagId": 13,
            "diagType": {
                "typeId": 3,
                "typeName": "ortho"
            }
        }
    ],
    "doctorPatient": [
        {
            "@doctorPatientId": 2,
            "doctorPatientId": 12,
            "doctor": {
                "@doctorId": 3,
                "doctorId": 6,
                "doctorName": "doctor1",
                "qualification": "mbbs",
                "doctorSpecialization": [
                    {
                        "doctorSpecializationId": 8,
                        "diagType": {
                            "typeId": 3,
                            "typeName": "ortho"
                        }
                    }
                ]
            },
            "patient": null
        }
    ]
}
(iii) GET API:
(A) Get Patient based on ID passed by the client
http://localhost:9002/patients/getpatientbyid/14

{
    "@patientId": 1,
    "patientId": 14,
    "patientName": "patient2",
    "address": "india",
    "email": "patient1@gmail.com",
    "patientDiagnosis": [
        {
            "patientDiagId": 16,
            "diagType": {
                "typeId": 3,
                "typeName": "ortho"
            }
        }
    ],
    "doctorPatient": [
        {
            "@doctorPatientId": 2,
            "doctorPatientId": 15,
            "doctor": {
                "@doctorId": 3,
                "doctorId": 6,
                "doctorName": "doctor1",
                "qualification": "mbbs",
                "doctorSpecialization": [
                    {
                        "doctorSpecializationId": 8,
                        "diagType": {
                            "typeId": 3,
                            "typeName": "ortho"
                        }
                    }
                ]
            },
            "patient": 1
        }
    ]
}
(B) Get All Patients saved in DB
http://localhost:9002/patients/getallpatients

[
    {
        "@patientId": 1,
        "patientId": 9,
        "patientName": "patient1",
        "address": "india",
        "email": "patient1@gmail.com",
        "patientDiagnosis": [
            {
                "patientDiagId": 11,
                "diagType": {
                    "typeId": 2,
                    "typeName": "cardio"
                }
            }
        ],
        "doctorPatient": [
            {
                "@doctorPatientId": 2,
                "doctorPatientId": 10,
                "doctor": {
                    "@doctorId": 3,
                    "doctorId": 7,
                    "doctorName": "doc1",
                    "qualification": "mbbs",
                    "doctorDiagnosis": [
                        {
                            "doctorDiagId": 8,
                            "diagType": {
                                "typeId": 2,
                                "typeName": "cardio"
                            }
                        }
                    ]
                },
                "patient": 1
            }
        ]
    },
    {
        "@patientId": 4,
        "patientId": 12,
        "patientName": "patient2",
        "address": "india",
        "email": "patient2@outlook.com",
        "patientDiagnosis": [
            {
                "patientDiagId": 16,
                "diagType": {
                    "typeId": 2,
                    "typeName": "cardio"
                }
            }
        ],
        "doctorPatient": [
            {
                "@doctorPatientId": 5,
                "doctorPatientId": 15,
                "doctor": 3,
                "patient": 4
            }
        ]
    }
]

(iv)Delete API:
http://localhost:9002/patients/deletepatient/12

Response: HTTP Status ACCEPTED

Get Doctor specialist list based on the patients diagnosis type :

Get API : http://localhost:9002/doctors/getdoctorlist/14

Response data:

[
    {
        "@doctorId": 1,
        "doctorId": 6,
        "doctorName": "doctor1",
        "qualification": "mbbs",
        "doctorSpecialization": [
            {
                "doctorSpecializationId": 8,
                "diagType": {
                    "typeId": 3,
                    "typeName": "ortho"
                }
            }
        ]
    }
]

Get All Patients mapped to the doctor based on doctor id passed from the client:

Get API: http://localhost:9002/doctors/getallpatients/6

Response Body:

[
    {
        "@patientId": 1,
        "patientId": 9,
        "patientName": "patient1",
        "address": "india",
        "email": "patient1@outlook.com",
        "patientDiagnosis": [
            {
                "patientDiagId": 13,
                "diagType": {
                    "typeId": 3,
                    "typeName": "ortho"
                }
            }
        ],
        "doctorPatient": [
            {
                "@doctorPatientId": 2,
                "doctorPatientId": 12,
                "doctor": {
                    "@doctorId": 3,
                    "doctorId": 6,
                    "doctorName": "doctor1",
                    "qualification": "mbbs",
                    "doctorSpecialization": [
                        {
                            "doctorSpecializationId": 8,
                            "diagType": {
                                "typeId": 3,
                                "typeName": "ortho"
                            }
                        }
                    ]
                },
                "patient": 1
            }
        ]
    },
    {
        "@patientId": 4,
        "patientId": 14,
        "patientName": "patient2",
        "address": "india",
        "email": "patient1@gmail.com",
        "patientDiagnosis": [
            {
                "patientDiagId": 16,
                "diagType": {
                    "typeId": 3,
                    "typeName": "ortho"
                }
            }
        ],
        "doctorPatient": [
            {
                "@doctorPatientId": 5,
                "doctorPatientId": 15,
                "doctor": 3,
                "patient": 4
            }
        ]
    }
]



