package org.vehicles.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Entity
public class Vehicle {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @NotBlank(message = "Brand is mandatory")
   private String brand;

   @NotBlank(message = "Model is mandatory")
   private String model;

   @NotBlank(message = "License plate is mandatory")
   @Pattern(regexp = "^[A-Z0-9-]+$", message = "License plate must be alphanumeric")
   private String licensePlate;

   @NotBlank(message = "Color is mandatory")
   private String color;
   private int year;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getBrand() {
      return brand;
   }

   public void setBrand(String brand) {
      this.brand = brand;
   }

   public String getModel() {
      return model;
   }

   public void setModel(String model) {
      this.model = model;
   }

   public String getLicensePlate() {
      return licensePlate;
   }

   public void setLicensePlate(String licensePlate) {
      this.licensePlate = licensePlate;
   }

   public String getColor() {
      return color;
   }

   public void setColor(String color) {
      this.color = color;
   }

   public int getYear() {
      return year;
   }

   public void setYear(int year) {
      this.year = year;
   }
}
