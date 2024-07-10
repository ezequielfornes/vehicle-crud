package org.vehicles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.vehicles.dto.VehicleDto;
import org.vehicles.entity.Vehicle;
import org.vehicles.exception.ResourceNotFoundException;
import org.vehicles.mapper.VehicleMapper;
import org.vehicles.repository.VehicleRepository;

@Service
public class VehicleService {

   @Autowired
   private VehicleRepository vehicleRepository;

   public Page<Vehicle> getVehicles(int page, int size, String sortField, String sortDirection) {
      Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
      return vehicleRepository.findAll(PageRequest.of(page, size, sort));
   }
   public List<Vehicle> searchVehicles(String keyword) {
      List<Vehicle> vehicles = new ArrayList<>();

      List<Vehicle> vehiclesByBrand = vehicleRepository.findByBrandContaining(keyword);
      if (vehiclesByBrand != null) {
         vehicles.addAll(vehiclesByBrand);
      }

      List<Vehicle> vehiclesByModel = vehicleRepository.findByModelContaining(keyword);
      if (vehiclesByModel != null) {
         vehicles.addAll(vehiclesByModel);
      }

      List<Vehicle> vehiclesByLicensePlateContaining = vehicleRepository.findByLicensePlateContaining(keyword);
      if (vehiclesByLicensePlateContaining != null) {
         vehicles.addAll(vehiclesByLicensePlateContaining);
      }

      return vehicles;
   }

   public Optional<Vehicle> getVehicleById(Long id) {
      return vehicleRepository.findById(id);
   }
   public Vehicle updateVehicle(Long id, VehicleDto vehicleDetails) {
      Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
      vehicle.setBrand(vehicleDetails.getBrand());
      vehicle.setModel(vehicleDetails.getModel());
      vehicle.setLicensePlate(vehicleDetails.getLicensePlate());
      vehicle.setColor(vehicleDetails.getColor());
      vehicle.setYear(vehicleDetails.getYear());
      return vehicleRepository.save(vehicle);
   }
   public Vehicle saveVehicle(VehicleDto vehicle) {
      return vehicleRepository.save(VehicleMapper.INSTANCE.dtoToEntity(vehicle));
   }

   public void deleteVehicle(Long id) {
      Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
      vehicleRepository.delete(vehicle);
   }
}
