package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.exception.ConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void findAllshouldReturnAllClients() {
        List<Client> list = new ArrayList<>();
        list.add(mock(Client.class));

        when(clientRepository.findAll()).thenReturn(list);
        List<Client> clients = clientService.findAll();

        assertNotNull(clients);
        assertEquals(1, clients.size());
    }

    public static final String CLIENT_NAME = "NAME1";

    @Test
    public void saveNotExistsClientIdShouldInsert() throws ConflictException {


        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        ArgumentCaptor<Client> client = ArgumentCaptor.forClass(Client.class);

        clientService.save(null, clientDto);

        verify(clientRepository).save(client.capture());

        assertEquals(CLIENT_NAME, client.getValue().getName());
    }


    @Test
    public void saveExistingClientShouldNotSave() {
        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        when(clientRepository.existsByName(CLIENT_NAME)).thenReturn(true);

        assertThrows(ConflictException.class, () -> clientService.save(null, clientDto),
                "Nombre de cliente ya en uso");

        verify(clientRepository).existsByName(CLIENT_NAME);
        verify(clientRepository, never()).save(any(Client.class));

    }

    public static final Long EXISTS_CLIENT_ID = 1L;

    @Test
    public void saveExistsClientIdShouldUpdate() throws ConflictException {

        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        Client client = mock(Client.class);
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        clientService.save(EXISTS_CLIENT_ID, clientDto);

        verify(clientRepository).save(client);
    }

    public static final String EXISTING_CLIENT_NAME = "NAME1";

    /*
        @Test
        public void saveExistClientWithSameNameSouldNotUpdate() {

            ClientDto clientDto = new ClientDto();
            clientDto.setName(CLIENT_NAME);

            Client existingClient = mock(Client.class);
            existingClient.setName(CLIENT_NAME);

            Client client = mock(Client.class);

            when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

            when(clientRepository.existsByName(CLIENT_NAME)).thenReturn(Optional.of(existingClient));

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                clientService.save(EXISTS_CLIENT_ID, clientDto);
            });

            assertEquals("Nombre de cliente en ya en uso, edite con otro nombre", exception.getMessage());

            verify(clientRepository).existsByName(CLIENT_NAME);
            verify(clientRepository, never()).save(any(Client.class));

            //clientService.save(EXISTS_CLIENT_ID, clientDto);

            //verify(clientRepository).save(client);
        }
    */
    @Test
    public void deleteExistsClientIdShouldDelete() throws Exception {

        Client client = mock(Client.class);
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        clientService.delete(EXISTS_CLIENT_ID);

        verify(clientRepository).deleteById(EXISTS_CLIENT_ID);
    }

}
