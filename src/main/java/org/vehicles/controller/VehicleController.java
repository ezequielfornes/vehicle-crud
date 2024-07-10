package org.vehicles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vehicles.dto.VehicleDto;
import org.vehicles.entity.Vehicle;
import org.vehicles.service.VehicleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
   @Autowired
   private VehicleService vehicleService;

   @GetMapping()
   public Page<Vehicle> getVehicles(
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size,
         @RequestParam(defaultValue = "id") String sortField,
         @RequestParam(defaultValue = "asc") String sortDirection) {
      return vehicleService.getVehicles(page, size, sortField, sortDirection);
   }

   @GetMapping("/search")
   public List<Vehicle> searchVehicles(@RequestParam String keyword) {
      return vehicleService.searchVehicles(keyword);
   }

   @GetMapping("/{id}")
   public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
      Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
      return vehicle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
   }

   @PostMapping()
   public ResponseEntity<Vehicle> registerVehicle(@Valid @RequestBody VehicleDto vehicle) {
      Vehicle savedVehicle = vehicleService.saveVehicle(vehicle);
      return new ResponseEntity<>(savedVehicle, HttpStatus.CREATED);
   }

   @PutMapping("/{id}")
   public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleDto vehicleDetails) {
      Vehicle updatedVehicle = vehicleService.updateVehicle(id, vehicleDetails);
      return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
      vehicleService.deleteVehicle(id);
      return ResponseEntity.ok().build();
   }
}
