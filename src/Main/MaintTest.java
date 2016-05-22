package Main;

import ca.uhn.hl7v2.model.v26.segment.*;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v26.datatype.NM;
import ca.uhn.hl7v2.model.v26.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v26.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v26.message.ORU_R01;

public class MaintTest {

	public static void main(String[] args) throws Exception{
		ORU_R01 oru = new ORU_R01();
		oru.initQuickstart("ORU", "R01", "P");

		// Populate the MSH Segment
		//MSH|^~\&|PC25ED^^||||20160426203100+0100||ORU^R01^ORU_R01|<CONTADOR>|P|2.6|||AL|AL|||||PC25^^^
		//MSH|^~\&|PC25ED||||20160522161936.251+0200||ORU^R01^ORU_R01|<CONTADOR>|P|2.6|||AL|AL|||||PC25
		MSH mshSegment = oru.getMSH();
		mshSegment.getSendingApplication().getNamespaceID().setValue("PC25ED");
		mshSegment.getMessageControlID().setValue("<CONTADOR>");
		mshSegment.getApplicationAcknowledgmentType().setValue("AL");
		mshSegment.getAcceptAcknowledgmentType().setValue("AL");
		mshSegment.getMessageProfileIdentifier(0).getEntityIdentifier().setValue("PC25");

		
		//PID|||"+grupo_id+"^^^UPNA^^^^^^||FUERTES^MIGUEL^J";
		//PID||PC25^^^&UPNA|||Fuertes^Miguel^J
		// Populate the PID Segment
		PID pid = oru.getPATIENT_RESULT().getPATIENT().getPID();
		pid.getPatientName(0).getFamilyName().getSurname().setValue("Fuertes");
		pid.getPatientName(0).getGivenName().setValue("Miguel");
		pid.getPatientName(0).getSecondAndFurtherGivenNamesOrInitialsThereof().setValue("J");
		pid.getPatientID().getIDNumber().setValue("PC25");
		pid.getPatientID().getAssigningAuthority().getUniversalID().setValue("UPNA");
		
		//OBR|1|PC25|PC25|29274-8^Vital signs measurements^LN|
		//OBR|1|PC25|PC25|29274-8^Vital signs measurements^LN
		/*
		* The OBR segment is contained within a group called ORDER_OBSERVATION, 
		* which is itself in a group called PATIENT_RESULT. These groups are
		* reached using named accessors.
		*/
		ORU_R01_ORDER_OBSERVATION orderObservation = oru.getPATIENT_RESULT().getORDER_OBSERVATION();
		
		// Populate the OBR
		OBR obr = orderObservation.getOBR();
		obr.getSetIDOBR().setValue("1");
		obr.getPlacerOrderNumber().getEntityIdentifier().setValue("PC25");
		obr.getFillerOrderNumber().getEntityIdentifier().setValue("PC25");
		obr.getUniversalServiceIdentifier().getIdentifier().setValue("29274-8");
		obr.getUniversalServiceIdentifier().getText().setValue("Vital signs measurements");
		obr.getUniversalServiceIdentifier().getNameOfCodingSystem().setValue("LN");
		
		//OBX|1|NM|8310-5^TEMPERATURA CORPORAL^LN|1|37.5|Cel^GRADOS CENTIGRADO^UCUM|||||N|
		//OBX|1|MN|8310-5^TEMPERATURA CORPORAL^LN|1|37.5|Cel^GRADOS CENTIGRADO^UCUM|||||N

		// Populate the first OBX
		OBX obx1 = orderObservation.getOBSERVATION(0).getOBX();
		obx1.getSetIDOBX().setValue("1");
		obx1.getObservationIdentifier().getIdentifier().setValue("8310-5");
		obx1.getObservationIdentifier().getText().setValue("TEMPERATURA CORPORAL");
		obx1.getObservationIdentifier().getNameOfCodingSystem().setValue("LN");
		obx1.getUnits().getIdentifier().setValue("Cel");
		obx1.getUnits().getText().setValue("GRADOS CENTIGRADO");
		obx1.getUnits().getNameOfCodingSystem().setValue("UCUM");
		obx1.getObservationResultStatus().setValue("N");
		obx1.getObservationSubID().setValue("1");
		
		// The first OBX has a value type of CE. So first, we populate OBX-2 with "CE"...
		obx1.getValueType().setValue("MN");
		NM nm1 = new NM(oru);
		nm1.setValue("37.5");
		obx1.getObservationValue(0).setData(nm1);
		
		// OBX|2|NM|8867-4^PULSO CORPORAL^LN|1|70|{beats}/min^PULSO POR MINUTO^UCUM|||||N|
		OBX obx2 = orderObservation.getOBSERVATION(1).getOBX();
		obx2.getSetIDOBX().setValue("2");
		obx2.getObservationIdentifier().getIdentifier().setValue("8867-4");
		obx2.getObservationIdentifier().getText().setValue("PULSO CORPORAL");
		obx2.getObservationIdentifier().getNameOfCodingSystem().setValue("LN");
		obx2.getUnits().getIdentifier().setValue("{beats}/min");
		obx2.getUnits().getText().setValue("PULSO POR MINUTO");
		obx2.getUnits().getNameOfCodingSystem().setValue("UCUM");
		obx2.getObservationResultStatus().setValue("N");
		obx2.getObservationSubID().setValue("1");
		
		// The first OBX has a value type of CE. So first, we populate OBX-2 with "CE"...
		obx2.getValueType().setValue("MN");
		NM nm2 = new NM(oru);
		nm2.setValue("70");
		obx2.getObservationValue(0).setData(nm2);
		
		// OBX|3|NM|20564-1^OXIMETRIA CORPORAL^LN|1|37.5|%^SATURACION EN SANGRE^UCUM|||||N|
		OBX obx3 = orderObservation.getOBSERVATION(2).getOBX();
		obx3.getSetIDOBX().setValue("3");
		obx3.getObservationIdentifier().getIdentifier().setValue("20564-1");
		obx3.getObservationIdentifier().getText().setValue("OXIMETRIA CORPORAL");
		obx3.getObservationIdentifier().getNameOfCodingSystem().setValue("LN");
		obx3.getUnits().getIdentifier().setValue("%");
		obx3.getUnits().getText().setValue("SATURACION EN SANGRE");
		obx3.getUnits().getNameOfCodingSystem().setValue("UCUM");
		obx3.getObservationResultStatus().setValue("N");
		obx3.getObservationSubID().setValue("1");
		
		// The first OBX has a value type of CE. So first, we populate OBX-2 with "CE"...
		obx3.getValueType().setValue("MN");
		NM nm3 = new NM(oru);
		nm3.setValue("98");
		obx3.getObservationValue(0).setData(nm3);
		

		// Now, let's encode the message and look at the output
		HapiContext context = new DefaultHapiContext();
		Parser parser = context.getPipeParser();
		String encodedMessage = parser.encode(oru);
		System.out.println("\n");
		System.out.println(encodedMessage);

	}

}
