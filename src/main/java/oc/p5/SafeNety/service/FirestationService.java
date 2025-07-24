package oc.p5.SafeNety.service;

import oc.p5.SafeNety.model.Firestation;
import oc.p5.SafeNety.model.Firestation;
import oc.p5.SafeNety.utils.DataLoader;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FirestationService {
    private final List<Firestation> firestations;
    public FirestationService() {this.firestations = DataLoader.loadData().getFirestations();
    }

    public void addFirestation(Firestation ajout) {
        firestations.add(ajout);
        DataLoader.saveData();
    }

    public void updateFirestation(Firestation maj) {
        for (Firestation f : firestations) {
            if (f.getAddress().equalsIgnoreCase(maj.getAddress())) {
                f.setStation(maj.getStation());
                break;
            }
        }
        DataLoader.saveData();
    }

    public void deleteByAddress(String address) {
        firestations.removeIf(f -> f.getAddress().equalsIgnoreCase(address));
        DataLoader.saveData();
    }

    public void deleteByStation(String station) {
        firestations.removeIf(f -> f.getStation() == station);
        DataLoader.saveData();
    }

}
