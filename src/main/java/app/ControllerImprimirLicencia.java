package app;

import com.itextpdf.text.*;
import com.itextpdf.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import com.pdfjet.*;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import model.Licencia;
import model.Titular;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
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
    @FXML private Text textClase;
    @FXML private Text textFV;
    @FXML private AnchorPane paneLicencia;
    @FXML private AnchorPane paneDorso;
    @FXML private AnchorPane paneFrente;
    private DTOImprimirLicencia licenciaSeleccionada = null;
    @FXML private ImageView imagenLic;
    @FXML private Text dorso;
    @FXML private Text frente;

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
        dorso.setVisible(true);


        if(licenciaSeleccionada != null) {
            Optional<ButtonType> result = PanelAlerta.get(EnumTipoAlerta.CONFIRMACION,
                    "Confirmar selección del titular",
                    "",
                    "¿Desea seleccionar a " + licenciaSeleccionada.getNombre() + " " + licenciaSeleccionada.getApellido() + "?",
                    null);
            if (result.get() == ButtonType.OK) {
                textL.setText(licenciaSeleccionada.getId().toString());

                textClase.setText(licenciaSeleccionada.getClaseLicencia().getValue());


                textA.setText(licenciaSeleccionada.getTitular().getApellido());
                textN.setText(licenciaSeleccionada.getTitular().getNombre());
                textC.setText(licenciaSeleccionada.getTitular().getCalle());
                textNC.setText(licenciaSeleccionada.getTitular().getCalle());
                textFN.setText(licenciaSeleccionada.getTitular().getFechaNacimiento().toString());

            }
        }
    };

    public void ondorsoButtonClicked(){
        boolean isPresent = paneDorso.isVisible();

        paneDorso.setVisible(isPresent);
        frente.setVisible(isPresent);
        paneFrente.setVisible(!isPresent);
        dorso.setVisible(!isPresent);
    }
    public void onfrenteButtonClicked(){
        boolean isPresent = paneDorso.isVisible();

        paneDorso.setVisible(!isPresent);
        frente.setVisible(!isPresent);
        paneFrente.setVisible(isPresent);
        dorso.setVisible(isPresent);
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
            WritableImage image = paneLicencia.snapshot(spa, null);//This part takes the snapshot, here node is the anchorpane you sent
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

    @FXML
    private void volver(){
        ControllerApp.getViewAnterior();
        instance = null;
    }
}
