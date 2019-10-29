package com.paw.controller;


import act.controller.Controller;
import act.db.jpa.JPADao;
import com.paw.model.Table;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.mvc.result.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

@Controller
public class TableController {

    private static final Logger LOG = LoggerFactory.getLogger(TableController.class);

    @Inject
    private JPADao<Long, Table> dao;

    @PostAction("/table")
    public Table addTable(Table table) {
        LOG.info("Dodano tablice: " + table.toString());
        return dao.save(table);

    }

    @GetAction("/table")
    public List<Table> getTables() {
        try {
            return dao.findAllAsList();
        } catch (NullPointerException e) {
            throw new NotFound();
        }
    }

}
