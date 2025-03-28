package mk.finki.ukim.mk.airbnb.service.impl;

import mk.finki.ukim.mk.airbnb.dto.HostDto;
import mk.finki.ukim.mk.airbnb.models.domain.Host;
import mk.finki.ukim.mk.airbnb.repository.HostRepository;
import mk.finki.ukim.mk.airbnb.service.HostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HostServiceImpl implements HostService {

    private final HostRepository hostRepository;

    public HostServiceImpl(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    @Override
    public List<HostDto> findAll() {
        return hostRepository.findAll().stream()
                .map(HostDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<HostDto> findById(Long id) {
        return hostRepository.findById(id)
                .map(HostDto::new);
    }

    @Override
    public HostDto save(HostDto hostDto) {
        Host host = hostDto.toEntity();
        host = hostRepository.save(host);
        return new HostDto(host);
    }

    @Override
    public void deleteById(Long id) {
        hostRepository.deleteById(id);
    }
} 