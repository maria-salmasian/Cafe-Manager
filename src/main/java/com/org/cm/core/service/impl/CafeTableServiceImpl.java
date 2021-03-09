package com.org.cm.core.service.impl;

import com.org.cm.core.model.CafeTableModel;
import com.org.cm.core.service.CafeTableService;
import com.org.cm.core.service.exception.CafeNotFoundException;
import com.org.cm.core.service.exception.CafeTableNotFound;
import com.org.cm.core.service.exception.ValidationException;
import com.org.cm.infrastructure.entity.Cafe;
import com.org.cm.infrastructure.entity.CafeTable;
import com.org.cm.infrastructure.repository.CafeRepository;
import com.org.cm.infrastructure.repository.CafeTableRepository;
import com.org.cm.infrastructure.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CafeTableServiceImpl implements CafeTableService {

    private final
    CafeTableRepository cafeTableRepository;
    private final
    CafeRepository cafeRepository;
    private final
    OrderRepository orderRepository;
    private final
    ModelMapper modelMapper;

    public CafeTableServiceImpl(CafeTableRepository cafeTableRepository, CafeRepository cafeRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.cafeTableRepository = cafeTableRepository;
        this.cafeRepository = cafeRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CafeTableModel> getCafeTables() {
        log.info("method getCafeTables is invoked");
        List<CafeTableModel> cafeTableModels = (cafeTableRepository
                .findAll())
                .stream()
                .map(x -> modelMapper.map(x, CafeTableModel.class))
                .collect(Collectors.toList());
        Assert.notEmpty(cafeTableModels, "No cafe tables found");
        return cafeTableModels;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CafeTableModel> getCafeTablesBasedOnCafe(long cafeId) {
        log.info("method getCafeTablesBasedOnCafe is invoked");
        List<CafeTableModel> cafeTableModels = (cafeTableRepository
                .findAllByCafe(cafeId)
                .stream()
                .map(x -> modelMapper.map(x, CafeTableModel.class))
                .collect(Collectors.toList()));
        Assert.notNull(cafeTableModels, "cafe tables not valid");
        return cafeTableModels;
    }

    @Override
    @Transactional(readOnly = true)
    public CafeTableModel getCafeTableByID(long id) throws CafeTableNotFound {
        log.info("method getCafeTableById is invoked");
        CafeTable cafeTable = cafeTableRepository.findById(id)
                .orElseThrow(() -> new CafeTableNotFound("cafe table not found for this id :: " + id));
        CafeTableModel cafeTableModel = modelMapper.map(cafeTable, CafeTableModel.class);
        Assert.notNull(cafeTableModel, "cafe table not valid");
        return cafeTableModel;
    }

    @Override
    @Transactional
    public CafeTable saveCafeTable(CafeTableModel cafeTableModel) throws ValidationException, CafeNotFoundException {
        log.info("method saveCafeTable is invoked");
        Assert.notNull(cafeTableModel, "cafe table not valid");
        Cafe cafe = cafeRepository.findById(cafeTableModel.getCafeId())
                .orElseThrow(() -> new CafeNotFoundException("Cafe specified not found"));
        if (cafe.getRemoved() == null) {
            CafeTable cafeTable = modelMapper.map(cafeTableModel, CafeTable.class);
            cafeTable.setCreated(LocalDateTime.now());
            cafeTable.setUpdated(LocalDateTime.now());
            return cafeTableRepository.save(cafeTable);

        } else throw new CafeNotFoundException("Cafe is removed");
    }

    @Override
    @Transactional
    public CafeTable updateCafeTableByID(long id, CafeTableModel cafeTableModel) throws CafeTableNotFound, CafeNotFoundException {
        log.info("method updateCafeTableById is invoked");
        Assert.notNull(cafeTableModel, "cafe tables not valid");
        CafeTable cafeTableToBeUpdated = cafeTableRepository.findById(id).orElseThrow(() ->
                new CafeTableNotFound("cafe table not found for this id :: " + id));
        CafeTable cafeTable = modelMapper.map(cafeTableModel, CafeTable.class);
        Cafe cafe = cafeRepository.findById(cafeTableModel.getCafeId()).orElseThrow(() -> new CafeNotFoundException("Cafe not found"));
        if (cafe.getRemoved() == null) {
            cafeTable.setCafe(cafe);
            cafeTable.setCreated(cafeTableToBeUpdated.getCreated());
            cafeTable.setUpdated(LocalDateTime.now());
            cafeTableRepository.delete(cafeTableToBeUpdated);
            return cafeTableRepository.save(cafeTable);
        } else throw new CafeTableNotFound("cafe is removed");

    }

    @Override
    @Transactional
    public void deleteCafeTableByID(long id) throws CafeTableNotFound {
        log.info("method deleteCafeTableById is invoked");
        CafeTable cafeTable = cafeTableRepository.findById(id)
                .orElseThrow(() -> new CafeTableNotFound("cafe table not found for this id :: " + id));
        cafeTable.setRemoved(LocalDateTime.now());
        cafeTableRepository.save(cafeTable);

    }


}
