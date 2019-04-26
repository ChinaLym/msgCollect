package com.msgc.service;

import com.msgc.entity.Comment;

import java.util.List;

public interface ICommentService {
    
    void deleteById(Integer id);
    
    Comment addComment(Integer tableId);

    List<Comment> findAllEffectiveByTableId(Integer tableId);
}
