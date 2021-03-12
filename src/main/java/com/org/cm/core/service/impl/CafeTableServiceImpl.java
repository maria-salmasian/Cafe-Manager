package com.org.cm.core.service.impl;

import com.org.cm.core.model.CafeTableModel;
import com.org.cm.core.service.CafeTableService;
import com.org.cm.core.service.exception.CafeNotFoundException;
import com.org.cm.core.service.exception.CafeTableNotFound;
import com.org.cm.infrastructure.entity.Cafe;
import com.org.cm.infrastructure.entity.CafeTable;
import com.org.cm.infrastructure.entity.User;
import com.org.cm.infrastructure.repository.CafeRepository;
import com.org.cm.infrastructure.repository.CafeTableRepository;
import com.org.cm.infrastructure.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    UserRepository userRepository;

    @Autowired
    public CafeTableServiceImpl(CafeTableRepository cafeTableRepository, CafeRepository cafeRepository, UserRepository userRepository) {
        this.cafeTableRepository = cafeTableRepository;
        this.cafeRepository = cafeRepository;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<CafeTableModel> getCafeTables() {
        log.info("method getCafeTables is invoked");
        List<CafeTableModel> cafeTableModels = (cafeTableRepository
                .findAll())
                .stream()
                .map(x -> cafeTableToCafeTableModel(x))
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
                .map(x -> cafeTableToCafeTableModel(x))
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
        CafeTableModel cafeTableModel = cafeTableToCafeTableModel(cafeTable);
        Assert.notNull(cafeTableModel, "cafe table not valid");
        return cafeTableModel;
    }

    @Override
    @Transactional
    public CafeTable saveCafeTable(CafeTableModel cafeTableModel) throws CafeNotFoundException {
        log.info("method saveCafeTable is invoked");
        Assert.notNull(cafeTableModel, "cafe table not valid");
        Cafe cafe = cafeRepository.findById(cafeTableModel.getCafeId())
                .orElseThrow(() -> new CafeNotFoundException("Cafe specified not found"));
        if (cafe.getRemoved() == null) {
            CafeTable cafeTable = cafeTableModelToCafeTable(cafeTableModel);
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
        CafeTable cafeTable = cafeTableModelToCafeTable(cafeTableModel);
        Cafe cafe = cafeRepository.findById(cafeTableModel.getCafeId()).orElseThrow(() -> new CafeNotFoundException("Cafe not found"));
        if (cafe.getRemoved() == null) {
            cafeTable.setCreated(cafeTableToBeUpdated.getCreated());
            cafeTable.setUpdated(LocalDateTime.now());
            (cafeTableToBeUpdated).setRemoved(LocalDateTime.now());
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


    private CafeTable cafeTableModelToCafeTable(CafeTableModel cafeTableModel) {
        CafeTable cafeTable = new CafeTable();
        cafeTable.setCafe(cafeRepository.getCafeById(cafeTableModel.getCafeId()));

        List<User> users =  new ArrayList<>();
        cafeTableModel.getUserIds().forEach(x-> users.add(userRepository.getUserById(x)));

        cafeTable.setUsers(users);
        return cafeTable;
    }

    private CafeTableModel cafeTableToCafeTableModel(CafeTable cafeTable) {
        CafeTableModel cafeTableModel = new CafeTableModel();
        cafeTableModel.setCafeId(cafeTable.getId());
        cafeTableModel.setCafeId(cafeTable.getCafe().getId());

        List<Long> orderIds = new ArrayList<>();
        cafeTable.getOrders().forEach(x -> orderIds.add(x.getId()));

        List<Long> userIds = new ArrayList<>();
        cafeTable.getUsers().forEach(x -> userIds.add(x.getId()));

        cafeTableModel.setOrderIds(orderIds);
        cafeTableModel.setUserIds(userIds);

        cafeTableModel.setId(cafeTable.getId());

        return cafeTableModel;
    }


}
