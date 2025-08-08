package gm.agenda_contactos;

import com.formdev.flatlaf.FlatDarculaLaf;
import gm.agenda_contactos.gui.AgendaContactosForm;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class AgendaContactosSwing {
    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        //instancia de la fabrica Spring
        ConfigurableApplicationContext contextoSpring =
                new SpringApplicationBuilder(AgendaContactosSwing.class)
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);
                //crear un objeto swing
        SwingUtilities.invokeLater(() -> {
            AgendaContactosForm agendaContactosForm = contextoSpring.getBean(AgendaContactosForm.class);
            agendaContactosForm.setVisible(true);
        });
    }
}
