package gm.agenda_contactos.gui;

import gm.agenda_contactos.modelo.Contacto;
import gm.agenda_contactos.servicio.ContactoServicio;
import gm.agenda_contactos.servicio.IContactoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class AgendaContactosForm extends JFrame{
    private JPanel panelPrincipal;
    private JTable contactosTabla;
    private JTextField textoNombre;
    private JTextField textoTelefono;
    private JTextField textoEmail;
    private JTextField textoDireccion;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    IContactoServicio contactoServicio;
    private DefaultTableModel tablaModeloContactos;

    @Autowired
    public AgendaContactosForm(ContactoServicio contactoServicio){
        this.contactoServicio = contactoServicio;
        iniciarForm();
        guardarButton.addActionListener(e -> guardarContacto());

    }

    private void iniciarForm(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloContactos = new DefaultTableModel(0, 5);
        String[] cabeceros = {"id", "nombre", "telefono", "email", "direccion"};
        this.tablaModeloContactos.setColumnIdentifiers(cabeceros);
        this.contactosTabla = new JTable(tablaModeloContactos);
        //cargar listado de clientes
        listarContactos();
    }

    private void listarContactos(){
        this.tablaModeloContactos.setRowCount(0);
        var contactos = contactoServicio.listarContactos();
        contactos.forEach(contacto -> {
            Object[] renglonContacto = {
                    contacto.getId(),
                    contacto.getNombre(),
                    contacto.getTelefono(),
                    contacto.getEmail(),
                    contacto.getDireccion()
            };
            this.tablaModeloContactos.addRow(renglonContacto);
        });
    }

    private void guardarContacto(){
        if(textoNombre.getText().equals((""))){
            mostrarMensaje("Ingrese un nombre");
            textoNombre.requestFocusInWindow();
            return;
        }
        if(textoTelefono.getText().equals((""))){
            mostrarMensaje("Ingrese un No. de Teléfono");
            textoTelefono.requestFocusInWindow();
            return;
        }
        //guardar los valores del formulario
        var nombre = textoNombre.getText();
        var telefono = textoTelefono.getText();
        var email = textoEmail.getText();
        var direccion = textoDireccion.getText();
        var contacto = new Contacto();
        contacto.getNombre();
        contacto.getTelefono();
        contacto.getEmail();
        contacto.getDireccion();
        this.contactoServicio.guardarContacto(contacto); //insertar los datos en la lista
        limpiarFormulario();
        listarContactos();
    }

    private void limpiarFormulario(){
        textoNombre.setText("");
        textoTelefono.setText("");
        textoEmail.setText("");
        textoDireccion.setText("");
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
