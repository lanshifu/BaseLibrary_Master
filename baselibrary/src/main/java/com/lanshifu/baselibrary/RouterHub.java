
package com.lanshifu.baselibrary;

/**
 * ================================================
 * RouterHub 用来定义路由器的路由地址, 以组件名作为前缀, 对每个组件的路由地址进行分组, 可以统一查看和管理所有分组的路由地址
 * <p>
 * RouterHub 存在于基础库, 可以被看作是所有组件都需要遵守的通讯协议, 里面不仅可以放路由地址常量, 还可以放跨组件传递数据时命名的各种 Key 值
 * 再配以适当注释, 任何组件开发人员不需要事先沟通只要依赖了这个协议, 就知道了各自该怎样协同工作, 既提高了效率又降低了出错风险, 约定的东西自然要比口头上说强
 * <p>
 * 如果您觉得把每个路由地址都写在基础库的 RouterHub 中, 太麻烦了, 也可以在每个组件内部建立一个私有 RouterHub, 将不需要跨组件的
 * 路由地址放入私有 RouterHub 中管理, 只将需要跨组件的路由地址放入基础库的公有 RouterHub 中管理, 如果您不需要集中管理所有路由地址的话
 * 这也是比较推荐的一种方式
 * <p>
 * 路由地址的命名规则为 组件名 + 页面名, 如订单组件的订单详情页的路由地址可以命名为 "/order/OrderDetailActivity"
 * <p>
 * ARouter 将路由地址中第一个 '/' 后面的字符称为 Group, 比如上面的示例路由地址中 order 就是 Group, 以 order 开头的地址都被分配该 Group 下
 * 切记不同的组件中不能出现名称一样的 Group, 否则会发生该 Group 下的部分路由地址找不到的情况!!!
 * 所以每个组件使用自己的组件名作为 Group 是比较好的选择, 毕竟组件不会重名
 *
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki#3.4">RouterHub wiki 官方文档</a>
 * Created by JessYan on 30/03/2018 18:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface RouterHub {
    /**
     * 组名
     */
    String APP = "/app";//宿主 App 组件
    String VIDEO = "/video";//视频组件
    String PICTURE = "/picture";//视频组件
    String DEMO = "/demo";//demo组件
    String WANDROID = "/wandroid";//wandroid组件
    String MAP = "/map";//地图组件

    /**
     * 服务组件, 用于给每个组件暴露特有的服务
     */
    String SERVICE = "/service";


    /**
     * 视频分组
     */
    String VIDEO_SERVICE_VIDEO_INFO_SERVICE = VIDEO + SERVICE + "/VideoInfoService";

    String VIDEO_MAIN_ACTIVITY = VIDEO + "/VideoMainActivity";
    String VIDEO_MAIN_FRAGMENT = VIDEO + "/VideoMainFragment";

    /**
     * 图片分组
     */
    String PICTURE_SERVICE_PICTURE_INFO_SERVICE = PICTURE + SERVICE + "/PictureInfoService";
    String PICTURE_MAIN_ACTIVITY = PICTURE + "/PictureMainActivity";
    String PICTURE_MAIN_FRAGMENT = PICTURE + "/PictureMainFragment";

    /**
     * demo组件
     */
    String DEMO_SERVICE_DEMO_INFO_SERVICE = DEMO + SERVICE + "/DemoInfoService";
    String DEMO_MAIN_ACTIVITY = DEMO + "/DemoMainActivity";

    /**
     * wandroid组件
     */
    String WANDROID_SERVICE_DEMO_INFO_SERVICE = WANDROID + SERVICE + "/WandroidInfoService";
    String WANDROID_LOGIN_ACTIVITY = WANDROID + "/WandroidLoginActivity";
    String WANDROID_MAIN_FRAGMENT= WANDROID + "/WandroidMainFragment";


    /**
     * map组件
     */
    String MAP_SERVICE_MAP_INFO_SERVICE = MAP + SERVICE + "/MapInfoService";
    String MAP_MAIN_ACTIVITY = MAP + "/MapMainActivity";



}
