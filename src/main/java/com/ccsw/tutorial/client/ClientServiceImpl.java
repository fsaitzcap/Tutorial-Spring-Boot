package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.exception.ConflictException;
import com.ccsw.tutorial.common.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccsw
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Client get(Long id) {

        return this.clientRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findAll() {

        return (List<Client>) this.clientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, ClientDto dto) throws ConflictException {
        Client client;

        if (id == null) {
            if (clientRepository.existsByName(dto.getName())) {
                throw new ConflictException("Error: Nombre de cliente ya en uso");
            }
            client = new Client();
        } else {
            client = this.clientRepository.findById(id).orElse(null);

            if (clientRepository.existsByName(dto.getName()) &&
            !client.getName().equals(dto.getName())) {
                throw new ConflictException("Error: Nombre de cliente en ya en uso, edite con otro nombre");
            }
        }

        assert client != null;
        client.setName(dto.getName());
        this.clientRepository.save(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws NotFoundException {
        if (this.clientRepository.findById(id).orElse(null) == null) {
            throw new NotFoundException("Error: El cliente a borrar no existe");
        }
        this.clientRepository.deleteById(id);
    }
}
