package mk.finki.ukim.mk.airbnb.service;

import mk.finki.ukim.mk.airbnb.dto.HostDto;

import java.util.List;
import java.util.Optional;

public interface HostService {
    List<HostDto> findAll();
    Optional<HostDto> findById(Long id);
    HostDto save(HostDto hostDto);
    void deleteById(Long id);
} 