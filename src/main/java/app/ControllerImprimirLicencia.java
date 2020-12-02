package app;



import com.itextpdf.text.*;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dto.DTOGestionTitular;
import dto.DTOImprimirLicencia;
import enumeration.EnumClaseLicencia;
import enumeration.EnumTipoAlerta;
import gestor.GestorLicencia;
import gestor.GestorTitular;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import model.Titular;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    @FXML private Text dorso;
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

    public void seleccionarTitular(DTOGestionTitular dtoGestionTitular){
        System.out.println("numero id " + dtoGestionTitular.getIdTitular());
        this.dto = new DTOImprimirLicencia();
        dto.setIdTitular(dtoGestionTitular.getIdTitular());
        dto.setFechaNacimiento(dtoGestionTitular.getFechaNacimiento());
        dto.setNombre(dtoGestionTitular.getNombre());
        dto.setApellido(dtoGestionTitular.getApellido());
        dto.setTipoDocumento(dtoGestionTitular.getTipoDocumento());
        dto.setDocumento(dtoGestionTitular.getDocumento());
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
        paneLicencia.setVisible(true);
        paneFrente.setVisible(true);
        paneDorso.setVisible(true);
        Titular titular2 = new Titular();
        titular2 = GestorTitular.get().getTitular(licenciaSeleccionada.getIdTitular());

        if(licenciaSeleccionada != null) {
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
        Document pdf = new Document();
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

        // Page page = new Page(pdf, Letter.LANDSCAPE);
          File fileJ = new File("..TP-MA/temp/foto.png");


            //exportFileChooser.showSaveDialog(alert.getOwner());
       //     fileJ.getParentFile().mkdirs();//crete temp directory if not available




         ///   BufferedImage bufferedImage = null;

        //    bufferedImage = ImageIO.read(new ByteArrayInputStream(Document pdf));



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


    @FXML
    private void ButtonComprobante()throws IOException, DocumentException {

        FileChooser fc= new FileChooser();
        try {
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));
            fc.setTitle("Save to pdf");
            fc.setInitialFileName("untitled.pdf");

            Alert alert = null;
            //alert = new Alert(Alert.AlertType.ERROR);
            File file = fc.showSaveDialog(null);
            String str = file.getAbsolutePath();
            FileOutputStream fos = new FileOutputStream(str);


            Document document = new Document();


            PdfWriter pdfw = PdfWriter.getInstance(document, fos);

            document.open();
            PdfPTable tabla = new PdfPTable(6);
            Paragraph p = new Paragraph("Comprobante Licencia\n\n", FontFactory.getFont("Arial", 16, Font.ITALIC, BaseColor.BLUE));

            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            document.add(new Paragraph(""));


            float[] mediaCeldas = {3.30f, 3.50f, 3.50f, 3.70f, 3.70f, 3.50f};

            tabla.setWidths(mediaCeldas);
            tabla.addCell(new Paragraph("Nombre de Titular", FontFactory.getFont("Arial", 12)));
            tabla.addCell(new Paragraph("Apellido", FontFactory.getFont("Arial", 12)));
            tabla.addCell(new Paragraph("Cedula", FontFactory.getFont("Arial", 12)));
            tabla.addCell(new Paragraph("Fecha Nac", FontFactory.getFont("Arial", 12)));
            tabla.addCell(new Paragraph("Domicilio", FontFactory.getFont("Arial", 12)));
            tabla.addCell(new Paragraph("Nacionalidad", FontFactory.getFont("Arial", 12)));
            com.itextpdf.text.Image img;
            //Es el tipo de clase
            BarcodeEAN codeEAN = new BarcodeEAN();
            //Seteo el tipo de codigo
            codeEAN.setCodeType(Barcode.EAN13);
            //Setep el codigo
            codeEAN.setCode("9781935182610");
            //Paso el codigo a imagen
            img = codeEAN.createImageWithBarcode( pdfw.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);

            document.add(tabla);
            document.close();
            Desktop.getDesktop().open(file);

        } catch (Exception e){
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

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }
}
