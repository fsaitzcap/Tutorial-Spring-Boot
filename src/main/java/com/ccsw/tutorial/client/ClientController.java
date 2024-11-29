package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.exception.ConflictException;
import com.ccsw.tutorial.common.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ccsw
 */
@Tag(name = "Client", description = "API of Client")
@RequestMapping(value = "/client")
@RestController
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar todas las {@link Client}
     *
     * @return {@link List} de {@link ClientDto}
     */
    @Operation(summary = "Find", description = "Method that return a list of Client")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<ClientDto> findAll() {
        List<Client> clients = this.clientService.findAll();

        return clients.stream()
                .sorted(Comparator.comparing(Client::getId))
                .map(e -> mapper.map(e, ClientDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Método para crear o actualizar una {@link Client}
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Client")
    @RequestMapping(path = {"", "/{id}"}, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody ClientDto dto) throws ConflictException {
        this.clientService.save(id, dto);
    }

    /**
     * Método para borrar una {@link Client}
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Client")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws NotFoundException {
        this.clientService.delete(id);
    }
}