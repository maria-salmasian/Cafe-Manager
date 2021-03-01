package com.org.cm.core.service;

import com.org.cm.core.model.CafeModel;
import com.org.cm.core.service.exception.CafeNotFoundException;
import com.org.cm.core.service.exception.ValidationException;
import com.org.cm.infrastructure.entity.Cafe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CafeService {
    List<CafeModel> getCafes();
    CafeModel getCafeById(long id) throws CafeNotFoundException;
    Cafe saveCafe(CafeModel cafeModel) throws ValidationException;
    Cafe updateCafeByID(long id, CafeModel cafeModel) throws CafeNotFoundException;
    void deleteCafeByID(long id) throws CafeNotFoundException;
}
