package com.tojoy.business.common.impl;

import com.tojoy.business.common.api.ITableCommonService;
import com.tojoy.business.common.dao.TableCommonDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyansheng
 * @version 1.0
 * @date 2017-11-27
 */
@Service
public class TableCommonServiceImpl implements ITableCommonService {

    @Resource
    private TableCommonDao tableCommonDao;

    @Override
    public int createTable(String key) {

        Map map = new HashMap();
        if (!this.isExistTable("tjy_thumb_up_" + key)) {
            map.put("thumbUpSql", "CREATE TABLE tjy_thumb_up_" + key + " (\n" +
                    "  `id` varchar(36) CHARACTER SET utf8 NOT NULL COMMENT '主键',\n" +
                    "  `user_id` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户ID',\n" +
                    "  `fk_id` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT 'fkid',\n" +
                    "  `create_time` datetime DEFAULT NULL COMMENT '操作时间',\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务点赞表';");
        }
        if (!this.isExistTable("tjy_collection_" + key)) {
            map.put("collectionSql", "CREATE TABLE `tjy_collection_" + key + "` (\n" +
                    "  `id` varchar(36) NOT NULL,\n" +
                    "  `user_id` varchar(36) DEFAULT NULL,\n" +
                    "  `fk_id` varchar(36) DEFAULT NULL,\n" +
                    "  `create_time` datetime DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务收藏表';\"");
        }
        if (!this.isExistTable("tjy_comment_" + key)) {

            map.put("commentSql", "CREATE TABLE `tjy_comment_" + key + "` (\n" +
                    "  `id` varchar(32) CHARACTER SET utf8 NOT NULL,\n" +
                    "  `parent_id` varchar(32) DEFAULT NULL COMMENT '上一级评论ID',\n" +
                    "  `user_id` varchar(32) DEFAULT NULL COMMENT '评论人ID',\n" +
                    "  `fk_id` varchar(36) DEFAULT NULL COMMENT '评论类别对应表的主键(如话题表ID)',\n" +
                    "  `comment_desc` varchar(2000) CHARACTER SET utf8 DEFAULT NULL COMMENT '评论内容',\n" +
                    "  `anonymous` int(11) DEFAULT NULL COMMENT '是否匿名评论，0否1是',\n" +
                    "  `img_url` varchar(200) DEFAULT NULL COMMENT '图片url',\n" +
                    "  `status` int(11) DEFAULT NULL COMMENT '状态',\n" +
                    "  `create_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                    "  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人ID',\n" +
                    "  `update_time` datetime DEFAULT NULL COMMENT '修改时间',\n" +
                    "  `update_user_id` varchar(32) DEFAULT NULL COMMENT '修改人ID',\n" +
                    "  `deleted` int(1) DEFAULT NULL COMMENT '逻辑删除标识',\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论信息表';");
        }

        if (!this.isExistTable("tjy_comment_thumb_up_" + key)) {
            map.put("commentThumbUpSql", "CREATE TABLE `tjy_comment_thumb_up_" + key + "` (\n" +
                    "  `id` varchar(32) NOT NULL,\n" +
                    "  `user_id` varchar(32) DEFAULT NULL COMMENT '点赞人 ',\n" +
                    "  `fk_id` varchar(32) DEFAULT NULL COMMENT '评论id ',\n" +
                    "  `create_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                    "  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人ID',\n" +
                    "  `update_time` datetime DEFAULT NULL COMMENT '修改时间',\n" +
                    "  `update_user_id` varchar(32) DEFAULT NULL COMMENT '修改人ID',\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论点赞表';");
        }
        if (!this.isExistTable("tjy_share_" + key)) {
            map.put("shareSql", "CREATE TABLE `tjy_share_" + key + "` (\n" +
                    "  `id` varchar(36) NOT NULL,\n" +
                    "  `user_id` varchar(36) DEFAULT NULL,\n" +
                    "  `fk_id` varchar(36) DEFAULT NULL,\n" +
                    "  `create_time` datetime DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='转发分享表';");
        }

        if (!this.isExistTable("tjy_reward_" + key)) {
            map.put("rewardSql", "CREATE TABLE `tjy_reward_" + key + "` (\n" +
                    "  `id` varchar(36) NOT NULL,\n" +
                    "  `user_id` varchar(36) DEFAULT NULL,\n" +
                    "  `fk_id` varchar(36) DEFAULT NULL,\n" +
                    "  `jb_amount` decimal(22,2) NULL DEFAULT 0.00  COMMENT 'J币金额',\n" +
                    "  `create_time` datetime DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='打赏信息表';");

        }
        if(map.size()==0){
            return 0;
        }
        return tableCommonDao.createTable(map);
    }

    @Override
    public boolean isExistTable(String tableName) {
        Map map = new HashMap();
        map.put("dbName", "mall");
        map.put("tableName", tableName);
        return tableCommonDao.isExistTable(map) == 0 ? false : true;
    }
}