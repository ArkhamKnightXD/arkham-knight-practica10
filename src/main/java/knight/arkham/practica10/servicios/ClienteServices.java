package knight.arkham.practica10.servicios;

import knight.arkham.practica10.modelos.Cliente;
import knight.arkham.practica10.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServices {

    @Autowired
    private ClienteRepositorio clienteRepo;


    @Transactional
    public void crearCliente(Cliente cliente){

         clienteRepo.save(cliente);
    }

    public List<Cliente> listarClientes(){

        return clienteRepo.findAll();
    }

    public Optional<Cliente> encontrarClientePorId(long id){

        return clienteRepo.findById(id);
    }

    public void eliminarCliente(Cliente cliente){

        clienteRepo.delete(cliente);
    }
}
