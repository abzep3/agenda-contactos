package gm.agenda_contactos.servicio;

import gm.agenda_contactos.modelo.Contacto;
import gm.agenda_contactos.repositorio.ContactoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoServicio implements IContactoServicio{ //esto se hace primero

    @Autowired
    private ContactoRepositorio contactoRepositorio;  //esto siempre del lado del contacto repositorio
                                                        //la inyección de dependencias

    @Override
    public List<Contacto> listarContactos() {
        List<Contacto> contactos = contactoRepositorio.findAll();
        return contactos;
    }

    @Override
    public Contacto buscarContactoPorId(Integer idContacto) {
        Contacto contacto = contactoRepositorio.findById(idContacto).orElse(null);
        return contacto;
    }

    @Override
    public void guardarContacto(Contacto contacto) {
        contactoRepositorio.save(contacto);
    }

    @Override
    public void eliminarContacto(Contacto contacto) {
        contactoRepositorio.delete(contacto);
    }
}
