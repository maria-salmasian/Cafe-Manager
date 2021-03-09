package com.org.cm.ws.controller;

import com.org.cm.core.model.CafeModel;
import com.org.cm.core.service.CafeService;
import com.org.cm.core.service.exception.CafeNotFoundException;
import com.org.cm.core.service.exception.NotFoundException;
import com.org.cm.core.service.exception.ValidationException;
import com.org.cm.ws.dto.CafeDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/cafe")
public class CafeController {

    private final
    CafeService cafeService;

    private final
    ModelMapper modelMapper;

    public CafeController(CafeService cafeService, ModelMapper modelMapper) {
        this.cafeService = cafeService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/{id}")
    public ResponseEntity<CafeDTO> getCafeById(@PathVariable("id") long id) throws CafeNotFoundException {
        log.info("method getCafeById in Controller is invoked");
        CafeModel cafeModel = cafeService.getById(id);
        CafeDTO cafeDTO = modelMapper.map(cafeModel, CafeDTO.class);
        return new ResponseEntity<>(cafeDTO, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<CafeDTO>> getAllCafes() {
        log.info("method getAllCafes in Controller is invoked");
        List<CafeModel> cafeModels = cafeService.getCafes();
        List<CafeDTO> cafeDTOS = cafeModels.stream().map(x -> modelMapper.map(x, CafeDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(cafeDTOS, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CafeDTO> createCafe( @RequestBody CafeDTO cafeDTO) throws ValidationException {
        log.info("method createCafe in Controller is invoked");

        CafeModel createdCafeModel = modelMapper.map(cafeDTO, CafeModel.class);
        cafeService.saveCafe(createdCafeModel);
        return new ResponseEntity<>(cafeDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CafeDTO> updateCafe(@PathVariable("id") long id,
                                              @RequestBody CafeDTO cafeDTO) throws NotFoundException {
        log.info("method updateCafe in Controller is invoked");

        CafeModel updatedCafeModel = modelMapper.map(cafeDTO, CafeModel.class);
        cafeService.updateCafeByID(id, updatedCafeModel);
        return new ResponseEntity<>(cafeDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCafe(@PathVariable("id") long id) throws CafeNotFoundException {
        log.info("method deleteCafe in Controller is invoked");
        cafeService.deleteCafeByID(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }



}
