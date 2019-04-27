package com.msgc.service.impl;

import com.msgc.constant.SessionKey;
import com.msgc.entity.Comment;
import com.msgc.entity.User;
import com.msgc.repository.ICommentRepository;
import com.msgc.service.ICommentService;
import com.msgc.utils.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
/**
* Type: CommentServiceImpl
* Description: serviceImp
* @author LYM
 */
@Service
@CacheConfig(cacheNames = "commentCache")//缓存key: tableId value: List<comment>
public class CommentServiceImpl implements ICommentService{

    //评论表时 parentId 为 -1
    private static final int ROOT_COMMENT_PARENT_ID = -1;

    private final ICommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     *  删除一条评论
     * @param id 要删除的评论的 ID
     */
    @Override
	public void deleteById(Integer id) {
		commentRepository.setDeleteStateById(id);
	}
    
    /**
     * 添加一条评论，该方法未校验 表id(tableId) 对应的表是否存在，未校验目标评论是否存在
     * @param tableId 目标表
     * @throws RuntimeException 格式不正确等
     */
    @Override
    public Comment addComment(Integer tableId) throws RuntimeException{
        HttpServletRequest request = WebUtil.getRequest();
        HttpSession session = request.getSession();
        Integer userId = ((User)session.getAttribute(SessionKey.USER)).getId();
        String content = request.getParameter("commentContent");
        //String aimCommentIdStr = request.getParameter("aimCommentId");
        Comment comment = new Comment();
        // 如果不存在回复的 目标评论Id 则目标评论Id 为 -1， 表示直接评论表
        comment.setParentId(ROOT_COMMENT_PARENT_ID);
        if(StringUtils.isEmpty(content)){
            throw new RuntimeException("评论内容为空");
        }else if(content.startsWith("[reply]") && content.contains("[/reply]")) {
            //是回复
            int cutIndex = content.indexOf("[/reply]");
            String replyAim = content.substring(7, cutIndex);
            comment.setParentId(Integer.parseInt(replyAim));
            content = content.substring(cutIndex + 8);
        }
        comment.setTableId(tableId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setEffective(true);
        return commentRepository.save(comment);
    }

    /**
     * 根据 收集表 ID 获取该表所有的未删除的评论
     * @param tableId 收集表 ID
     * @return 该表所有的未删除的评论
     */
    @Override
    @Cacheable(sync = true)
    public List<Comment> findAllEffectiveByTableId(Integer tableId) {
        return commentRepository.findAllByTableIdAndEffective(tableId, true);
    }
}
