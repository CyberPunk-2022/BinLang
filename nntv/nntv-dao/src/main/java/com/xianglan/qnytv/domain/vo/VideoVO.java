package com.xianglan.qnytv.domain.vo;

        import cn.hutool.core.util.IdUtil;
        import com.xianglan.qnytv.domain.UserInfo;
        import lombok.Data;

        import java.util.Date;

@Data
public class VideoVO {

    private String videoId; //视频id

    private String authorId; //创作者id

    private String categoryId; //分类Id

    private String url; //视频url

    private String title; //视频标题

    private String postUrl; //视频分后面

    private String authorName; //视频作者名称

    private String commentNum; //评论数量

    //目的是渲染前端，vue是数据驱动的视图，所以这里要返回唯一不重复的数据
    private String videoUuid = IdUtil.getSnowflake().nextId() + "";//视频临时uuid

    private UserInfo userInfo;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 创建时间
     */
    private String stars;

}
