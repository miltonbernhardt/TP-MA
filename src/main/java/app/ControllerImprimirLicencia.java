package app;

import javafx.fxml.FXML;

public class ControllerImprimirLicencia {
    /*@FXML
    private void ButtonAction(ActionEvent event)throws IOException, DocumentException {

        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/nombrebasededatos", "nombre de usuario", "clave de base de datos");


            Statement estado = con.createStatement();
            ResultSet resultado = estado.executeQuery("SELECT *  FROM  usuario ");

            OutputStream file = new FileOutputStream(new File("C://impresiones//usuario.pdf"));
            Document document = new Document();


            PdfWriter.getInstance(document, file);

            document.open();
            PdfPTable tabla = new PdfPTable(6);
            Paragraph p = new Paragraph("Lista de Usuarios del sistema\n\n", FontFactory.getFont("Arial",16,Font.ITALIC,BaseColor.BLUE));

            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            document.add(new Paragraph(""));



            float[] mediaCeldas ={3.30f,3.50f,3.50f,3.70f,3.70f,3.50f};

            tabla.setWidths(mediaCeldas);
            tabla.addCell(new Paragraph("Nombre de Usuario", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Apellido", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Cedula", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Correo", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Nombre de usuario o nick", FontFactory.getFont("Arial",12)));
            tabla.addCell(new Paragraph("Clave", FontFactory.getFont("Arial",12)));



            while (resultado.next()){
                tabla.addCell(new Paragraph(resultado.getString("nombre"), FontFactory.getFont("Arial",10)));
                tabla.addCell(new Paragraph(resultado.getString("apellido"), FontFactory.getFont("Arial",10)));
                tabla.addCell(new Paragraph(resultado.getString("cedula"), FontFactory.getFont("Arial",10)));
                tabla.addCell(new Paragraph(resultado.getString("email"), FontFactory.getFont("Arial",10)));
                tabla.addCell(new Paragraph(resultado.getString("nick"), FontFactory.getFont("Arial",10)));
                tabla.addCell(new Paragraph(resultado.getString("clave"), FontFactory.getFont("Arial",10)));



            }

            document.add(tabla);
            document.close();
            file.close();


        }

        catch (SQLException e){
            e.printStackTrace();
        }


        try{
            File file = new File("C://impresiones//usuario.pdf");
            Desktop.getDesktop().open(file);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }*/
}
