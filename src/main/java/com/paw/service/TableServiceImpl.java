package com.paw.service;


import com.paw.dao.TableDao;
import com.paw.dto.TableDto;
import com.paw.mapper.DefaultMapper;
import com.paw.model.TableModel;
import ma.glasnost.orika.MapperFacade;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class TableServiceImpl implements TableService{

    @Inject
    private TableDao tableDao;

    private MapperFacade mapper = new DefaultMapper();

    public TableModel addTable(TableDto tableDto) {
        return tableDao.save(mapper.map(tableDto, TableModel.class));
    }

    @Override
    public List<TableDto> getTables() {
        return tableDao.findAllAsList().stream().map(tableModel -> mapper.map(tableModel,TableDto.class)).collect(Collectors.toList());
    }

}
