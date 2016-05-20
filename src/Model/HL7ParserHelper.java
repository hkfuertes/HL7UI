package Model;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.PipeParser;

/**
 * Created by hkfuertes on 20/05/16.
 */
public class HL7ParserHelper {

    public static Message parse(String message) {
        Message msg = null;
        HapiContext context = new DefaultHapiContext();
        PipeParser parser = context.getPipeParser();
        try {
            msg = parser.parse(message);
        } catch (HL7Exception e) {
            e.printStackTrace();
        }
        return msg;
    }
}
