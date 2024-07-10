package org.vehicles.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.vehicles.entity.Vehicle;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

   Page<Vehicle> findAll(Pageable pageable);
   List<Vehicle> findByBrandContaining(String brand);
   List<Vehicle> findByModelContaining(String model);
   List<Vehicle> findByLicensePlateContaining(String licensePlate);

}
