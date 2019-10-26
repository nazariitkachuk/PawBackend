package com.paw.mapper;

import com.paw.dto.TableDto;
import com.paw.model.TableModel;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

public class DefaultMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(TableDto.class, TableModel.class)
                .byDefault()
                .register();
    }
}
