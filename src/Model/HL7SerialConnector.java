package Model;

/**
 * Created by hkfuertes on 19/05/16.
 */
public class HL7SerialConnector {

/*
    @Override
    public void onLineReaded(String line) {
        if (line.startsWith(((char) 0x0b) + "")) {
            //Mandamos el anterior
            if (listener != null && current != null) {
                listener.onMessageReceived(current);
            }
            //Mensaje Nuevo
            current = new HL7Message(line);
        } else if (line.startsWith("PID")) {
            if (current != null) {
                current.addSegment(new HL7PIDSegment(line));
            }
        } else if (line.startsWith("OBR")) {
            if (current != null) {
                current.addSegment(new HL7OBRSegment(line));
            }
        } else if (line.startsWith("OBX")) {
            if (current != null) {
                current.addSegment(new HL7OBXSegment(line));
            }
        } else {
            if (current != null)
                current.addSegment(new HL7Segment(line));
        }
    }
    */

}
