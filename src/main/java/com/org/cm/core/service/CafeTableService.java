package com.org.cm.core.service;

import com.org.cm.core.model.CafeTableModel;
import com.org.cm.core.model.ProductModel;
import com.org.cm.infrastructure.entity.CafeTable;
import com.org.cm.infrastructure.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CafeTableService {
    List<CafeTableModel> getCafeTables();
    CafeTableModel getCafeTableByID(int id) ;
    CafeTable saveCafeTable (CafeTableModel cafeTableModel) ;
    CafeTable updateCafeTableByID(int id, CafeTableModel cafeTableModel) ;
    void deleteCafeTableByID(int id) ;
}
