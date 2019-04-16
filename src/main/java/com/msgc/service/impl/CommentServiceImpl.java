package com.msgc.service.impl;

import com.msgc.constant.SessionKey;
import com.msgc.entity.Comment;
import com.msgc.entity.User;
import com.msgc.repository.ICommentRepository;
import com.msgc.service.ICommentService;
import com.msgc.utils.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;
/**
* Type: CommentServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
public class CommentServiceImpl implements ICommentService{

    //评论表时 parentId 为 -1
    private static final int ROOT_COMMENT_PARENTID = -1;

    private final ICommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
    
    @Override
    public Comment findById(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElse(null);
    }
    
    @Override
    public List<Comment> findAllById(List<Integer> idList) {
        return commentRepository.findAllById(idList);
    }
    
    @Override
    public List<Comment> findAll(Comment commentExample) {
        return commentRepository.findAll(Example.of(commentExample));
    }
    
    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
	public void deleteById(Integer id) {
		commentRepository.deleteById(id);
	}
    
    @Override
    public List<Comment> save(List<Comment> commentList) {
        return commentRepository.saveAll(commentList);
    }

    /**
     * 添加一条评论，该方法未校验 表id(tableId) 对应的表是否存在，未校验目标评论是否存在
     * @param tableId 目标表
     * @throws RuntimeException 格式不正确等
     */
    @Override
    public void addComment(Integer tableId) throws RuntimeException{
        HttpServletRequest request = WebUtil.getRequest();
        HttpSession session = request.getSession();
        Integer userId = ((User)session.getAttribute(SessionKey.USER)).getId();
        String content = request.getParameter("commentContent");
        String aimCommentIdStr = request.getParameter("aimCommentId");
        if(StringUtils.isBlank(content)){
            throw new RuntimeException("评论内容为空");
        }
        Comment comment = new Comment();
        comment.setTableId(tableId);
        comment.setUserId(userId);
        // 如果不存在回复的 目标评论Id 则目标评论Id 为 -1， 表示直接评论表
        comment.setParentId(StringUtils.isNotEmpty(aimCommentIdStr)
                ? Integer.parseInt(aimCommentIdStr)
                : ROOT_COMMENT_PARENTID);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setEffective(true);
        commentRepository.save(comment);
    }
}
