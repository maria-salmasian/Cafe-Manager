package com.org.cm.core.service;

import com.org.cm.core.model.CafeTableModel;
import com.org.cm.core.service.exception.CafeNotFoundException;
import com.org.cm.core.service.exception.CafeTableNotFound;
import com.org.cm.core.service.exception.ValidationException;
import com.org.cm.infrastructure.entity.CafeTable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CafeTableService {
    List<CafeTableModel> getCafeTables();
    CafeTableModel getCafeTableByID(long id) throws CafeTableNotFound;
    CafeTable saveCafeTable (CafeTableModel cafeTableModel) throws ValidationException, CafeNotFoundException;
    CafeTable updateCafeTableByID(long id, CafeTableModel cafeTableModel) throws CafeTableNotFound, ValidationException, CafeNotFoundException;
    void deleteCafeTableByID(long id) throws CafeTableNotFound;
    List<CafeTableModel> getCafeTablesBasedOnCafe(long cafeId);
}
