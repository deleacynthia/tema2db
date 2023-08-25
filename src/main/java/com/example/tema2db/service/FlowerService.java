package com.example.tema2db.service;

import com.example.tema2db.model.Flower;
import com.example.tema2db.model.Order;
import com.example.tema2db.repository.FlowerRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlowerService {

    private final OrderService orderService;

    private final FlowerRepository flowerRepository;
    public List<Flower> getAllFlowers() {
        log.info("All Flowers");
        return flowerRepository.findAll().stream().filter(flower -> !flower.isDeleted()).collect(Collectors.toList());
    }
    public Flower addFlower(Flower flower){
        log.info("Adding flower");
        flower.setDeleted(false);
        return flowerRepository.save(flower);
    }

    public Flower updateFlower(Flower flower){
        log.info("Updating flower");
        Flower existingFlower = flowerRepository.findById(flower.getId()).orElse(null);

        if (existingFlower == null) {

        throw new RuntimeException("Flower not found");
    }

    if (flower.getPrice() != null) {
        existingFlower.setPrice(flower.getPrice());
    }

    if (flower.getStock() != null) {
        existingFlower.setStock(flower.getStock());
    }

        return flowerRepository.save(existingFlower);

    }

    @Transactional
    public void buyFlower(Long flowerToBuyId, Long quantity) {
        Flower selectedFlower = flowerRepository.findById(flowerToBuyId).orElse(null);

        if (selectedFlower == null || selectedFlower.getStock() < quantity) {
            return;
        }

        selectedFlower.setStock(selectedFlower.getStock() - quantity);
        flowerRepository.save(selectedFlower);

        Order order = new Order();
        order.setFlowerId(flowerToBuyId);
        order.setQuantity(quantity);
        order.setPrice(quantity * selectedFlower.getPrice());
        orderService.saveOrder(order);
    }

    public void deleteFlower(Long id) {
        log.info("Deleting flower");
        flowerRepository.deleteById(id);
    }

    public Flower getFlower(Long id){
        log.info("Getting flower with id " + id);

        return flowerRepository.findById( id ).get();
    }
}
