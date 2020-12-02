package app;



import com.itextpdf.text.*;
import com.itextpdf.*;

import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import dto.DTOBuscarTitular;
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
    @FXML private DatePicker campoFecha;
    @FXML private ComboBox<EnumClaseLicencia> CBclase;
    @FXML private TableView<DTOImprimirLicencia> tabla;
    @FXML private TableColumn<DTOImprimirLicencia, String> columnaTitular;
    @FXML private TableColumn<DTOImprimirLicencia, String> columnaId;
    @FXML private TableColumn<DTOImprimirLicencia, String> columnaClase;
    @FXML private TableColumn<DTOImprimirLicencia, String> columnaFecha;
    @FXML private TableColumn<DTOImprimirLicencia, String>  columnaObser;
    @FXML private TableColumn<DTOImprimirLicencia, String> columnaVenc;
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
    @FXML private AnchorPane paneDorso;
    @FXML private AnchorPane paneFrente;
    private DTOImprimirLicencia licenciaSeleccionada = null;
    @FXML private ImageView iPhoto;
    @FXML private Button imprimirComprobante;
    @FXML private Button imprimirLic;
    @FXML private Text frente;
    @FXML private Text textDescripC;
    @FXML private Text textObser;
    @FXML private Text textG;
    @FXML private Text textF;
    @FXML private Text textDondante;

    @FXML

    private void initialize(){
        CBclase.setItems(FXCollections.observableArrayList(EnumClaseLicencia.values()));
        iniciarTabla();
        iniciarDatePicker(campoFecha);
    }
    @FXML
    public void buscarTitular(){
        ControllerBuscarTitular.get().setControllerImprimirLicencia(this);
    }

    public void seleccionarTitular(DTOBuscarTitular dtoBuscarTitular){
        System.out.println("numero id " + dtoBuscarTitular.getIdTitular());
        this.dto = new DTOImprimirLicencia();
        dto.setIdTitular(dtoBuscarTitular.getIdTitular());
        dto.setFechaNacimiento(dtoBuscarTitular.getFechaNacimiento());
        dto.setNombre(dtoBuscarTitular.getNombre());
        dto.setApellido(dtoBuscarTitular.getApellido());
        dto.setTipoDocumento(dtoBuscarTitular.getTipoDocumento());
        dto.setDocumento(dtoBuscarTitular.getDocumento());
        System.out.println("numero id " + dto.getIdTitular());
        System.out.println("nombre"  + dto.getTitular());
        campoTitular.setText(dto.getIdTitular().toString());
       ArrayList<EnumClaseLicencia> listaLicencias = GestorLicencia.get().obtenerLicencias(dto.getIdTitular());

        //Se resetea el estado del comboBox y se muestra las clases que tiene como licencia
      CBclase.getSelectionModel().clearSelection();
      CBclase.getItems().clear();
      CBclase.setPromptText("Seleccionar clase de licencia");

        listaLicencias.forEach(listaLicencia -> CBclase.getItems().add(listaLicencia));
       // int cantidadClasesLicencia = listaLicencias.size();


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
            if(dto.getIdTitular().equals(0)){
                dto.setIdTitular(dto.getTitular().getId());

            }


        }

    }

    @FXML
    private void buscarLicencia(){
        tabla.getItems().clear();
        DTOImprimirLicencia argumentos = new DTOImprimirLicencia();

        if(campoId.getText().equals("")){
            System.out.println("entra al if vacio ");
            argumentos.setId(0);
        } else {
            System.out.println("entra para colocar valor"+ Integer.parseInt(campoId.getText()));
            argumentos.setId(Integer.parseInt(campoId.getText()));}


        if(campoTitular.getText().equals("")){
            System.out.println("entra al if vacio de titular ");
            argumentos.setIdTitular(0);
        } else {
            System.out.println("entra para colocar valor al titular "+ Integer.parseInt(campoTitular.getText()));
            argumentos.setIdTitular(Integer.parseInt(campoTitular.getText()));}

       //System.out.println("campo id " + campoId.getText());

        argumentos.setFechaEmision(campoFecha.getValue());
        argumentos.setClaseLicencia(CBclase.getValue());



        cargarTabla(GestorLicencia.get().searchLic(argumentos));
    }

    private void selectionLicencia(DTOImprimirLicencia licenciaSeleccionada){

        if(licenciaSeleccionada != null) {
            paneLicencia.setVisible(true);
            Titular titular2 = GestorTitular.get().getTitular(licenciaSeleccionada.getIdTitular());
            imprimirComprobante.setOnAction(event -> {
                try {
                    ButtonComprobante(licenciaSeleccionada , titular2);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            });


            Optional<ButtonType> result = PanelAlerta.get(EnumTipoAlerta.CONFIRMACION,
                    "Confirmar selección del titular",
                    "",
                    "¿Desea seleccionar a " + licenciaSeleccionada.getNombre() + " " + licenciaSeleccionada.getApellido() + "?",
                    null);
            if (result.get() == ButtonType.OK) {
                textL.setText(licenciaSeleccionada.getId().toString());

                textClase.setText(licenciaSeleccionada.getClaseLicencia().getValue());


                textA.setText(titular2.getApellido());
                textN.setText(titular2.getNombre());
                textC.setText(titular2.getCalle());

                textNC.setText(titular2.getNumeroCalle().toString());
                textFN.setText(titular2.getFechaNacimiento().toString());
                textFV.setText(licenciaSeleccionada.getFechaVencimiento().toString());
                textS.setText(titular2.getSexo().toString());
                textObser.setText(licenciaSeleccionada.getObservaciones());
                textF.setText(titular2.getFactorRH().toString());
                textG.setText(titular2.getGrupoSanguineo().toString());
                textDondante.setText(titular2.getDonanteOrganos().toString());

            }
        }
    };

    public void onsubirFotoButtonClicked(){
/*
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


*/

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
            Rectangle pagesize = new Rectangle(105 ,148);
            Document pdf = new Document(pagesize);

            PdfWriter.getInstance(pdf, fos);
        pdf.open();
        final SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(1.1, 1.1));//Scaling only if required i.e., the screenshot is unable to fit in the page
        File fileJ2 = new File("..TP-MA/temp/foto2.png");


            WritableImage image2 = paneLicencia.snapshot(spa, null);//This part takes the snapshot, here node is the anchorpane you sent
            BufferedImage buffImage2 = SwingFXUtils.fromFXImage(image2, null);
            ImageIO.write(buffImage2, "png", fileJ2);//Writes the snapshot to file
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buffImage2, "png", baos);

            com.itextpdf.text.Image iTextImage = com.itextpdf.text.Image.getInstance(baos.toByteArray());
        pdf.add(iTextImage);


         //   Image iTextImage2 = Image.getInstance(baos.toByteArray());

           // pdf.add(iTextImage2);

        //Page page = new Page(pdf, Letter.LANDSCAPE);
          File fileJ = new File("..TP-MA/temp/foto.png");


            //exportFileChooser.showSaveDialog(alert.getOwner());
       //     fileJ.getParentFile().mkdirs();//crete temp directory if not available




         ///   BufferedImage bufferedImage = null;

        //    bufferedImage = ImageIO.read(new ByteArrayInputStream(Document pdf));



            pdf.close();
            fos.flush();
            Desktop.getDesktop().open(fileJ2);


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

    private void ButtonComprobante(DTOImprimirLicencia licenciaSeleccionada, Titular titular)throws IOException, DocumentException {

        FileChooser fc= new FileChooser();

            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));
            fc.setTitle("Save to pdf");
            fc.setInitialFileName("untitled.pdf");

            Alert alert = null;
            //alert = new Alert(Alert.AlertType.ERROR);
            File file = fc.showSaveDialog(null);
            String str = file.getAbsolutePath();
            FileOutputStream fos = new FileOutputStream(str);

            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            try {
                PdfWriter pdfw = PdfWriter.getInstance(document, fos);

                document.open();
                float[] columnWidths = {10f};
                // create PDF table with the given widths
                PdfPTable table = new PdfPTable(4);
                // set table width a percentage of the page width
                table.setWidthPercentage(90f);
                Paragraph p = new Paragraph("Comprobante Licencia\n\n", FontFactory.getFont("Arial", 18, Font.BOLD, BaseColor.BLACK));

                p.setAlignment(Element.ALIGN_LEFT);
                document.add(p);
                Paragraph p2 = new Paragraph(titular.getId().toString(), FontFactory.getFont("Arial", 18, Font.BOLD, BaseColor.BLACK));

                p2.setAlignment(Element.ALIGN_RIGHT);
                document.add(p2);

                document.add(new Paragraph(""));
                table.addCell(getHeaderCell("Datos del titular"));

                PdfPTable childTable2 = new PdfPTable(4);
                childTable2.addCell(getFirstRowFormatted("Nombre de Titular"));
                childTable2.addCell(getFirstRowFormatted(titular.getNombre()));
                childTable2.addCell(getFirstRowFormatted("Apellido"));
                childTable2.addCell(getFirstRowFormatted(titular.getApellido()));
                childTable2.addCell(getSecondRowFormatted("Dni"));
                childTable2.addCell(getSecondRowFormatted(titular.getDNI()));
                childTable2.addCell(getSecondRowFormatted("25"));
                childTable2.addCell(childTable2);
                document.add(new Paragraph(""));
                PdfPTable table2 = new PdfPTable(4);
                table2.addCell(getLastRowFormatted("Datos de la licencia"));
// Add header details
                float fl= licenciaSeleccionada.getCosto();
                System.out.println("costo " + fl);
                PdfPTable childTable1 = new PdfPTable(4);
                childTable1.addCell(getFirstRowFormatted("Fecha Emision"));
                childTable1.addCell(getFirstRowFormatted(licenciaSeleccionada.getFechaEmision().toString()));
                childTable1.addCell(getFirstRowFormatted("Fecha de Vencimiento"));
                childTable1.addCell(getFirstRowFormatted(licenciaSeleccionada.getFechaVencimiento().toString()));
                childTable1.addCell(getSecondRowFormatted("Costo"));
             //   childTable1.addCell(getSecondRowFormatted(licenciaSeleccionada.getCosto().toString()));
                childTable1.addCell(getSecondRowFormatted("Clase"));
                childTable1.addCell(getSecondRowFormatted(licenciaSeleccionada.getClaseLicencia().toString()));
                table2.addCell(childTable2);
                



                //Es el tipo de clase
                BarcodeEAN codeEAN = new BarcodeEAN();
                //Seteo el tipo de codigo
                codeEAN.setCodeType(Barcode.EAN13);
                //Setep el codigo
                codeEAN.setCode("9781935182610");
                //Paso el codigo a imagen
                com.itextpdf.text.Image img = codeEAN.createImageWithBarcode(pdfw.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);

                document.add(table);
                document.add(img);
                document.close();
                Desktop.getDesktop().open(file);

            }catch (Exception e) {
                e.printStackTrace();
            }

        /*try{
            File file = new File("C://impresiones//usuario.pdf");
            Desktop.getDesktop().open(file);
        }
        catch (Exception e){
            e.printStackTrace();
        }*/

        }



    private PdfPCell getHeaderCell(String text) {
        PdfPCell headerCell = new PdfPHeaderCell();
        headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        headerCell.addElement(new Paragraph(text));
        return headerCell;

    }

    private PdfPCell getFirstRowFormatted(String text) {
        PdfPCell pdfpCell = new PdfPCell();
        pdfpCell.addElement(new Paragraph(text));

        return pdfpCell;
    }

    private PdfPCell getSecondRowFormatted(String text) {
        PdfPCell pdfpCell = new PdfPCell();
        pdfpCell.addElement(new Paragraph(text));
        pdfpCell.setRotation(90);
        return pdfpCell;
    }
    private PdfPCell getLastRowFormatted(String text) {
        PdfPCell pdfpCell = new PdfPCell();
        pdfpCell.addElement(new Paragraph(text));
        pdfpCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        pdfpCell.setRotation(90);
        return pdfpCell;
    }

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }

    @FXML
    private void cerrar(){

        paneLicencia.setVisible(false);
    }
}
