package com.storehouse.service.impl;

import com.storehouse.dao.ThumbsDao;
import com.storehouse.dao.impl.ThumbsDaoImpl;
import com.storehouse.entity.Thumbs;
import com.storehouse.service.ThumbsService;
import com.storehouse.utils.BaseUtils;

import java.sql.Connection;
import java.util.List;

/**
 * 创建点赞相关接口
 */
public class ThumbsServiceImpl implements ThumbsService {
    /**
     * 添加
     *
     * @param thumbs 实体类对象
     * @return 受影响的行数
     */
    @Override
    public int addThumbs(Thumbs thumbs) {
        int jg = 0;
        Connection conn = BaseUtils.getConnection();
        try {

            ThumbsDao td = new ThumbsDaoImpl(conn);
            jg = td.addThumbs(thumbs);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseUtils.closeAll(null, null, conn);
        }
        return jg;
    }

    /**
     * 删除
     *
     * @param thumbsId 根据id编号删除
     * @return 返回受影响的行数
     */
    @Override
    public int delThumbs(int thumbsId) {
        int jg = 0;
        Connection conn = BaseUtils.getConnection();
        try {

            ThumbsDao td = new ThumbsDaoImpl(conn);
            jg = td.delThumbs(thumbsId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            BaseUtils.closeAll(null, null, conn);
        }
        return jg;
    }

    /**
     * 修改
     *
     * @return 返回受影响的行数
     */
    @Override
    public int updateThumbs(Thumbs thumbs) {
        int jg;
        Connection conn = BaseUtils.getConnection();
        ThumbsDao td = new ThumbsDaoImpl(conn);
        jg = td.updateThumbs(thumbs);
        BaseUtils.closeAll(null, null, conn);
        return jg;
    }

    /**
     * 查询所有
     *
     * @return 返回list集合
     */
    @Override
    public List<Thumbs> queryThumbs() {
        Connection con = BaseUtils.getConnection();
        List<Thumbs> list = new ThumbsDaoImpl(con).queryThumbs();
        BaseUtils.closeAll(null, null, con);
        return list;
    }

    /**
     * 查询是否存在对应uid与文章id的点赞记录
     *
     * @param uid       用户id
     * @param articleId 文章id
     * @return 返回数量
     */
    @Override
    public int ThumbsByUidAndArticleId(int uid, int articleId) {
        Connection conn = BaseUtils.getConnection();
        int count = new ThumbsDaoImpl(conn).ThumbsByUidAndArticleId(uid, articleId);
        BaseUtils.closeAll(null, null, conn);
        return count;
    }
}
