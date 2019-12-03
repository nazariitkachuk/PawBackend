package com.paw.model;

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
    @ElementCollection
    List<Integer> commentIdList = new ArrayList<>();
    @ElementCollection
    List<Integer> attachmentIdList = new ArrayList<>();

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
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

    public void setAttachmentIdList(List<Integer> attachmentIdList) {
        this.attachmentIdList = attachmentIdList;
    }
}
