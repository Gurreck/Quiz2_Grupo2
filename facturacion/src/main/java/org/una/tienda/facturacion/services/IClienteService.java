package org.una.tienda.facturacion.services;


import java.util.List;
import java.util.Optional;
import org.una.tienda.facturacion.dtos.ClienteDTO;

public interface IClienteService {

    public Optional<List<ClienteDTO>> findAll();

    public Optional<ClienteDTO> findById(Long id);

    public ClienteDTO create(ClienteDTO cliente);

    public Optional<ClienteDTO> update(ClienteDTO cliente, Long id);

    public void delete(Long id);

    public void deleteAll();

}
