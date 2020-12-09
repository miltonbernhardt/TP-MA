package app;



import com.itextpdf.text.*;
import com.itextpdf.*;

import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import dto.DTOGestionTitular;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import dto.DTOEmitirLicencia;
import dto.DTOImprimirLicencia;
import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoAlerta;
import enumeration.EnumTipoDocumento;
import gestor.GestorLicencia;
import gestor.GestorTitular;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import model.Licencia;
import model.Titular;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static javafx.scene.image.Image.*;

public class ControllerImprimirLicencia {
    private static ControllerImprimirLicencia instance = null;
    private DTOImprimirLicencia dto;

    public static ControllerImprimirLicencia get() {
        if (instance == null){
            ControllerApp.setViewAnterior();
            instance = (ControllerImprimirLicencia) ControllerApp.setRoot("imprimirLic", "Licencia");
        }
        return instance;
    }
    @FXML private TextField campoTitular;
    @FXML private TextField campoId;
    @FXML private DatePicker fechaDesde;
    @FXML private DatePicker fechaHasta;
    @FXML private ComboBox<EnumClaseLicencia> CBclase;
    @FXML private TableView<DTOImprimirLicencia> tabla;
    @FXML private TableColumn<DTOImprimirLicencia, String> columnaTitular;
    @FXML private TableColumn<DTOImprimirLicencia, String> columnaId;
    @FXML private TableColumn<DTOImprimirLicencia, String> columnaClase;
    @FXML private TableColumn<DTOImprimirLicencia, String> columnaFecha;
    @FXML private TableColumn<DTOImprimirLicencia, String> columnaObser;
    @FXML private TableColumn<DTOImprimirLicencia, String> columnaVenc;
    @FXML private TableColumn<DTOImprimirLicencia, String> columnaCosto;
    @FXML private Text textL;
    @FXML private Text textA;
    @FXML private Text textN;
    @FXML private Text textFN;
    @FXML private Text textC;
    @FXML private Text textNC;
    @FXML private Text textS;
    @FXML private Text textClase;
    @FXML private Text textFV;
    @FXML private AnchorPane paneLicencia;
    private DTOImprimirLicencia licenciaSeleccionada = null;
    @FXML private Text textCosto;
    @FXML private Text textDescripC;
    @FXML private Text textObser;
    @FXML private Text textG;
    @FXML private Text textF;
    @FXML private Text textDondante;
    @FXML private Text textIdTitular;
    @FXML private Text textFE;
    @FXML private AnchorPane paneTar;

    @FXML
    private void initialize(){
        CBclase.setItems(FXCollections.observableArrayList(EnumClaseLicencia.values()));
        iniciarTabla();
        iniciarDatePicker(fechaDesde);
        iniciarDatePicker(fechaHasta);
    }
    @FXML
    /* buscarTitular() llama a la interfaz BuscarTitular y devuelve un DTOGestionTitular */
    public void buscarTitular(){
        ControllerBuscarTitular.get().setControllerImprimirLicencia(this);
    }
    /* Se obtiene el Titular seleccionado en la tabla.  */
    public void titularBuscado(DTOGestionTitular dtoGestionTitular){
      campoTitular.setText(String.valueOf(dtoGestionTitular.getIdTitular()));
        // Obtener las licencias correspondientes al titular:
       ArrayList<EnumClaseLicencia> listaLicencias = GestorLicencia.get().obtenerLicencias(dtoGestionTitular.getIdTitular());
       //Se resetea el estado del comboBox y se muestra las clases que tiene como licencia
      CBclase.getSelectionModel().clearSelection();
      CBclase.getItems().clear();
      CBclase.setPromptText("Seleccionar clase de licencia");
      listaLicencias.forEach(listaLicencia -> CBclase.getItems().add(listaLicencia));
   }

