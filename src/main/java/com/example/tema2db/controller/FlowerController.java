package com.example.tema2db.controller;


import com.example.tema2db.model.Flower;
import com.example.tema2db.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/flowers")
public class FlowerController {
    private final FlowerService flowerService;

    @GetMapping
    public List<Flower> getAllFlowers() {
        return flowerService.getAllFlowers();
    }

    @PostMapping
    public void addFlower(@RequestBody Flower flower) {
        flowerService.addFlower(flower);
    }

    @PutMapping
    public Flower updateFlower(@RequestBody Flower flower) {
        return flowerService.updateFlower(flower);
    }

    @DeleteMapping("/{id}")
    public void deleteFlower(@PathVariable Long id) {
        flowerService.deleteFlower(id);
    }

    @GetMapping("{id}")
    public Flower getFlower ( @PathVariable Long id)
    {
        return flowerService.getFlower( id );

    }

    @PostMapping("buy")
    public void buyFlower(@RequestParam("flowerId") Long flowerId, @RequestParam("quantity") Long quantity) {
        flowerService.buyFlower(flowerId, quantity);
    }
}