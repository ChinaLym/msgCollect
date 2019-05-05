package com.msgc.service;

import com.msgc.entity.Comment;
import com.msgc.entity.Table;

import java.util.List;

public interface ICommentService {

    Comment findById(Integer id);

    void deleteById(Integer id);
    
    Comment addComment(Table table);

    List<Comment> findAllEffectiveByTableId(Integer tableId);
}
