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

public class PatientInternalFrame extends JInternalFrame {

	public JTextArea rawData;
	
	String[] columnNames = {"Hora",
            "Temperatura",
            "Pulso",
            "Saturacion"};
	
	String[] graph = {"mV", "Time"};
	
	private Paciente paciente;
	private DefaultTableModel dtm;
	private JTable table;
	private JSplitPane splitPane;

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
		//splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(table),new JPanel());
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(table),new ChartPanel( createJFreeChart() ));
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		this.setSize(new Dimension(500, 300));
		this.setLocation(0, 0);
	}
	
	public void printMessage(ORU_R01 message){
		ArrayList<Observacion> obsInMsg = ORUUtils.getObservaciones(message);
		Mensaje msg = ORUUtils.getMensaje(message);
		String[] row = new String[]{msg.hora.toLocaleString(),obsInMsg.get(0).toString(), obsInMsg.get(1).toString(),obsInMsg.get(2).toString()};
		dtm.addRow(row);
	}
	
	public Paciente getPaciente(){
		return paciente;
	}
	
	public String toString(){
		return paciente.toString();
	}
	
	public JFreeChart createJFreeChart(){
		//http://www.jfree.org/jfreechart/api/javadoc/src-html/org/jfree/chart/demo/TimeSeriesChartDemo1.html
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"ECG",  // title
				graph[1],             // x-axis label
				graph[0],   // y-axis label
				createDataset(),            // data
				false,               // create legend?
				false,               // generate tooltips?
				false               // generate URLs?
				);
		return chart;
	}
	
	private static XYDataset createDataset() {
		TimeSeries s1 = new TimeSeries("L&G European Index Trust");
		for(int i = 0; i<20; i++){
			s1.addOrUpdate(new Millisecond(),181);
		}
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(s1);
		return dataset;
	}
	
}
