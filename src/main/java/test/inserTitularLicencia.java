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
        //Creo un nuevo titular
        Titular t1 = new Titular(EnumTipoDocumento.DNI,
                "40000001",
                "PruebaTipoD",
                "caseD5",
                LocalDate.of(1950, Month.MARCH, 21),
                EnumGrupoSanguineo.GRUPO_0,
                EnumFactorRH.FACTOR_POSITIVO,
                true, EnumSexo.MASCULINO);
        Licencia l1 = new Licencia(t1,
                                    EnumClaseLicencia.CLASE_D,
                                    LocalDate.of(2014, Month.FEBRUARY, 01),
                                    LocalDate.of(2018, Month.AUGUST, 12));
        Licencia l2 = new Licencia(t1,
                EnumClaseLicencia.CLASE_B,
                LocalDate.of(2010, Month.FEBRUARY, 01),
                LocalDate.of(2014, Month.AUGUST, 12));
        //Agrego licencia al titular
        t1.getLicencias().add(l1);
        t1.getLicencias().add(l2);
        //Persisto en Base de Datos
        DAO.get().save(t1);
        System.out.println("exit successful");
    }
}