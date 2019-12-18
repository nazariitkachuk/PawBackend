package com.paw.model;

import jline.internal.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paw_cards")
public class PawCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cardId;
    private String name;
    private String description;
    @Nullable
    private boolean status = true;
    @Nullable
    private int belongsToListId = 0;
    @ElementCollection
    List<Integer> commentIdList = new ArrayList<>();
    @ElementCollection
    List<Integer> attachmentIdList = new ArrayList<>();
    @ElementCollection
    List<Integer> historyIdList = new ArrayList<>();

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getBelongsToListId() {
        return belongsToListId;
    }

    public void setBelongsToListId(int belongsToListId) {
        this.belongsToListId = belongsToListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getCommentIdList() {
        return commentIdList;
    }

    public void addIdToCommentList(int id) {
        commentIdList.add(id);
    }

    public void deleteCommentIdFromList(int id) {
        commentIdList.remove(commentIdList.indexOf(id));
    }

    public void setCommentIdList(List<Integer> commentIdList) {
        this.commentIdList = commentIdList;
    }

    public List<Integer> getAttachmentIdList() {
        return attachmentIdList;
    }

    public void addIdToAttachmentList(int id) {
        attachmentIdList.add(id);
    }

    public void deleteIdFromAttachmentList(int id) {
        attachmentIdList.remove(attachmentIdList.indexOf(id));
    }

    public void addIdToHistoryList(int id) {
        historyIdList.add(id);
    }

    public void deleteIdFromHistoryList(int id) {
        historyIdList.remove(historyIdList.indexOf(id));
    }

    public List<Integer> getHistoryIdList() {
        return historyIdList;
    }

    public void setHistoryIdList(List<Integer> historyIdList) {
        this.historyIdList = historyIdList;
    }

    public void setAttachmentIdList(List<Integer> attachmentIdList) {
        this.attachmentIdList = attachmentIdList;
    }
}
