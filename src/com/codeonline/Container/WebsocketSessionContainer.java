package com.codeonline.Container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhf on 2016/8/6.
 */
public class WebsocketSessionContainer {
    public static Map<String,Object> webSocketSessions=new ConcurrentHashMap<>();
}
