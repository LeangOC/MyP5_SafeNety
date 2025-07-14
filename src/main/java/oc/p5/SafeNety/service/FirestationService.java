package oc.p5.SafeNety.service;
import oc.p5.SafeNety.dto.FirestationDTO;
import oc.p5.SafeNety.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirestationService {

    private final DataRepository dataRepository;

    public FirestationService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<FirestationDTO> getAddressesByStation(String stationNumber) {
        return dataRepository.getFirestations().stream()
                .filter(f -> f.getStation().equals(stationNumber))
                .map(f -> new FirestationDTO(f.getAddress()))
                .collect(Collectors.toList());
    }
}
