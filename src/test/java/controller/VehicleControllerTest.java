package controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.vehicles.Main;
import org.vehicles.dto.VehicleDto;
import org.vehicles.entity.Vehicle;
import org.vehicles.service.VehicleService;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@AutoConfigureMockMvc
public class VehicleControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private TestRestTemplate restTemplate;

   @MockBean
   private VehicleService vehicleService;

   @Test
   void contextLoads() {
   }

   @Test
   public void testCreateVehicle() {
      VehicleDto vehicle = new VehicleDto();
      vehicle.setBrand("Toyota");
      vehicle.setModel("Corolla");
      vehicle.setLicensePlate("ABC123");
      vehicle.setColor("Red");
      vehicle.setYear(2020);

      Vehicle vehicleEntity = new Vehicle();
      vehicle.setBrand("Toyota");
      vehicle.setModel("Corolla");
      vehicle.setLicensePlate("ABC123");
      vehicle.setColor("Red");
      vehicle.setYear(2020);

      when(vehicleService.saveVehicle(vehicle)).thenReturn(vehicleEntity);

      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

      ResponseEntity<VehicleDto> responseEntity = restTemplate.exchange("/vehicles", HttpMethod.POST, new HttpEntity<>(vehicle, headers), VehicleDto.class);

      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseEntity.getBody()).isNotNull();
      assertThat(responseEntity.getBody().getBrand()).isEqualTo("Toyota");
   }
   @Test
   public void testGetVehicles() {
      Vehicle vehicle = new Vehicle();
      vehicle.setBrand("Toyota");
      vehicle.setModel("Corolla");
      vehicle.setLicensePlate("ABC123");
      vehicle.setColor("Red");
      vehicle.setYear(2020);

      Page<Vehicle> vehiclePage = new PageImpl<>(Collections.singletonList(vehicle));

      when(vehicleService.getVehicles(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
            .thenReturn(vehiclePage);

      ResponseEntity<String> responseEntity = restTemplate.getForEntity("/vehicles", String.class);

      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseEntity.getBody()).contains("Toyota");
   }

   @Test
   public void testSearchVehicles() {
      Vehicle vehicle = new Vehicle();
      vehicle.setBrand("Toyota");
      vehicle.setModel("Corolla");
      vehicle.setLicensePlate("ABC123");
      vehicle.setColor("Red");
      vehicle.setYear(2020);

      when(vehicleService.searchVehicles(Mockito.anyString())).thenReturn(Collections.singletonList(vehicle));

      ResponseEntity<String> responseEntity = restTemplate.getForEntity("/vehicles/search?keyword=Toyota", String.class);

      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseEntity.getBody()).contains("Toyota");
   }

   @Test
   public void testGetVehicleById() {
      Vehicle vehicle = new Vehicle();
      vehicle.setBrand("Toyota");
      vehicle.setModel("Corolla");
      vehicle.setLicensePlate("ABC123");
      vehicle.setColor("Red");
      vehicle.setYear(2020);

      when(vehicleService.getVehicleById(Mockito.anyLong())).thenReturn(Optional.of(vehicle));

      ResponseEntity<Vehicle> responseEntity = restTemplate.getForEntity("/vehicles/1", Vehicle.class);

      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseEntity.getBody()).isNotNull();
      assertThat(responseEntity.getBody().getBrand()).isEqualTo("Toyota");
   }

   @Test
   public void testUpdateVehicle() {
      VehicleDto vehicleDto = new VehicleDto();
      vehicleDto.setBrand("Toyota");
      vehicleDto.setModel("Corolla");
      vehicleDto.setLicensePlate("ABC123");
      vehicleDto.setColor("Red");
      vehicleDto.setYear(2020);

      Vehicle vehicleEntity = new Vehicle();
      vehicleEntity.setBrand("Toyota");
      vehicleEntity.setModel("Corolla");
      vehicleEntity.setLicensePlate("ABC123");
      vehicleEntity.setColor("Red");
      vehicleEntity.setYear(2020);

      when(vehicleService.updateVehicle(Mockito.anyLong(), Mockito.any(VehicleDto.class))).thenReturn(vehicleEntity);

      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

      ResponseEntity<Vehicle> responseEntity = restTemplate.exchange("/vehicles/1", HttpMethod.PUT, new HttpEntity<>(vehicleDto, headers), Vehicle.class);

      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(responseEntity.getBody()).isNotNull();
      assertThat(responseEntity.getBody().getBrand()).isEqualTo("Toyota");
   }

   @Test
   public void testDeleteVehicle() {
      Mockito.doNothing().when(vehicleService).deleteVehicle(Mockito.anyLong());

      ResponseEntity<Void> responseEntity = restTemplate.exchange("/vehicles/1", HttpMethod.DELETE, null, Void.class);

      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
   }
}
