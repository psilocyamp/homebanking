
//aca creo un repositorio para administrar las instancias de esa clase en la base de datos
//desp marcó la clase de repositorio con el @Repositorio anotación para indicar que cumple con el patrón de diseño del Repositorio, por lo que actúa como puntos de acceso a los datos en una aplicación.

package com.mindhub.homebanking.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.mindhub.homebanking.models.Client;
import org.springframework.stereotype.Repository;

@Repository  //proporciona una funcionalidad para que la clase funcione como repositorio, y se le considerara como una base de datos
//Repositorio Jpa es parte de Spring Data JPA y proporciona métodos CRUD (Crear, Leer, Actualizar, Eliminar)
public interface ClientRepository extends JpaRepository<Client, Long>{  //long seria la clave primaria, client interfaz con la q esta asociado el repo
} //client repository oara guardar el repo


//Métodos proporcionados por JpaRepository:
//findAll: Devuelve todos los registros de la entidad.
//findById: Busca un registro por su identificador.
//save: Guarda una entidad, creando una nueva o actualizando una existente.
//deleteById: Elimina un registro por su identificador.
//count: Devuelve el número total de registros.
//existsById: Verifica si existe un registro con el identificador dado.


