package app;

import com.itextpdf.text.*;
import com.itextpdf.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import com.pdfjet.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ControllerImprimirLicencia {

    @FXML private AnchorPane panel;

    private static ControllerImprimirLicencia instance = null;


    public static ControllerImprimirLicencia get() {
        if (instance == null){
            ControllerApp.setViewAnterior();
            instance = (ControllerImprimirLicencia) ControllerApp.setRoot("imprimirLic", "Licencia");
        }
        return instance;
    }
    public void imprimirLicencia() throws FileNotFoundException, DocumentException {
        FileChooser fc= new FileChooser();
        try{
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));
        fc.setTitle("Save to pdf");
        fc.setInitialFileName("untitled.pdf");
        Alert alert=null;
            //alert = new Alert(Alert.AlertType.ERROR);
        File file = fc.showSaveDialog(null);
        String str = file.getAbsolutePath();
        FileOutputStream fos = new FileOutputStream(str);
        Document pdf = new Document();
            PdfWriter.getInstance(pdf, fos);
            pdf.open();
        // Page page = new Page(pdf, Letter.LANDSCAPE);
            File fileJ = new File("..TP-MA/temp/foto.png");
            //exportFileChooser.showSaveDialog(alert.getOwner());
            fileJ.getParentFile().mkdirs();//crete temp directory if not available
            final SnapshotParameters spa = new SnapshotParameters();
            spa.setTransform(javafx.scene.transform.Transform.scale(0.71, 1.1));//Scaling only if required i.e., the screenshot is unable to fit in the page
            WritableImage image = panel.snapshot(spa, null);//This part takes the snapshot, here node is the anchorpane you sent
            BufferedImage buffImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(buffImage, "png", fileJ);//Writes the snapshot to file


         ///   BufferedImage bufferedImage = null;

        //    bufferedImage = ImageIO.read(new ByteArrayInputStream(Document pdf));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buffImage, "png", baos);
            Image iTextImage = Image.getInstance(baos.toByteArray());

           pdf.add(iTextImage);


            pdf.close();
            fos.flush();
            Desktop.getDesktop().open(file);

       /* PdfWriter.getInstance(document, file);

        //document.open();
          //  document.add(new Paragraph("Esto es el primer párrafo, normalito"));
        //Alert alert=null;
        //alert = new Alert(Alert.AlertType.ERROR);
        //FileChooser exportFileChooser = new FileChooser(); //The folder selector to save the pdf
       // exportFileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());
        //exportFileChooser.setInitialFileName("HOLI");
        File fileJ = new File("../desktop/HOLI.png");
        //exportFileChooser.showSaveDialog(alert.getOwner());
        fileJ.getParentFile().mkdirs();//crete temp directory if not available
        final SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(javafx.scene.transform.Transform.scale(0.71, 1.1));//Scaling only if required i.e., the screenshot is unable to fit in the page
        WritableImage image = panel.snapshot(spa, null);//This part takes the snapshot, here node is the anchorpane you sent
        BufferedImage buffImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(buffImage, "png", fileJ);//Writes the snapshot to file
        document.add((Element) fileJ);
        document.close();
        file.close();*/
        }catch (Exception e) {
        e.printStackTrace();
        }

        /*try{
         //   ImageIO.write(buffImage, "png", file);//Writes the snapshot to file
            File file = new File("C:/impresiones/archiv.pdf");
            Desktop.getDesktop().open(file);
        }catch (Exception e) {
            e.printStackTrace();
        }*/
    }
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
