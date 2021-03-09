package com.org.cm.core.service.impl;

import com.org.cm.core.model.CafeModel;
import com.org.cm.core.service.CafeService;
import com.org.cm.core.service.exception.CafeNotFoundException;
import com.org.cm.core.service.exception.ProductNotFound;
import com.org.cm.infrastructure.entity.Cafe;
import com.org.cm.infrastructure.entity.Product;
import com.org.cm.infrastructure.repository.CafeRepository;
import com.org.cm.infrastructure.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CafeServiceImpl implements CafeService {

    private final
    CafeRepository cafeRepository;

    private final ProductRepository productRepository;

    private final
    ModelMapper modelMapper;

    public CafeServiceImpl(CafeRepository cafeRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.cafeRepository = cafeRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CafeModel> getCafes() {
        log.info("method getCafes is called");
        List<CafeModel> cafeModelList = (cafeRepository.findAll())
                .stream()
                .map(x -> modelMapper.map(x, CafeModel.class))
                .collect(Collectors.toList());
        Assert.notEmpty(cafeModelList, "No cafe found");
        return cafeModelList;
    }
//optional find by id meththod , get by id, vor nersic find by id a kanchum,

    @Override
    @Transactional(readOnly = true)
    public CafeModel getById(long id) throws CafeNotFoundException {
        log.info("method getCafeById is called");
        Cafe cafe = cafeRepository.findById(id)
                .orElseThrow(() -> new CafeNotFoundException("cafe not found for this id :: " + id));
        CafeModel cafeModel = modelMapper.map(cafe, CafeModel.class);
        Assert.notNull(cafeModel, "cafeModel null");
        return cafeModel;
    }

    @Override
    @Transactional
    public Cafe saveCafe(CafeModel cafeModel) {
        log.info("method saveCafe is called");
        Assert.notNull(cafeModel, "cafe not valid");
        Cafe cafe = modelMapper.map(cafeModel, Cafe.class);
        cafe.setCreated(LocalDateTime.now());
        cafe.setUpdated(LocalDateTime.now());
        List<Product> products = new ArrayList<>();
        cafeModel.getProductIds().forEach(x-> {
            try {
                products.add(productRepository.findById(x).orElseThrow(() ->
                        new ProductNotFound("product not found for this id :: "+x)) );
            } catch (ProductNotFound productNotFound) {
                productNotFound.printStackTrace();
            }
        });
        cafe.setProducts(products);
        return cafeRepository.save(cafe);
    }

    //cafetobeupdated i poxaren getById
    //chem yndunum, vorovhetev getById n indz model a talis, isk es model em stanum vor po
    @Override
    @Transactional
    public Cafe updateCafeByID(long id, CafeModel cafeModel) throws CafeNotFoundException {
        log.info("method updateCafeById is called");
        Assert.notNull(cafeModel, "cafe not valid");
        Cafe cafeToBeUpdated = cafeRepository.findById(id).orElseThrow(() ->
                new CafeNotFoundException("cafe not found for this id :: " + id));
        Cafe cafe1 = modelMapper.map(cafeModel, Cafe.class);
        List<Product> products = new ArrayList<>();
        cafeModel.getProductIds().forEach(x-> {
            try {
                products.add(productRepository.findById(x).orElseThrow(() ->
                        new ProductNotFound("product not found for this id :: "+x)) );
            } catch (ProductNotFound productNotFound) {
                productNotFound.printStackTrace();
            }
        });
        cafe1.setProducts(products);
        cafe1.setCreated(cafeToBeUpdated.getCreated());
        cafe1.setUpdated(LocalDateTime.now());
        cafeRepository.delete(cafeToBeUpdated);
        return cafeRepository.save(cafe1);
    }


    @Override
    @Transactional
    public void deleteCafeByID(long id) throws CafeNotFoundException {
        log.info("method deleteCafeById is called");
        Cafe cafe = cafeRepository.findById(id)
                .orElseThrow(() -> new CafeNotFoundException("cafe not found for this id :: " + id));
        cafe.setRemoved(LocalDateTime.now());
        cafeRepository.save(cafe);
    }
}
