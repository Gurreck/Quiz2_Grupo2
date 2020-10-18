package org.una.tienda.facturacion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.una.tienda.facturacion.dtos.ClienteDTO;
import org.una.tienda.facturacion.entities.Cliente;
import org.una.tienda.facturacion.repositories.IClienteRepository;
import org.una.tienda.facturacion.utils.MapperUtils;

@Service
public class ClienteServiceImplementation implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public Optional<List<ClienteDTO>> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Optional<ClienteDTO> oneToDto(Optional<Cliente> one) {
        if (one.isPresent()) {
            ClienteDTO clienteDTO = MapperUtils.DtoFromEntity(one.get(),   ClienteDTO.class);
            return Optional.ofNullable(clienteDTO);
        } else {
            return null;
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> findById(Long id) {
        return oneToDto(clienteRepository.findById(id));
    }

    @Override
    @Transactional
    public ClienteDTO create(ClienteDTO ClienteDTO) {
        
        Cliente cliente = MapperUtils.EntityFromDto(ClienteDTO, Cliente.class);
        
        if (cliente.getDireccion() != null && cliente.getTelefono() != null && cliente.getEmail() != null) {
            
        cliente = clienteRepository.save(cliente);
        return MapperUtils.DtoFromEntity(cliente, ClienteDTO.class);
        
        }
        else {
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<ClienteDTO> update(ClienteDTO clienteDTO, Long id) {
        if (clienteRepository.findById(id).isPresent() && clienteRepository.findById(id).get().getEstado() != false
                && clienteRepository.findById(id).get().getDireccion() != null && clienteRepository.findById(id).get().getTelefono() != null
                    && clienteRepository.findById(id).get().getEmail() != null) {
            
            Cliente cliente = MapperUtils.EntityFromDto(clienteDTO, Cliente.class);
            cliente = clienteRepository.save(cliente);
            return Optional.ofNullable(MapperUtils.DtoFromEntity(cliente, ClienteDTO.class));
        } else {
            return null;
        } 
    }

    @Override
    public void deleteAll() {

    }
}
