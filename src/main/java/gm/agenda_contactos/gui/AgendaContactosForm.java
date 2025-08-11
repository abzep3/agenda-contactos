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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private Integer idContacto;

    @Autowired
    public AgendaContactosForm(ContactoServicio contactoServicio){
        this.contactoServicio = contactoServicio;
        iniciarForm();
        guardarButton.addActionListener(e -> guardarContacto());
        limpiarButton.addActionListener(e -> limpiarFormulario());
        eliminarButton.addActionListener(e -> eliminarContacto());
        contactosTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarContactosSeleccionados();
            }
        });


    }

    private void iniciarForm(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloContactos = new DefaultTableModel(0, 5){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
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
        contacto.setNombre(nombre);
        contacto.setTelefono(telefono);
        contacto.setEmail(email);
        contacto.setDireccion(direccion);
        this.contactoServicio.guardarContacto(contacto); //insertar los datos en la lista
        limpiarFormulario();
        listarContactos();
    }

    private void eliminarContacto(){
        var renglon = contactosTabla.getSelectedRow();
        if(renglon!= -1){
            var idContactoStr = contactosTabla.getModel().getValueAt(renglon, 0).toString();
            this.idContacto = Integer.parseInt(idContactoStr);
            var contacto = new Contacto();
            contacto.setId(this.idContacto);
            contactoServicio.eliminarContacto(contacto);
            mostrarMensaje("Contacto con id" + this.idContacto + " eliminado");
            limpiarFormulario();
            listarContactos();
        }
    }

    private void cargarContactosSeleccionados(){
        var renglon = contactosTabla.getSelectedRow();
        if(renglon != -1){
            var id = contactosTabla.getModel().getValueAt(renglon, 0).toString();
            this.idContacto = Integer.parseInt(id);
            var nombre = contactosTabla.getModel().getValueAt(renglon, 1).toString();
            this.textoNombre.setText(nombre);
            var telefono = contactosTabla.getModel().getValueAt(renglon, 2).toString();
            this.textoTelefono.setText(telefono);
            var email = contactosTabla.getModel().getValueAt(renglon, 3).toString();
            this.textoEmail.setText(email);
            var direccion = contactosTabla.getModel().getValueAt(renglon, 4).toString();
            this.textoDireccion.setText(direccion);
        }
    }

    private void limpiarFormulario(){
        textoNombre.setText("");
        textoTelefono.setText("");
        textoEmail.setText("");
        textoDireccion.setText("");
        this.idContacto=null;
        this.contactosTabla.getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
