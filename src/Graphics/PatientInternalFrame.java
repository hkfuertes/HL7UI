package Graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import Model.Mensaje;
import Model.ORUUtils;
import Model.Observacion;
import Model.Paciente;
import ca.uhn.hl7v2.model.v26.message.ORU_R01;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import javax.swing.JButton;

public class PatientInternalFrame extends JInternalFrame {

	private static TimeSeries ekgSeries;
	public JTextArea rawData;
	Observacion otemp, osat, opulse, oekg;
	
	String[] columnNames = {"Hora",
            "Temperatura",
            "Pulso",
            "Saturacion"};
	
	String[] graph = {"mV", "Time"};
	
	private Paciente paciente;
	private DefaultTableModel dtm;
	private JTable table;
	private TimeSeries tmpSeries;
	
	private EKGPanel ekgPanel;

	public PatientInternalFrame(Paciente paciente) {
		super(paciente.toString(),true,true,true,true);
		
		this.paciente = paciente;

		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		dtm = new DefaultTableModel();
		
		for(String name : columnNames){
			dtm.addColumn(name);
		}
		
		table = new JTable(dtm);
		ekgPanel = new EKGPanel();
		
		tmpSeries = new TimeSeries("TEMPERATURA");

		ChartPanel tmp_chart = createTempChartPanel(tmpSeries);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add(ekgPanel, "ECG");
		tabbedPane.add(tmp_chart, "TEMPERATURA");
		tabbedPane.add( new JScrollPane(table), "Datos");
		
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		this.setSize(new Dimension(500, 300));
		this.setLocation(0, 0);
	}
	
	public void printMessage(ORU_R01 message){
		Mensaje msg = ORUUtils.getMensaje(message);
		ArrayList<Observacion> obsInMsg = ORUUtils.getObservaciones(message);
		
		//Imprmimos el ecg en el panel.
		ekgPanel.printMessage(message);
		
		for(Observacion obx : obsInMsg){
			switch(obx.tipo){
			case Observacion.TEMPERATURA_LOINC:
				otemp = obx;
				tmpSeries.add(new Millisecond(msg.hora), Double.parseDouble(obx.medida));
				break;
			case Observacion.PULSO_LOINC:
				opulse = obx;
				break;
			case Observacion.SATURACION_LOINC:
				osat = obx;
				break;
			case Observacion.EKG_LOINC:
				oekg = obx;
				break;
			}
		}

		String[] row = new String[]{
				msg.hora.toString(),
				otemp != null ? otemp.toString():"", 
				opulse != null ? opulse.toString():"",
				osat != null ? osat.toString():""
				};
		dtm.addRow(row);
		otemp = null; osat = null; opulse = null;
	}
	
	public Paciente getPaciente(){
		return paciente;
	}
	
	public String toString(){
		return paciente.toString();
	}
	
	public ChartPanel createTempChartPanel(TimeSeries tempSeries){
		//http://www.jfree.org/jfreechart/api/javadoc/src-html/org/jfree/chart/demo/TimeSeriesChartDemo1.html
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"TEMPERATURA",  // title
				"Tiempo",             // x-axis label
				"Grados",   // y-axis label
				new TimeSeriesCollection(tempSeries),            // data
				false,               // create legend?
				false,               // generate tooltips?
				false               // generate URLs?
				);
		return new ChartPanel(chart);
	}	
}
