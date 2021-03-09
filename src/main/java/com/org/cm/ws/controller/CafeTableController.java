package com.org.cm.ws.controller;

import com.org.cm.core.model.CafeTableModel;
import com.org.cm.core.service.CafeTableService;
import com.org.cm.core.service.exception.CafeNotFoundException;
import com.org.cm.core.service.exception.CafeTableNotFound;
import com.org.cm.core.service.exception.ValidationException;
import com.org.cm.ws.dto.CafeTableDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cafeTable")
@Slf4j
public class CafeTableController {
    private final
    CafeTableService cafeTableService;
    private final
    ModelMapper modelMapper;

    public CafeTableController(CafeTableService cafeTableService, ModelMapper modelMapper) {
        this.cafeTableService = cafeTableService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CafeTableDTO> getCafeTableById(@PathVariable("id") long id) throws CafeTableNotFound {
        log.info("method getCafeTableById in Controller is invoked");

        CafeTableModel cafeTableModel = cafeTableService.getCafeTableByID(id);
        CafeTableDTO cafeTableDTO = modelMapper.map(cafeTableModel, CafeTableDTO.class);
        return new ResponseEntity<>(cafeTableDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CafeTableDTO>> getAllCafeTables() {
        log.info("method getAllCafeTables in Controller is invoked");

        List<CafeTableModel> cafeTableModels = cafeTableService.getCafeTables();
        List<CafeTableDTO> cafeTableDTOS = cafeTableModels.stream().map(x -> modelMapper.map(x, CafeTableDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(cafeTableDTOS, HttpStatus.OK);
    }


    @GetMapping("cafeId/{id}")
    public ResponseEntity<List<CafeTableDTO>> getAllCafeTablesOfCafe(@PathVariable("id") long id) {
        log.info("method getAllCafeTablesOfCafe in Controller is invoked");

        List<CafeTableModel> cafeTableModels = cafeTableService.getCafeTablesBasedOnCafe(id);
        List<CafeTableDTO> cafeTableDTOS = cafeTableModels.stream().map(x -> modelMapper.map(x, CafeTableDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(cafeTableDTOS, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<HttpStatus> createCafeTable(@Valid @RequestBody CafeTableDTO cafeTableDTO) throws ValidationException, CafeNotFoundException {
        log.info("method createCafeTable in Controller is invoked");

        CafeTableModel cafeTableModel = modelMapper.map(cafeTableDTO, CafeTableModel.class);
        cafeTableService.saveCafeTable(cafeTableModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CafeTableDTO> updateCafeTable(@PathVariable("id") long id,
                                                   @Valid @RequestBody CafeTableDTO cafeTableDTO) throws CafeNotFoundException, CafeTableNotFound, ValidationException {
        log.info("method updateCafeTable in Controller is invoked");

        CafeTableModel cafeTableModel = modelMapper.map(cafeTableDTO, CafeTableModel.class);
        cafeTableService.updateCafeTableByID(id, cafeTableModel);
        return new ResponseEntity<>(cafeTableDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCafeTable(
            @PathVariable("id") long id) throws CafeTableNotFound {
        log.info("method deleteCafeTable in Controller is invoked");

        cafeTableService.deleteCafeTableByID(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
