package knight.arkham.practica10.controladores;

import knight.arkham.practica10.modelos.Familia;
import knight.arkham.practica10.servicios.FamiliaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/familia")
public class FamiliaController {

    // Trabajar con familia para ya terminar un equipo completo

    @Autowired
    private FamiliaService familiaService;

    @RequestMapping("/")
    public String index(Model model, Principal principal){

        //Indicando el modelo que será pasado a la vista.
        model.addAttribute("titulo", "Electrodomesticos CXA");
        model.addAttribute("familias", familiaService.listarFamilias());

      //  model.addAttribute("usuario", principal.getName());

        return "/freemarker/familia";
    }


    @RequestMapping("/creacion")
    public String creacionFamilia(Model model){


        model.addAttribute("titulo", "Electrodomesticos CXA");
        model.addAttribute("familias", familiaService.listarFamilias());

        //Ubicando la vista desde resources/templates
        return "/freemarker/crearfamilia";
    }

    // Para controlar que se pueda mandar el valor false en subfamilia tuve
    // que indicar el required false y este me permite dejar el checkbox vacio lo que me dara un valor falso

    // El id al final me daba el siguiente: error Consider declaring it as object wrapper for the corresponding primitive type.
    // Y se soluciona basicamente cambiando long por Long
    @RequestMapping(value = "/crear", method = RequestMethod.POST)
    public String crearFamilia(Model model, @RequestParam(name = "nombre") String nombre, @RequestParam(name = "subFamilia", required = false) boolean subFamilia,@RequestParam(name = "idFamilia", required = false) Long idFamilia){

        // intentar agregar la eleccion de familia y subfamilia en el create del misma forma que utilize en alquiler


        //Ahora aqui debe de haber una eleccion, pues o se creara una familia normal o se creara una
        // subfamilia que va a tener una familia padre, por lo tanto usare un if aqui

        // Si se selecciona que no es una subfamilia aqui me creara una familia normal, este es el caso mas sencillo
        if (subFamilia == false){

            Familia familiaToCreate = new Familia(nombre,subFamilia);

            familiaService.crearFamilia(familiaToCreate);

        }


        // Este es el caso mas complejo aqui creo la subfamilia con su respectiva familia padre
        if (subFamilia == true){

            // Obtengo el id de la familia que estara asociada a la subfamilia y busco la familia mediante este
            // para asi agregarla a la subfamilia que creare, entro el familiatofind dentro de esta funcion
            // ya que afuera me daria error cuando no me llegue el id de familiapadre

            Familia familiaToFind = familiaService.encontrarFamiliaPorId(idFamilia);

            Familia familiaToCreate = new Familia(nombre,subFamilia, familiaToFind);

            familiaService.crearFamilia(familiaToCreate);

        }








        model.addAttribute("titulo", "Electrodomesticos CXA");
        model.addAttribute("mensaje","La familia ha sido creada con exito");
        model.addAttribute("ruta","familia");


        //Ubicando la vista desde resources/templates
        return "/freemarker/mensajes";
    }


    @RequestMapping("/borrar")
    public String eliminarFamilia(Model model,  @RequestParam(name = "id") long id){


        // Aqui elimino el cliente mandandole el id obtenido mediante la url en el requesparam
        familiaService.eliminarFamilia(id);

        model.addAttribute("titulo", "Electrodomesticos CXA");
        model.addAttribute("mensaje","La familia ha sido eliminada con exito");
        model.addAttribute("ruta","familia");

        //Ubicando la vista desde resources/templates
        return "/freemarker/mensajes";
    }



}
