package com.paw.controller;


import act.controller.Controller;
import act.db.jpa.JPADao;
import com.paw.model.*;
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
public class TableController extends AuthenticatedController {

    private static final Logger LOG = LoggerFactory.getLogger(TableController.class);

    @Inject
    private JPADao<Integer, PawTable> tableDao;

    @Inject
    private JPADao<Integer, PawList> listDao;

    @Inject
    private JPADao<Integer, PawCard> cardDao;

    @Inject
    private JPADao<Integer, Attachment> attachmentDao;

    @Inject
    private JPADao<Integer, Comment> commentDao;

    @Inject
    private JPADao<Integer, History> historyDao;


    @PostAction("/table")
    public PawTable addTable(PawTable pawTable) {
        pawTable.setOwner(me.email);
        LOG.info("Dodano tablice: " + pawTable.toString());
        return tableDao.save(pawTable);

    }

    @GetAction("/table")
    public List<PawTable> getTableList() {
        if (me != null) {
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
        if (table.getOwner().equals(me.email())) {
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
            if (tempTable.getOwner().equals(me.email())) {
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
        if (tempTable.getOwner().equals(me.email())) {
            LOG.info("Usunięto tablice o id : {}", tempTable.getTableId());
            tableDao.deleteById(id);
        } else {
            throw new Unauthorized();
        }
    }

    @PostAction("table/{id}/list")
    public PawTable addNewList(int id, PawList pawList) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
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
        if (table.getOwner().equals(me.email())) {
            return table.getPawLists().stream().map(listId -> listDao.findById(listId)).collect(Collectors.toList());
        } else {
            throw new Unauthorized();
        }
    }

    @PostAction("table/{id}/list/{listId}/card")
    public PawTable addNewCard(int id, int listId, PawCard pawCard) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            PawList tempPawlist = listDao.findById(listId);
            History history = new History();
            history.setHistory("Utworzono kartę " + pawCard.getName());
            history = historyDao.save(history);
            pawCard.addIdToHistoryList(history.getHistoryId());
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
        if (tempTable.getOwner().equals(me.email())) {
            PawList tempPawlist = listDao.findById(listId);
            return tempPawlist.getPawCardList().stream().map(cardId -> cardDao.findById(cardId)).collect(Collectors.toList());
        } else {
            throw new Unauthorized();
        }
    }

    @PutAction("table/{id}/list/{listId}/card/{cardId}")
    public PawCard editCard(int id, int listId, int cardId, PawCard pawCard) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            PawCard tempPawCard = cardDao.findById(cardId);
            History history = new History();

            if (!(pawCard.getDescription() == null) && !pawCard.getDescription().equals("")) {
                tempPawCard.setDescription(pawCard.getDescription());
                history.setHistory("Zmieniono opis karty na  " + pawCard.getDescription());
            }
            if (!(pawCard.getName() == null) && !pawCard.getName().equals("")) {
                tempPawCard.setName(pawCard.getName());
                history.setHistory("Zmieniono nazwe karty na  " + pawCard.getName());
            }
            history = historyDao.save(history);
            pawCard.addIdToHistoryList(history.getHistoryId());
            return cardDao.save(tempPawCard);
        } else {
            throw new Unauthorized();
        }
    }

    @DeleteAction("table/{id}/list/{listId}/card/{cardId}")
    public PawList deleteCard(int id, int listId, int cardId) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            PawList pawList = listDao.findById(listId);
            pawList.deleteIdFromList(cardId);
            listDao.save(pawList);
            cardDao.deleteById(cardId);
            return pawList;
        } else {
            throw new Unauthorized();
        }
    }

    @PostAction("table/{id}/list/{listId}/card/{cardId}/archive")
    public PawCard archiveCard(int id, int listId, int cardId) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            PawList pawList = listDao.findById(listId);
            pawList.deleteIdFromList(cardId);
            listDao.save(pawList);
            PawCard card = cardDao.findById(cardId);
            card.setStatus(false);
            card.setBelongsToListId(listId);

            History history = new History();
            history.setHistory("Zarchiwizowano karte " + card.getName());
            history = historyDao.save(history);
            card.addIdToHistoryList(history.getHistoryId());
            return cardDao.save(card);
        } else {
            throw new Unauthorized();
        }
    }

    @GetAction("table/{id}/card/archive")
    public List<PawCard> getArchiveCard(int id) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            return cardDao.findAllAsList().stream().filter(card -> !card.isStatus()).collect(Collectors.toList());
        } else {
            throw new Unauthorized();
        }
    }

    @DeleteAction("table/{id}/card/{cardId}/archive")
    public PawCard unarchiveCard(int id, int cardId) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            PawCard card = cardDao.findById(cardId);
            card.setStatus(true);
            PawList pawList = listDao.findById(card.getBelongsToListId());
            pawList.addIdToList(cardId);
            listDao.save(pawList);

            History history = new History();
            history.setHistory("Odarchiwizowano kartę " + card.getName());
            history = historyDao.save(history);
            card.addIdToHistoryList(history.getHistoryId());

            return cardDao.save(card);
        } else {
            throw new Unauthorized();
        }
    }

    @GetAction("table/{id}/list/{listId}/card/{cardId}")
    public PawCard getCardDetails(int id, int listId, int cardId) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            return cardDao.findById(cardId);
        } else {
            throw new Unauthorized();
        }
    }

    @PostAction("table/{id}/list/{listId}/card/{cardId}/attachment")
    public PawCard addAttachment(int id, int listId, int cardId, Attachment attachment) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            PawCard pawCard = cardDao.findById(cardId);

            pawCard.addIdToAttachmentList(attachmentDao.save(attachment).getFileId());

            History history = new History();
            history.setHistory("Dodano załącznik do karty o nazwie " + attachment.getFileName());
            history = historyDao.save(history);
            pawCard.addIdToHistoryList(history.getHistoryId());

            return cardDao.save(pawCard);
        } else {
            throw new Unauthorized();
        }
    }

    @GetAction("table/{id}/list/{listId}/card/{cardId}/attachment")
    public List<Attachment> getAttachmentList(int id, int listId, int cardId) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            PawCard pawCard = cardDao.findById(cardId);
            return pawCard.getAttachmentIdList().stream().map(fileId -> attachmentDao.findById(fileId)).collect(Collectors.toList());
        } else {
            throw new Unauthorized();
        }
    }

    @GetAction("table/{id}/list/{listId}/card/{cardId}/attachment/{fileId}")
    public Attachment getAttachment(int id, int listId, int cardId, int fileId) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            return attachmentDao.findById(fileId);
        } else {
            throw new Unauthorized();
        }
    }

    @DeleteAction("table/{id}/list/{listId}/card/{cardId}/attachment/{fileId}")
    public PawCard deleteAttachment(int id, int listId, int cardId, int fileId) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            PawCard pawCard = cardDao.findById(cardId);
            pawCard.deleteIdFromAttachmentList(fileId);
            attachmentDao.deleteById(fileId);

            History history = new History();
            history.setHistory("Usunięto załącznik z karty");
            history = historyDao.save(history);
            pawCard.addIdToHistoryList(history.getHistoryId());

            return cardDao.save(pawCard);
        } else {
            throw new Unauthorized();
        }
    }

    @GetAction("table/{id}/allattachment")
    public List<Attachment> getAllAttachment(int id) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            return attachmentDao.findAllAsList();
        } else {
            throw new Unauthorized();
        }
    }

    @PostAction("table/{id}/list/{listId}/card/{cardId}/comment/{commentId}/attachment/{fileId}")
    public Comment addAttachmentToComment(int id, int listId, int cardId, int commentId, int fileId) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            Comment comment = commentDao.findById(commentId);
            comment.addIdToAttachmentList(fileId);
            return commentDao.save(comment);
        } else {
            throw new Unauthorized();
        }
    }

    @DeleteAction("table/{id}/list/{listId}/card/{cardId}/comment/{commentId}/attachment/{fileId}")
    public Comment deleteAttachmentFromComment(int id, int listId, int cardId, int commentId, int fileId) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            Comment comment = commentDao.findById(commentId);
            comment.deleteIdFromAttachmentList(fileId);
            return commentDao.save(comment);
        } else {
            throw new Unauthorized();
        }
    }



    @PostAction("table/{id}/list/{listId}/card/{cardId}/comment")
    public PawCard addComment(int id, int listId, int cardId, Comment comment) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            PawCard pawCard = cardDao.findById(cardId);
            comment.setAuthorName(me.email);
            comment = commentDao.save(comment);
            pawCard.addIdToCommentList(comment.getCommentId());

            History history = new History();
            history.setHistory("Dodano komentarz do karty od uzytkownika " + comment.getAuthorName());
            history = historyDao.save(history);
            pawCard.addIdToHistoryList(history.getHistoryId());

            return cardDao.save(pawCard);
        } else {
            throw new Unauthorized();
        }
    }

    @GetAction("table/{id}/list/{listId}/card/{cardId}/comment")
    public List<Comment> getCommentList(int id, int listId, int cardId) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            PawCard pawCard = cardDao.findById(cardId);
            return pawCard.getCommentIdList().stream().map(commentId -> commentDao.findById(commentId)).collect(Collectors.toList());
        } else {
            throw new Unauthorized();
        }
    }

    @GetAction("table/{id}/list/{listId}/card/{cardId}/comment/{commentId}")
    public Comment getComment(int id, int listId, int cardId, int commentId) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            return commentDao.findById(commentId);
        } else {
            throw new Unauthorized();
        }
    }

    @PutAction("table/{id}/list/{listId}/card/{cardId}/comment/{commentId}")
    public Comment editComment(int id, int listId, int cardId, int commentId, Comment comment) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            Comment tempComment = commentDao.findById(commentId);
            if (tempComment.getAuthorName().equals(me.email())) {
                if(!comment.getContent().equals("") && !(comment.getContent() ==null)) {
                    tempComment.setContent(comment.getContent());
                }
            }
            return commentDao.save(tempComment);
        } else {
            throw new Unauthorized();
        }
    }

    @DeleteAction("table/{id}/list/{listId}/card/{cardId}/comment/{commentId}")
    public PawCard deleteComment(int id, int listId, int cardId, int commentId) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            PawCard pawCard = cardDao.findById(cardId);
            pawCard.deleteCommentIdFromList(commentId);
            Comment comment = commentDao.findById(commentId);
            commentDao.deleteById(commentId);

            History history = new History();
            history.setHistory("Usunięto komentarz od " + comment.getAuthorName());
            history = historyDao.save(history);
            pawCard.addIdToHistoryList(history.getHistoryId());
            return cardDao.save(pawCard);
        } else {
            throw new Unauthorized();
        }
    }

    @GetAction("table/{id}/list/{listId}/card/{cardId}/history")
    public List<History> getCardHistory(int id, int listId, int cardId, int commentId) {
        PawTable tempTable = tableDao.findById(id);
        if (tempTable.getOwner().equals(me.email())) {
            return cardDao.findById(cardId).getHistoryIdList().stream().map(historyId -> historyDao.findById(historyId)).collect(Collectors.toList());
        } else {
            throw new Unauthorized();
        }
    }
}
