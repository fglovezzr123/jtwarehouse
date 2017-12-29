package com.tojoy.business.common.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tojoy.business.common.api.ICommentThumbUpService;
import com.tojoy.business.common.bean.CommentThumbUp;
import com.tojoy.business.common.dao.CommentThumbUpDao;
import com.tojoy.business.common.model.DataGrid;
import com.tojoy.business.common.model.PageParam;
import com.tojoy.business.common.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyansheng
 * @version 1.0
 * @date 2017-04-04 15:00:56
 */
@Service
public class CommentThumbUpServiceImpl implements ICommentThumbUpService {

    @Resource
    private CommentThumbUpDao commentThumbUpDao;

    @Override
    public List selectCommentThumbUpList(CommentThumbUp commentThumbUp) {
        return commentThumbUpDao.queryList(commentThumbUp);
    }

    @Override
    public int insert(CommentThumbUp t) {
        t.setId(UUIDGenerator.getUUID());
        return commentThumbUpDao.insert(t);
    }


    @Override
    public int delete(CommentThumbUp t) {
        return commentThumbUpDao.delete(t);
    }

    @Override
    public Map<String, Object> queryById(CommentThumbUp commentThumbUp) {
        return commentThumbUpDao.queryById(commentThumbUp);
    }

    @Override
    public int queryCount(CommentThumbUp commentThumbUp) {
        return commentThumbUpDao.queryCount(commentThumbUp);
    }

}