package test;

import enumeration.*;
import hibernate.DAO;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Licencia;
import model.Titular;

import java.time.LocalDate;
import java.time.Month;

public class inserTitularLicencia extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Titular t1 = new Titular(EnumTipoDocumento.DNI,
                "40000001",
                "PruebaTipoG",
                "caseG1",
                LocalDate.of(2000, Month.MARCH, 21),
                EnumGrupoSanguineo.GRUPO_0,
                EnumFactorRH.FACTOR_POSITIVO,
                true, EnumSexo.MASCULINO);
        Licencia l1 = new Licencia(t1,
                EnumClaseLicencia.CLASE_G,
                LocalDate.of(2020, Month.FEBRUARY, 01),
                LocalDate.of(2024, Month.AUGUST, 12));
        //Agrego licencia al titular
        t1.getLicencias().add(l1);
        //t1.getLicencias().add(l2);
        //Persisto en Base de Datos
        DAO.get().save(t1);
        System.out.println("exit successful");
        System.exit(0);
    }
}