package service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.vehicles.dto.VehicleDto;
import org.vehicles.entity.Vehicle;
import org.vehicles.exception.ResourceNotFoundException;
import org.vehicles.mapper.VehicleMapper;
import org.vehicles.repository.VehicleRepository;
import org.vehicles.service.VehicleService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VehicleServiceTest {

   @InjectMocks
   private VehicleService vehicleService;

   @Mock
   private VehicleRepository vehicleRepository;

   public VehicleServiceTest() {
      MockitoAnnotations.openMocks(this);
   }

   @Test
   public void testGetVehicles() {
      Vehicle vehicle = new Vehicle();
      vehicle.setBrand("Toyota");

      Page<Vehicle> vehiclePage = new PageImpl<>(Collections.singletonList(vehicle));
      when(vehicleRepository.findAll(any(PageRequest.class))).thenReturn(vehiclePage);

      Page<Vehicle> result = vehicleService.getVehicles(0, 10, "brand", "asc");

      assertThat(result.getContent()).hasSize(1);
      assertThat(result.getContent().get(0).getBrand()).isEqualTo("Toyota");
   }

   @Test
   public void testSearchVehicles() {
      Vehicle vehicle1 = new Vehicle();
      vehicle1.setBrand("Toyota");
      Vehicle vehicle2 = new Vehicle();
      vehicle2.setModel("Corolla");
      Vehicle vehicle3 = new Vehicle();
      vehicle3.setLicensePlate("ABC123");

      when(vehicleRepository.findByBrandContaining("Toy")).thenReturn(Collections.singletonList(vehicle1));
      when(vehicleRepository.findByModelContaining("Toy")).thenReturn(Collections.singletonList(vehicle2));
      when(vehicleRepository.findByLicensePlateContaining("Toy")).thenReturn(Collections.singletonList(vehicle3));

      List<Vehicle> result = vehicleService.searchVehicles("Toy");

      assertThat(result).hasSize(3);
      assertThat(result).extracting(Vehicle::getBrand).contains("Toyota");
      assertThat(result).extracting(Vehicle::getModel).contains("Corolla");
      assertThat(result).extracting(Vehicle::getLicensePlate).contains("ABC123");
   }

   @Test
   public void testGetVehicleById() {
      Vehicle vehicle = new Vehicle();
      vehicle.setBrand("Toyota");

      when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

      Optional<Vehicle> result = vehicleService.getVehicleById(1L);

      assertThat(result).isPresent();
      assertThat(result.get().getBrand()).isEqualTo("Toyota");
   }

   @Test
   public void testUpdateVehicle() {
      Vehicle vehicle = new Vehicle();
      vehicle.setId(1L);
      vehicle.setBrand("Toyota");

      VehicleDto vehicleDto = new VehicleDto();
      vehicleDto.setBrand("Honda");
      vehicleDto.setModel("Civic");
      vehicleDto.setLicensePlate("XYZ789");
      vehicleDto.setColor("Blue");
      vehicleDto.setYear(2021);

      when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
      when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

      Vehicle updatedVehicle = vehicleService.updateVehicle(1L, vehicleDto);

      assertThat(updatedVehicle.getBrand()).isEqualTo("Honda");
      assertThat(updatedVehicle.getModel()).isEqualTo("Civic");
      assertThat(updatedVehicle.getLicensePlate()).isEqualTo("XYZ789");
      assertThat(updatedVehicle.getColor()).isEqualTo("Blue");
      assertThat(updatedVehicle.getYear()).isEqualTo(2021);
   }

   @Test
   public void testSaveVehicle() {
      VehicleDto vehicleDto = new VehicleDto();
      vehicleDto.setBrand("Toyota");
      vehicleDto.setModel("Corolla");
      vehicleDto.setLicensePlate("ABC123");
      vehicleDto.setColor("Red");
      vehicleDto.setYear(2020);

      Vehicle vehicle = VehicleMapper.INSTANCE.dtoToEntity(vehicleDto);

      when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

      Vehicle savedVehicle = vehicleService.saveVehicle(vehicleDto);

      assertThat(savedVehicle.getBrand()).isEqualTo("Toyota");
      assertThat(savedVehicle.getModel()).isEqualTo("Corolla");
      assertThat(savedVehicle.getLicensePlate()).isEqualTo("ABC123");
      assertThat(savedVehicle.getColor()).isEqualTo("Red");
      assertThat(savedVehicle.getYear()).isEqualTo(2020);
   }

   @Test
   public void testDeleteVehicle() {
      Vehicle vehicle = new Vehicle();
      vehicle.setId(1L);
      vehicle.setBrand("Toyota");

      when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

      vehicleService.deleteVehicle(1L);

      when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> vehicleService.getVehicleById(1L)).isInstanceOf(ResourceNotFoundException.class);
   }
}

