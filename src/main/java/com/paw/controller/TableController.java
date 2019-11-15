package com.paw.controller;


import act.controller.Controller;
import act.db.jpa.JPADao;
import com.paw.model.PawList;
import com.paw.model.PawTable;
import org.osgl.mvc.annotation.DeleteAction;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.mvc.annotation.PutAction;
import org.osgl.mvc.result.BadRequest;
import org.osgl.mvc.result.NotFound;
import org.osgl.mvc.result.Unauthorized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class TableController extends AuthenticatedController{

    private static final Logger LOG = LoggerFactory.getLogger(TableController.class);

    @Inject
    private JPADao<Integer, PawTable> tableDao;

    @Inject
    private JPADao<Integer, PawList> listDao;

    @PostAction("/table")
    public PawTable addTable(PawTable pawTable) {
        pawTable.setOwner(me.email);
        LOG.info("Dodano tablice: " + pawTable.toString());
        return tableDao.save(pawTable);

    }

    @GetAction("/table")
    public List<PawTable> getTableList() {
        if(me !=null) {
            try {
                return tableDao.findAllAsList().stream()
                        .filter(table -> table.getOwner().equals(me.email()))
                        .collect(Collectors.toList());
            } catch (NullPointerException e) {
                throw new NotFound();
            }
        } else {
            throw new Unauthorized();
        }
    }

    @GetAction("/table/{id}")
    public PawTable getTableDetails(int id) {
        PawTable table = tableDao.findById(id);
        if(table.getOwner().equals(me.email())) {
            return table;
        } else {
            throw new Unauthorized();
        }
    }

    @PutAction("/table/{id}")
    public PawTable updateTableName(int id, PawTable pawTable) {
        if (id != pawTable.getId()) {
            throw new BadRequest("Id in body do not match id in path");
        } else {
            PawTable tempTable = tableDao.findById(id);
            if(tempTable.getOwner().equals(me.email())) {
                LOG.info("Zmieniono nazwe tablicy o  id:{} z {} na {}", id, tempTable.getName(), pawTable.getName());
                tempTable.setName(pawTable.getName());
                return tableDao.save(tempTable);
            } else {
                throw new Unauthorized();
            }
        }
    }

    @DeleteAction("/table/{id}")
    public void deleteTableById(int id) {
        PawTable tempTable = tableDao.findById(id);
        if(tempTable.getOwner().equals(me.email())) {
            tableDao.deleteById(id);
        } else {
            throw new Unauthorized();
        }
    }

    @PostAction("table/{id}/list")
    public PawTable addNewList(int id, PawList pawList) {
        PawTable tempTable = tableDao.findById(id);
        if(tempTable.getOwner().equals(me.email())) {
            LOG.info("Dodano liste {} do tablicy o id : {}", pawList.toString(), tempTable.getId());
            listDao.save(pawList);
            tempTable.addPawList(pawList);
            return tableDao.save(tempTable);
        } else {
            throw new Unauthorized();
        }
    }


    @GetAction("table/{id}/list")
    public List<PawList> getPawListListOnTable(int id) {
        PawTable table = tableDao.findById(id);
        if(table.getOwner().equals(me.email())) {
            return table.getPawLists();
        } else {
            throw new Unauthorized();
        }
    }

}