    private void iniciarDatePicker(DatePicker campoFecha) {
        campoFecha.setConverter(new StringConverter<LocalDate>()
        {
            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate)
            {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString)
            {
                if(dateString==null || dateString.trim().isEmpty())
                {
                    return null;
                }
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
    }

    private void iniciarTabla() {
        tabla.setPlaceholder(new Label("No hay licencia que mostrar."));
        columnaTitular.setCellValueFactory(new PropertyValueFactory<>("idTitular"));
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaClase.setCellValueFactory(new PropertyValueFactory<>("claseLicencia"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fechaEmision"));
        columnaObser.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        columnaVenc.setCellValueFactory(new PropertyValueFactory<>("fechaVencimiento"));
        columnaCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        tabla.setRowFactory( tv -> {
            TableRow<DTOImprimirLicencia> fila = new TableRow<>();
            fila.setOnMouseClicked(event -> {
                licenciaSeleccionada = tabla.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 2 && (! fila.isEmpty()) && licenciaSeleccionada != null ) {
                    licenciaSeleccionada = fila.getItem();
                    selectionLicencia(licenciaSeleccionada);
                }
            });
            return fila ;
        });
    }

    private void cargarTabla(List<DTOImprimirLicencia> lista) {
        tabla.getItems().clear();

        for(DTOImprimirLicencia dto:lista){
            tabla.getItems().add(dto);

        }

    }

    @FXML

    private void buscarLicencia(){
        tabla.getItems().clear();
        DTOImprimirLicencia argumentos = new DTOImprimirLicencia();
        //dto argumentos con los datos obtenidos en los campos de busqueda
        if(campoId.getText().equals("")){
            argumentos.setId(0);
        } else {
             argumentos.setId(Integer.parseInt(campoId.getText()));
        }
        if(campoTitular.getText().equals("")){
            argumentos.setIdTitular(0);
        } else {
            argumentos.setIdTitular(Integer.parseInt(campoTitular.getText()));}

        argumentos.setFechaEmision(fechaDesde.getValue());
        argumentos.setFechaEmision(fechaHasta.getValue());
        argumentos.setClaseLicencia(CBclase.getValue());
        //se procede a buscar las licencias solicitadas
        cargarTabla(GestorLicencia.get().searchLic(argumentos));
    }

    private void selectionLicencia(DTOImprimirLicencia licenciaSeleccionada){

        if(licenciaSeleccionada != null) {
            paneLicencia.setVisible(true);  //AnchorPane , "Vista previa" de la licencia
            //creo un titular con todos los datos del mismo
            Titular titular2 = GestorTitular.get().getTitular(licenciaSeleccionada.getIdTitular());
            textL.setText(String.valueOf(licenciaSeleccionada.getId()));
            textA.setText(titular2.getApellido());
            textN.setText(titular2.getNombre());
            textC.setText(titular2.getCalle());
            textCosto.setText(String.valueOf(licenciaSeleccionada.getCosto()));
            textNC.setText(titular2.getNumeroCalle().toString());
            textFN.setText(titular2.getFechaNacimiento().toString());
            textFV.setText(licenciaSeleccionada.getFechaVencimiento().toString());
            textS.setText(titular2.getSexo().toString());
            textObser.setText(licenciaSeleccionada.getObservaciones());
            textF.setText(titular2.getFactorRH().toString());
            textG.setText(titular2.getGrupoSanguineo().toString());
            textDondante.setText(titular2.getDonanteOrganos().toString());
            textDescripC.setText(licenciaSeleccionada.getClaseLicencia().getDescripcion());
            textFE.setText(licenciaSeleccionada.getFechaEmision().toString());
            textIdTitular.setText(titular2.getId().toString());
            textClase.setText(licenciaSeleccionada.getClaseLicencia().toString());
            }
    }


/*    public void onsubirFotoButtonClicked(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        File selectedFileInput = selectedFile;


        // Image imageFromProjectFolder = new Image(file.toURI().toString());

        //ByteArrayOutputStream baos = new ByteArrayOutputStream();


         //   URL imagePath = getClass().getResource(fos.toString());
        Image imageFromProjectFolder = new Image(selectedFile.toURI().toString());
          //  img.setUrl("file://" + imagePath);
        System.out.println("direccion de imagen " + selectedFile.toURI().toString());
      // ImageView imageView = new ImageView(file.toURI().toString());
        //    paneLicencia.getChildren().add(imageView);
      //  ImageIO.write(img, "png", baos);

        //Image iTextImage = Image.getInstance(baos.toByteArray());
        //url="@../Imagenes/add-user-2-128.png"
   //     iPhoto.setImage(imageView);
     //       iPhoto.setVisible(true);




    }*/

    public void imprimirLicencia() {
        // to invoke file save dialogs
        FileChooser fc= new FileChooser();
        try{
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
            fc.setTitle("Save to jpg");
            fc.setInitialFileName("licencia.png");
            File file = fc.showSaveDialog(null);
            String str = file.getAbsolutePath();
            FileOutputStream fos = new FileOutputStream(str);
            final SnapshotParameters spa = new SnapshotParameters();
            spa.setTransform(Transform.scale(1.1, 1.1));//Scaling
            File fileJ2 = new File(str);

            WritableImage image2 = paneTar.snapshot(spa, null);//toma el snapshot del AnchorPane
            BufferedImage buffImage2 = SwingFXUtils.fromFXImage(image2, null);
            ImageIO.write(buffImage2, "png", fileJ2);//escribe el snapshot en el archivo

            fos.flush();
            Desktop.getDesktop().open(fileJ2); //abre el archivo JPG creado

        }catch (Exception e) {
        e.printStackTrace();
        }

    }

    public void ButtonComprobante()throws IOException, DocumentException {

        FileChooser fc= new FileChooser();

            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));
            fc.setTitle("Save to pdf");
            fc.setInitialFileName("untitled.pdf");

            File file = fc.showSaveDialog(null);
            String str = file.getAbsolutePath();
            FileOutputStream fos = new FileOutputStream(str);

            Document document = new Document(PageSize.A4, 40, 40, 50, 50);
            try {
                PdfWriter pdfw = PdfWriter.getInstance(document, fos);

                document.open();
                //Tables
                PdfPTable table = new PdfPTable(2);
                PdfPTable table2 = new PdfPTable(2);
                PdfPTable tablaTitular = new PdfPTable(1);
                PdfPTable tablaLicencia = new PdfPTable(1);
                PdfPTable cello = new PdfPTable(2);

                // set table width a percentage of the page width
                table.setWidthPercentage(100f);
                table2.setWidthPercentage(100f);
                tablaTitular.setWidthPercentage(100f);
                tablaLicencia.setWidthPercentage(100f);
                cello.setWidthPercentage(100f);

                // TOP
                Paragraph p = new Paragraph("Seguridad Vial              -             Ministerio de Transporte              -             Provincia de Santa Fe" , FontFactory.getFont("Arial", 12, Font.UNDERLINE, BaseColor.BLACK));

                p.setAlignment(Element.ALIGN_LEFT);

                Paragraph p2 = new Paragraph("Boleta de pago ", FontFactory.getFont("Arial", 16, Font.BOLD, BaseColor.BLACK));

                Paragraph p3 = new Paragraph( "N° de Licencia: " + textL.getText() + "                ------------------                 " + "Fecha de pago: " + LocalDate.now(), FontFactory.getFont("Arial",14, Font.NORMAL, BaseColor.BLACK));
                p2.setAlignment(Element.ALIGN_CENTER);
                p3.setAlignment(Element.ALIGN_LEFT);

                // Codigo QR y barra
                String textCodigoQR = "Nombre: " + textN.getText() + " Apellido: " + textA.getText() + "- Id: " + textIdTitular.getText() + " Domicilio: " + textC.getText() + textNC.getText();
                BarcodeQRCode codigoBarrasQR = new BarcodeQRCode(textCodigoQR, 200, 200, null);
                com.itextpdf.text.Image img;
                 BarcodeEAN codeEAN = new BarcodeEAN();
                //Seteo el tipo de codigo
                codeEAN.setCodeType(Barcode.EAN8);
                //Setep el codigo
                codeEAN.setCode(textL.getText() +"0" + textIdTitular.getText() + "0");
                codeEAN.setBarHeight(codeEAN.getSize() * 4f);
                //Paso el codigo a imagen
                img = codeEAN.createImageWithBarcode( pdfw.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);

                tablaTitular.addCell(getHeaderCell("Datos del Titular "));
                table.addCell(getFirstRowFormatted("Nombre: " + textN.getText()));
                table.addCell(getFirstRowFormatted("Apellido: " + textA.getText()));
                table.addCell(getFirstRowFormatted("Fecha de Nacimiento: " + textFN.getText()));
                table.addCell(getFirstRowFormatted("Domicilio: " + textC.getText() + textNC.getText()));
                table.addCell(getFirstRowFormatted("Sexo: " + textS.getText()));
                table.addCell(getFirstRowFormatted("Observaciones: " + textObser.getText()));

                tablaLicencia.addCell(getHeaderCell("Datos de la licencia "));
                table2.addCell(getFirstRowFormatted("Clase: " + textClase.getText()));
                table2.addCell(getFirstRowFormatted("Descripción: " + textDescripC.getText()));
                table2.addCell(getFirstRowFormatted("Fecha Emision: " + textFE.getText()));
                table2.addCell(getFirstRowFormatted("Fecha Vencimiento: " + textFV.getText()));
                table2.addCell(getFirstRowFormatted("Importe: " + textCosto.getText()));
                table2.addCell(getFirstRowFormatted("Organizacion Seguridad Vial"));

                img.setAlignment(Element.ALIGN_LEFT);
                cello.addCell("Cello de la Organización:  " );
                cello.addCell(img);

                com.itextpdf.text.Image image =codigoBarrasQR.getImage();
                image.setAlignment(Element.ALIGN_MIDDLE);

                //se agregan los elementos a documento en orden, agregando tambien satos de linea
                document.add(p); // ministerio...
                document.add(Chunk.NEWLINE);
                document.add(p2); // bolet
                document.add(new Paragraph("\n"));
                document.add(new LineSeparator());
                document.add(Chunk.NEWLINE);
                document.add(p3); // id fecha
                document.add(image); //codigo QR
                document.add(new Paragraph("\n"));
                document.add(tablaTitular);
                document.add(table);
                document.add(Chunk.NEWLINE);
                document.add(tablaLicencia);
                document.add(table2);
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
                document.add(new DottedLineSeparator());
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
                document.add(cello);

                document.close();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    // cell format
    private PdfPCell getHeaderCell(String text) {
        PdfPCell headerCell = new PdfPHeaderCell();

        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        Paragraph p5 = new Paragraph(text);
        p5.setAlignment(Element.ALIGN_CENTER);
        headerCell.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED_ALL);
        headerCell.setVerticalAlignment(PdfPCell.ALIGN_JUSTIFIED_ALL);
        headerCell.addElement(p5);

        return headerCell;

    }

    private PdfPCell getFirstRowFormatted(String text) {
        PdfPCell pdfpCell = new PdfPCell();
        pdfpCell.addElement(new Paragraph(text));

        return pdfpCell;
    }

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }

    @FXML
    private void cerrar(){
        campoId.clear();
        campoTitular.clear();
        tabla.getItems().clear();
        fechaHasta.getEditor().clear();
        fechaDesde.getEditor().clear();
        CBclase.setItems(FXCollections.observableArrayList(EnumClaseLicencia.values()));
        paneLicencia.setVisible(false);
    }
}
