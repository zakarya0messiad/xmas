package fr.univ.rouen.xmas.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import fr.univ.rouen.xmas.controllers.ToyController;
import fr.univ.rouen.xmas.dto.ItemDTO;
import fr.univ.rouen.xmas.dto.UserDTO;
import fr.univ.rouen.xmas.entities.Skill;
import fr.univ.rouen.xmas.entities.Toy;
import fr.univ.rouen.xmas.entities.User;
import fr.univ.rouen.xmas.services.ToyService;
import fr.univ.rouen.xmas.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class PdfExport {

    public static ByteArrayInputStream usersExport(List<UserDTO> users) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, output);
        document.open();
        document.addTitle("Liste_des_utilisateurs");

        PdfPTable fileNameTable = new PdfPTable(1);
        fileNameTable.setWidthPercentage(45);
        PdfPCell fileNameBox;
        fileNameBox = new PdfPCell(new Phrase("Liste des utilisateurs", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        fileNameBox.setHorizontalAlignment(Element.ALIGN_CENTER);
        fileNameBox.setVerticalAlignment(Element.ALIGN_CENTER);
        fileNameBox.setFixedHeight(20);
        fileNameTable.addCell(fileNameBox);
        document.add(fileNameTable);

        PdfPTable usersTable = new PdfPTable(4);
        usersTable.setWidthPercentage(100);

        List<String> headList = new ArrayList<>();
        headList.add("ID");
        headList.add("Nom");
        headList.add("Email");
        headList.add("Role");

        BaseColor color = new BaseColor(220, 220, 220);

        PdfPCell headCell;
        for (int i = 0; i < headList.size(); i++) {
            headCell = new PdfPCell(new Phrase(headList.get(i), FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headCell.setFixedHeight(20);
            headCell.setBackgroundColor(color);
            usersTable.addCell(headCell);
        }

        for (UserDTO user : users) {
            PdfPCell contentCell;
            contentCell = new PdfPCell(new Phrase(String.valueOf(user.getId()), FontFactory.getFont(FontFactory.HELVETICA)));
            contentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            usersTable.addCell(contentCell);


            contentCell = new PdfPCell(new Phrase(user.getName(), FontFactory.getFont(FontFactory.HELVETICA)));
            contentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            usersTable.addCell(contentCell);

            contentCell = new PdfPCell(new Phrase(user.getEmail(), FontFactory.getFont(FontFactory.HELVETICA)));
            contentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            usersTable.addCell(contentCell);

            contentCell = new PdfPCell(new Phrase(user.getRole(), FontFactory.getFont(FontFactory.HELVETICA)));
            contentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            usersTable.addCell(contentCell);
        }

        document.add(usersTable);

        document.close();

        return new ByteArrayInputStream(output.toByteArray());
    }

    public static ByteArrayInputStream itemsExport(List<ItemDTO> items) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, output);
        document.open();
        document.addTitle("Liste_des_commandes");

        PdfPTable fileNameTable = new PdfPTable(1);
        fileNameTable.setWidthPercentage(45);
        PdfPCell fileNameBox;
        fileNameBox = new PdfPCell(new Phrase("Liste des commandes", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        fileNameBox.setHorizontalAlignment(Element.ALIGN_CENTER);
        fileNameBox.setVerticalAlignment(Element.ALIGN_CENTER);
        fileNameBox.setFixedHeight(20);
        fileNameTable.addCell(fileNameBox);
        document.add(fileNameTable);

        PdfPTable itemsTable = new PdfPTable(5);
        itemsTable.setWidthPercentage(100);

        List<String> headList = new ArrayList<>();
        headList.add("ID Commande");
        headList.add("Nom Jouet");
        headList.add("Nom Lutin");
        headList.add("Temps");
        headList.add("Fini");


        BaseColor color = new BaseColor(220, 220, 220);

        PdfPCell headCell;
        for (int i = 0; i < headList.size(); i++) {
            headCell = new PdfPCell(new Phrase(headList.get(i), FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headCell.setFixedHeight(20);
            headCell.setBackgroundColor(color);
            itemsTable.addCell(headCell);
        }

        for (ItemDTO item : items) {
            PdfPCell contentCell;
            contentCell = new PdfPCell(new Phrase(String.valueOf(item.getId()), FontFactory.getFont(FontFactory.HELVETICA)));
            contentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemsTable.addCell(contentCell);

            //Toy toy = toyService.getToyById(item.getToy().ge());
            contentCell = new PdfPCell(new Phrase(String.valueOf(item.getToy().getName()), FontFactory.getFont(FontFactory.HELVETICA)));
            contentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemsTable.addCell(contentCell);

            //User user = userService.getUserById(item.getUser().getId());
            contentCell = new PdfPCell(new Phrase(String.valueOf(item.getUser().getName()), FontFactory.getFont(FontFactory.HELVETICA)));
            contentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemsTable.addCell(contentCell);

            contentCell = new PdfPCell(new Phrase(String.valueOf(item.getProcessTime()), FontFactory.getFont(FontFactory.HELVETICA)));
            contentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemsTable.addCell(contentCell);

            contentCell = new PdfPCell(new Phrase(String.valueOf(item.getFinished()), FontFactory.getFont(FontFactory.HELVETICA)));
            contentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemsTable.addCell(contentCell);
        }

        document.add(itemsTable);

        document.close();

        return new ByteArrayInputStream(output.toByteArray());
    }
}
