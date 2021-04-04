package madbrains.controller;

import lombok.extern.slf4j.Slf4j;
import madbrains.model.MyComparator;
import madbrains.service.ComparatorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@Slf4j
@RestController
@RequestMapping("user/comparator")
public class ComparatorController {
    private final ComparatorService comparatorService;

    @Autowired
    public ComparatorController( ComparatorService comparatorService) {
        this.comparatorService = comparatorService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addComparator(@RequestBody MyComparator groovy) {
        try {
            comparatorService.createComparator(groovy);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info("Error at creating comparator" + groovy);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
