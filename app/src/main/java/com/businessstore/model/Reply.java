package com.businessstore.model;


import java.io.Serializable;
import java.util.List;

/**
 * class replay
 *
 * @author wuxi
 * @date 2018/9/18
 */
public class Reply implements Serializable{
    private static final long serialVersionUID = -615823929477263404L;

    private int commentId;
    private int identityId;
    private int identityType;
    private String commentCon;
    private int replyId;
    private String createdAt;
    private String name;
    private String head;
    private List<Reply> reply;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getIdentityId() {
        return identityId;
    }

    public void setIdentityId(int identityId) {
        this.identityId = identityId;
    }

    public int getIdentityType() {
        return identityType;
    }

    public void setIdentityType(int identityType) {
        this.identityType = identityType;
    }

    public String getCommentCon() {
        return commentCon;
    }

    public void setCommentCon(String commentCon) {
        this.commentCon = commentCon;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public List<Reply> getReply() {
        return reply;
    }

    public void setReply(List<Reply> reply) {
        this.reply = reply;
    }
}
