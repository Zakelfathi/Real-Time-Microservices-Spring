package com.product.inventoryservice;

import com.product.inventoryservice.model.Inventory;
import com.product.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return (args) -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("123456");
			inventory.setQuantity(10);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("123457");
			inventory2.setQuantity(20);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory2);
		};
	}
}
