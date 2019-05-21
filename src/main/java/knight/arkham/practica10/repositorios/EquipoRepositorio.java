package knight.arkham.practica10.repositorios;

import knight.arkham.practica10.modelos.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepositorio extends JpaRepository<Equipo, Long> {

    Equipo findEquipoById(long id);
}
