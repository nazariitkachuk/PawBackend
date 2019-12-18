package com.paw.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int commentId;
    String authorName;
    String content;
    @ElementCollection
    List<Integer> attachmentIdList = new ArrayList<>();

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void addIdToAttachmentList(int id) {
        attachmentIdList.add(id);
    }

    public void deleteIdFromAttachmentList(int id) {
        attachmentIdList.remove(attachmentIdList.indexOf(id));
    }

    public void setContent(String content) {
        this.content = content;
    }
}
