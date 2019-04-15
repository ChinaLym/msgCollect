package com.msgc.service;

import com.msgc.entity.Comment;

import java.util.List;

public interface ICommentService {

    Comment save(Comment comment);

    Comment findById(Integer id);
    
    List<Comment> findAllById(List<Integer> idList);
    
    List<Comment> findAll(Comment commentExample);
    
    List<Comment> findAll();
    
    void deleteById(Integer id);
    
    List<Comment> save(List<Comment> commentList);

    void addComment(Integer tableId);
}
