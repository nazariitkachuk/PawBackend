package com.paw.service;

import com.paw.dto.TableDto;
import com.paw.model.TableModel;

import java.util.List;

public interface TableService {

    TableModel addTable(TableDto tableDto);

    List<TableDto> getTables();
}
