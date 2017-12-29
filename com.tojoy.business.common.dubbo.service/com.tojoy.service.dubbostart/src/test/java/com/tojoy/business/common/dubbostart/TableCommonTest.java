package com.tojoy.business.common.dubbostart;

/**
 * Created by 王艳胜 on 2017/12/21.
 */
public class TableCommonTest {

   /* @Resource
    private ITableCommonService tableCommonService;

    @Test
    public  void  test(){
        Map map = new HashMap();
        map.put("sql","DROP TABLE IF EXISTS `tjy_thumb_up`;\n" +
                "CREATE TABLE `tjy_thumb_up` (\n" +
                "  `id` varchar(36) CHARACTER SET utf8 NOT NULL COMMENT '主键',\n" +
                "  `user_id` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户ID',\n" +
                "  `fk_id` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT 'fkid',\n" +
                "  `create_time` datetime DEFAULT NULL COMMENT '操作时间',\n" +
                "  `type` int(11) DEFAULT NULL COMMENT '类型（1:会议）',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务点赞表';");
        tableCommonService.createTable(map);
    }*/
}
