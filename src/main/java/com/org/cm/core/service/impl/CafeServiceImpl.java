package com.org.cm.core.service.impl;

import com.org.cm.core.model.CafeModel;
import com.org.cm.core.service.CafeService;
import com.org.cm.core.service.exception.CafeNotFoundException;
import com.org.cm.core.service.exception.ValidationException;
import com.org.cm.infrastructure.entity.Cafe;
import com.org.cm.infrastructure.repository.CafeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CafeServiceImpl implements CafeService {

    @Autowired
    CafeRepository cafeRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<CafeModel> getCafes() {
        List<CafeModel> userList = (cafeRepository.findAll())
                .stream()
                .map(x -> modelMapper.map(x, CafeModel.class))
                .collect(Collectors.toList());
        Assert.notEmpty(userList, "No user found");
        return userList;
    }

    @Override
    public CafeModel getCafeById(long id) throws CafeNotFoundException {
        Cafe cafe = cafeRepository.findById(id)
                .orElseThrow(() -> new CafeNotFoundException("cafe not found for this id :: " + id));
        return modelMapper.map(cafe, CafeModel.class);
    }

    @Override
    public Cafe saveCafe(CafeModel cafeModel) throws ValidationException {
        if (cafeModel != null) {
            Cafe cafe = modelMapper.map(cafeModel, Cafe.class);
            cafe.setDeleted(false);
            return cafeRepository.save(cafe);
        } else throw new
                ValidationException("Body Not Valid");
    }

    @Override
    public Cafe updateCafeByID(long id, CafeModel cafeModel) throws CafeNotFoundException {
        Cafe cafeToBeUpdated = cafeRepository.findById(id).orElseThrow(() ->
                new CafeNotFoundException("cafe not found for this id :: " + id));
        Assert.notNull(cafeModel, "the body is null");
        Cafe cafe1 = modelMapper.map(cafeModel, Cafe.class);
        cafe1.setDeleted(false);
        cafeRepository.delete(cafeToBeUpdated);
        return cafeRepository.save(cafe1);
    }

    @Override
    public void deleteCafeByID(long id) throws CafeNotFoundException {
        Cafe cafe = cafeRepository.findById(id)
                .orElseThrow(() -> new CafeNotFoundException("cafe not found for this id :: " + id));
        cafe.setDeleted(true);
        cafeRepository.save(cafe);
    }
}
