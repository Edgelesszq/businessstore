package com.businessstore.util;

import com.businessstore.model.Reply;

import java.util.ArrayList;
import java.util.List;


public class HaveReplyUtil {

    private static List<Reply> mReplyList = new ArrayList<>();
    /**
     * 递归获取所有的回复
     * @param replyList 消息列表
     * @param i 节点
     * @return mReply
     */
    public static  List<Reply> haveReply(List<Reply> replyList, int i){
        if (replyList.get(i).getReply()!=null){
            for (int j = 0; j < replyList.get(i).getReply().size();j++) {
                mReplyList.addAll(replyList.get(i).getReply());
                return haveReply(replyList.get(i).getReply(),j);
            }
        }
        return mReplyList;
    }

    public static List<Reply> haveReplyX(Reply reply, int i){
        if (reply.getReply()!=null){
            for (int j = 0; j < reply.getReply().size();j++) {
                mReplyList.addAll(reply.getReply());
                return haveReply(reply.getReply(),j);
            }
        }
        return mReplyList;
    }
}
