package com.paw.controller;


import act.controller.Controller;
import act.db.jpa.JPADao;
import com.paw.model.PawCard;
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

    @Inject
    private JPADao<Integer, PawCard> cardDao;

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
            } catch (Exception e) {
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
        if (id != pawTable.getTableId()) {
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
            LOG.info("Usunięto tablice o id : {}", tempTable.getTableId());
            tableDao.deleteById(id);
        } else {
            throw new Unauthorized();
        }
    }

    @PostAction("table/{id}/list")
    public PawTable addNewList(int id, PawList pawList) {
        PawTable tempTable = tableDao.findById(id);
        if(tempTable.getOwner().equals(me.email())) {
            LOG.info("Dodano liste {} do tablicy o id : {}", pawList.toString(), tempTable.getTableId());
            tempTable.addIdToList(listDao.save(pawList).getListId());
            return tableDao.save(tempTable);
        } else {
            throw new Unauthorized();
        }
    }

    @GetAction("table/{id}/list")
    public List<PawList> getPawListListOnTable(int id) {
        PawTable table = tableDao.findById(id);
        if(table.getOwner().equals(me.email())) {
            return  table.getPawLists().stream().map(listId -> listDao.findById(listId)).collect(Collectors.toList());
        } else {
            throw new Unauthorized();
        }
    }

    @PostAction("table/{id}/list/{listId}/card")
    public PawTable addNewCard(int id, int listId, PawCard pawCard) {
        PawTable tempTable = tableDao.findById(id);
        if(tempTable.getOwner().equals(me.email())) {
            PawList tempPawlist = listDao.findById(listId);
            tempPawlist.addIdToList(cardDao.save(pawCard).getCardId());
            listDao.save(tempPawlist);
            return tableDao.findById(id);
        } else {
            throw new Unauthorized();
        }
    }

    @GetAction("table/{id}/list/{listId}/card")
    public List<PawCard> getCartList(int id, int listId) {
        PawTable tempTable = tableDao.findById(id);
        if(tempTable.getOwner().equals(me.email())) {
            PawList tempPawlist = listDao.findById(listId);
            return tempPawlist.getPawCardList().stream().map(cardId -> cardDao.findById(cardId)).collect(Collectors.toList());
        } else {
            throw new Unauthorized();
        }
    }

    @PutAction("table/{id}/list/{listId}/card/{cardId}")
    public PawCard editCard(int id, int listId,int cardId, PawCard pawCard) {
        PawTable tempTable = tableDao.findById(id);
        if(tempTable.getOwner().equals(me.email())) {
            PawCard tempPawCard = cardDao.findById(cardId);

            if(!(pawCard.getDescription() == null) && !pawCard.getDescription().equals("")) {
                tempPawCard.setDescription(pawCard.getDescription());
            }
            if(!(pawCard.getName() == null) && !pawCard.getName().equals("")) {
                tempPawCard.setName(pawCard.getName());
            }
            return cardDao.save(tempPawCard);
        } else {
            throw new Unauthorized();
        }
    }

    @DeleteAction("table/{id}/list/{listId}/card/{cardId}")
    public PawList deleteCard(int id, int listId, int cardId) {
        PawTable tempTable = tableDao.findById(id);
        if(tempTable.getOwner().equals(me.email())) {
        PawList pawList = listDao.findById(listId);
        pawList.deleteIdFromList(cardId);
        listDao.save(pawList);
        cardDao.deleteById(cardId);
        return pawList;
        } else {
            throw new Unauthorized();
        }
    }

    @GetAction("table/{id}/list/{listId}/card/{cardId}")
    public PawCard getCardDetails(int id, int listId, int cardId) {
        PawTable tempTable = tableDao.findById(id);
        if(tempTable.getOwner().equals(me.email())) {
            return cardDao.findById(cardId);
        } else {
            throw new Unauthorized();
        }
    }
}
