package knight.arkham.practica10.controladores;

import knight.arkham.practica10.modelos.Equipo;
import knight.arkham.practica10.modelos.Familia;
import knight.arkham.practica10.servicios.EquipoServices;
import knight.arkham.practica10.servicios.FamiliaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/equipo")
public class EquipoController {

    @Autowired
    private EquipoServices equipoServices;

    @Autowired
    private FamiliaService familiaService;

    @RequestMapping("/")
    public String index(Model model, Principal principal){

        //Indicando el modelo que será pasado a la vista.
        model.addAttribute("titulo", "Electrodomesticos CXA");
        model.addAttribute("equipos",equipoServices.listarEquipos());

       // model.addAttribute("usuario", principal.getName());
        //Ubicando la vista desde resources/templates
        return "/freemarker/equipo";
    }


    @RequestMapping("/creacion")
    public String creacionEquipo(Model model){


        model.addAttribute("familias", familiaService.listarFamilias());
        model.addAttribute("titulo", "Electrodomesticos CXA");


        //Ubicando la vista desde resources/templates
        return "/freemarker/crearequipo";
    }


    @RequestMapping(value = "/crear", method = RequestMethod.POST)
    public String crearEquipo(Model model, @RequestParam(name = "nombre") String nombre, @RequestParam(name = "marca") String marca,@RequestParam(name = "imagenEquipo") String imagenEquipo,@RequestParam(name = "cantidadExistencia") int cantidadExistencia,@RequestParam(name = "costoAlquilerPorDia") float costoAlquilerPorDia,@RequestParam(name = "familia", required = false) Long idFamilia, @RequestParam(name = "subFamilia", required = false) Long idSubFamilia ){

       // Familia familia = familiaService.encontrarFamiliaPorId(idFamilia);
        //Familia subFamilia = familiaService.encontrarFamiliaPorId(idSubFamilia);

        // Familias y subfamilias de prueba para comprobar que se creara bien el equipo
        Familia familiaTest = new Familia("Personas",false);
        Familia subFamiliaTest = new Familia("Gente",true,familiaTest);

        familiaService.crearFamilia(familiaTest);
        familiaService.crearFamilia(subFamiliaTest);


        Equipo equipoToCreate = new Equipo(nombre,marca,imagenEquipo,cantidadExistencia,costoAlquilerPorDia,familiaTest,subFamiliaTest);

        // Aqui inserto cliente
        equipoServices.crearEquipo(equipoToCreate);

        model.addAttribute("titulo", "Electrodomesticos CXA");
        model.addAttribute("mensaje","El equipo ha sido creado con exito");
        model.addAttribute("ruta","equipo");


        //Ubicando la vista desde resources/templates
        return "/freemarker/mensajes";
    }



    @RequestMapping("/edicion")
    public String edicionEquipo(Model model,  @RequestParam(name = "id") long id ){

        //Aqui obtengo el cliente que voy a editar
        Equipo equipoToEdit = equipoServices.encontrarEquipoPorId(id);


        //Aqui le mando el cliente que editaremos a la vista de editar cliente para asi trabajar con sus datos y poder
        // modificarlos de manera correcta
        model.addAttribute("equipo",equipoToEdit);
        model.addAttribute("titulo", "Electrodomesticos CXA");


        return "/freemarker/editarequipo";
    }





    // Como tengo que obtener el cliente de la vista aqui necesito un requesparam y le mando el parametro con /?id=cliente.id
    // desde la vista hacia esta funcion mediante la url
    @RequestMapping("/editar")
    public String editarEquipo(Model model,@RequestParam(name = "id") long id, @RequestParam(name = "nombre") String nombre, @RequestParam(name = "marca") String marca,@RequestParam(name = "imagenEquipo") String imagenEquipo,@RequestParam(name = "cantidadExistencia") int cantidadExistencia,@RequestParam(name = "costoAlquilerPorDia") float costoAlquilerPorDia ){

        // Para editar el cliente primero debo de buscarlo


        // almaceno el cliente encontrado en el objeto clienteToEdit
        Equipo equipoToEdit = equipoServices.encontrarEquipoPorId(id);

        equipoToEdit.setNombre(nombre);
        equipoToEdit.setMarca(marca);
        equipoToEdit.setCantidadExistencia(cantidadExistencia);
        equipoToEdit.setImagenEquipo(imagenEquipo);
        equipoToEdit.setCostoAlquilerPorDia(costoAlquilerPorDia);

        //Le agrego los campos editados mediante las propiedades set de la clase

        // Aqui guardo el cliente de nuevo ya que .save funciona tanto como para crear nuevo o editar.
        equipoServices.crearEquipo(equipoToEdit);


        model.addAttribute("titulo", "Electrodomesticos CXA");
        model.addAttribute("mensaje","El equipo ha sido editado con exito");
        model.addAttribute("ruta","equipo");

        //Ubicando la vista desde resources/templates
        return "/freemarker/mensajes";
    }




    @RequestMapping("/borrar")
    public String eliminarEquipo(Model model,  @RequestParam(name = "id") long id){


        // Aqui elimino el cliente mandandole el id obtenido mediante la url en el requesparam
        equipoServices.eliminarEquipo(id);

        model.addAttribute("titulo", "Electrodomesticos CXA");
        model.addAttribute("mensaje","El equipo ha sido eliminado con exito");
        model.addAttribute("ruta","equipo");

        //Ubicando la vista desde resources/templates
        return "/freemarker/mensajes";
    }


}
