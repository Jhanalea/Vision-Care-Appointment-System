package com.visioncare.controller;


import com.itextpdf.text.html.WebColors;
import com.visioncare.model.Appointment;
import com.visioncare.model.Patient;
import com.visioncare.service.AppointmentService;
import com.visioncare.service.PatientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.mail.MessagingException;


public class GenerateReportController implements Initializable {
    AppointmentService appointmentService;

    @FXML
    private Button appt_all_time;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void generateAllTimeReport() throws FileNotFoundException, DocumentException, SQLException {
        Document report = new Document();
        PdfWriter.getInstance(report, new FileOutputStream("/Users/jhana/Desktop/VCC-Reports/Vision Care Appointment Report-All Time-" + LocalDate.now() + ".pdf"));
        report.open();

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);

        PdfPCell tableCell;
        PdfPCell apptID;
        PdfPCell firstName;
        PdfPCell lastName;
        PdfPCell email;
        PdfPCell date;
        PdfPCell time;
        PdfPCell status;

        Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL , BaseColor.WHITE);
        Font font2 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL , new BaseColor(120, 119, 148));
        Font font3 = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL , new BaseColor(191, 7, 33));

        tableCell = new PdfPCell(new Phrase("Appt ID",font));
        tableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableCell.setBackgroundColor(new BaseColor(111, 92, 194));
        tableCell.setBorderColor(new BaseColor(111, 92, 194));
        table.addCell(tableCell);

        tableCell = new PdfPCell(new Phrase("First Name",font));
        tableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableCell.setBackgroundColor(new BaseColor(111, 92, 194));
        tableCell.setBorderColor(new BaseColor(111, 92, 194));
        table.addCell(tableCell);

        tableCell = new PdfPCell(new Phrase("Last Name",font));
        tableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableCell.setBackgroundColor(new BaseColor(111, 92, 194));
        tableCell.setBorderColor(new BaseColor(111, 92, 194));
        table.addCell(tableCell);

        tableCell = new PdfPCell(new Phrase("Email Address",font));
        tableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableCell.setBackgroundColor(new BaseColor(111, 92, 194));
        tableCell.setBorderColor(new BaseColor(111, 92, 194));
        table.addCell(tableCell);

        tableCell = new PdfPCell(new Phrase("Date",font));
        tableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableCell.setBackgroundColor(new BaseColor(111, 92, 194));
        tableCell.setBorderColor(new BaseColor(111, 92, 194));
        table.addCell(tableCell);

        tableCell = new PdfPCell(new Phrase("Time",font));
        tableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableCell.setBackgroundColor(new BaseColor(111, 92, 194));
        tableCell.setBorderColor(new BaseColor(111, 92, 194));
        table.addCell(tableCell);

        tableCell = new PdfPCell(new Phrase("Status",font));
        tableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableCell.setBackgroundColor(new BaseColor(111, 92, 194));
        tableCell.setBorderColor(new BaseColor(111, 92, 194));
        table.addCell(tableCell);

        Appointment appointment = new Appointment();
        appointmentService = new AppointmentService();
        List<Appointment> appointmentList = appointmentService.listAppointmentTable();
        for (int i =0;i<appointmentList.size();i++){
            apptID = new PdfPCell(new Phrase(String.valueOf(appointmentList.get(i).getAppointmentID()),font2));
            firstName = new PdfPCell(new Phrase(appointmentList.get(i).getAppointmentPatient().getFirstName(),font2));
            lastName = new PdfPCell(new Phrase(appointmentList.get(i).getAppointmentPatient().getLastName(),font2));
            email = new PdfPCell(new Phrase(appointmentList.get(i).getAppointmentPatient().getEmailAddress(),font2));
            date = new PdfPCell(new Phrase(appointmentList.get(i).getAppointmentDate(),font2));
            time = new PdfPCell(new Phrase(appointmentList.get(i).getAppointmentTime(),font2));
            status = new PdfPCell(new Phrase(appointmentList.get(i).getAppointmentStatus(),font2));
            if (status.getPhrase().getContent().equals("Missed")){
                status.setPhrase(new Phrase(appointmentList.get(i).getAppointmentStatus(),font3));
            }


            table.addCell(apptID);
            table.addCell(firstName);
            table.addCell(lastName);
            table.addCell(email);
            table.addCell(date);
            table.addCell(time);
            table.addCell(status);


        }

        report.add(table);
        report.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("File Downloaded");
        alert.setHeaderText("Report Saved Locally");
        alert.setContentText("Report has been successfully downloaded to your machine.");
        alert.showAndWait();

    }
    @FXML
    void handleButtonAction(ActionEvent event) throws FileNotFoundException, DocumentException, SQLException {
        if (event.getSource() == appt_all_time){
            generateAllTimeReport();
        }
    }
}
