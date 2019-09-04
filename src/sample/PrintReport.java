package sample;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.sql.Connection;
import java.util.HashMap;

public class PrintReport extends JFrame {

    public void showReport(Connection conn, String korisnickoIme) throws JRException {
        String reportSrcFile = getClass().getResource("/reports/report.jrxml").getFile();
        String reportsDir = getClass().getResource("/reports/").getFile();

        String param = korisnickoIme;
        JasperDesign jasperDesign = JRXmlLoader.load(reportSrcFile);
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("reportsDirPath", reportsDir);
        parameters.put("param",param);

        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(500, 500);
        this.setVisible(true);
    }

}
