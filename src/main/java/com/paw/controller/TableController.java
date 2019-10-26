package com.paw.controller;


import act.controller.Controller;
import act.controller.annotation.UrlContext;
import com.paw.dto.TableDto;
import com.paw.model.TableModel;
import com.paw.service.TableService;
import com.paw.service.TableServiceImpl;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.mvc.result.NotFound;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
public class TableController {

    private TableService tableService = new TableServiceImpl();

    @PostAction("/table/{asd}/{qwe}")
    public TableModel addTable(String asd, String qwe) {
        TableDto tableDto = new TableDto();
        tableDto.setName(asd);
        tableDto.setId(Integer.valueOf(qwe));
        System.out.println(tableDto);
        return tableService.addTable(tableDto);
    }

    @GetAction("/table")
    public List<TableDto> getTables() {
        try {
            return tableService.getTables();
        } catch (NullPointerException e) {
            throw new NotFound();
        }
    }

}
