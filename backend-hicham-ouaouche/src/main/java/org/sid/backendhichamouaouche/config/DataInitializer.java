package org.sid.backendhichamouaouche.config;

import org.sid.backendhichamouaouche.dtos.AgencyRequest;
import org.sid.backendhichamouaouche.dtos.RentalRequest;
import org.sid.backendhichamouaouche.dtos.VehicleRequest;
import org.sid.backendhichamouaouche.entities.AppUser;
import org.sid.backendhichamouaouche.enums.Role;
import org.sid.backendhichamouaouche.enums.VehicleStatus;
import org.sid.backendhichamouaouche.repositories.UserRepository;
import org.sid.backendhichamouaouche.services.AgencyService;
import org.sid.backendhichamouaouche.services.RentalService;
import org.sid.backendhichamouaouche.services.VehicleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AgencyService agencyService;
    private final VehicleService vehicleService;
    private final RentalService rentalService;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, AgencyService agencyService, VehicleService vehicleService, RentalService rentalService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.agencyService = agencyService;
        this.vehicleService = vehicleService;
        this.rentalService = rentalService;
    }

    @Override
    public void run(String... args) {
        seedUsers();
        if (agencyService.findAll().isEmpty()) {
            var agency1 = agencyService.create(AgencyRequest.builder()
                    .name("Agence Centrale")
                    .address("10 Avenue Hassan II")
                    .city("Casablanca")
                    .phone("0522000001")
                    .build());
            var agency2 = agencyService.create(AgencyRequest.builder()
                    .name("Agence Nord")
                    .address("45 Boulevard Mohamed V")
                    .city("Rabat")
                    .phone("0537000002")
                    .build());

            var car = vehicleService.create(VehicleRequest.builder()
                    .agencyId(agency1.getId())
                    .vehicleType("CAR")
                    .marque("Dacia")
                    .modele("Logan")
                    .matricule("AA-111-BB")
                    .prixParJour(350.0)
                    .dateMiseEnService(LocalDate.of(2022, 1, 10))
                    .status(VehicleStatus.DISPONIBLE)
                    .nombrePortes(4)
                    .typeCarburant("ESSENCE")
                    .boiteVitesse("MANUELLE")
                    .build());

            var moto = vehicleService.create(VehicleRequest.builder()
                    .agencyId(agency2.getId())
                    .vehicleType("MOTO")
                    .marque("Yamaha")
                    .modele("MT-07")
                    .matricule("CC-222-DD")
                    .prixParJour(180.0)
                    .dateMiseEnService(LocalDate.of(2023, 5, 5))
                    .status(VehicleStatus.DISPONIBLE)
                    .cylindree(700)
                    .typeMoto("ROADSTER")
                    .casqueInclus(true)
                    .build());

            rentalService.create(RentalRequest.builder()
                    .vehicleId(car.getId())
                    .customerName("Test Client")
                    .customerPhone("0600000000")
                    .startDate(LocalDate.now().minusDays(7))
                    .endDate(LocalDate.now().minusDays(3))
                    .build());

            vehicleService.update(moto.getId(), VehicleRequest.builder()
                    .agencyId(agency2.getId())
                    .vehicleType("MOTO")
                    .marque("Yamaha")
                    .modele("MT-07")
                    .matricule("CC-222-DD")
                    .prixParJour(180.0)
                    .dateMiseEnService(LocalDate.of(2023, 5, 5))
                    .status(VehicleStatus.DISPONIBLE)
                    .cylindree(700)
                    .typeMoto("ROADSTER")
                    .casqueInclus(true)
                    .build());
        }
    }

    private void seedUsers() {
        if (userRepository.count() > 0) {
            return;
        }
        userRepository.save(AppUser.builder().username("admin").password(passwordEncoder.encode("admin123")).role(Role.ROLE_ADMIN).enabled(true).build());
        userRepository.save(AppUser.builder().username("employee").password(passwordEncoder.encode("employee123")).role(Role.ROLE_EMPLOYEE).enabled(true).build());
        userRepository.save(AppUser.builder().username("client").password(passwordEncoder.encode("client123")).role(Role.ROLE_CLIENT).enabled(true).build());
    }
}