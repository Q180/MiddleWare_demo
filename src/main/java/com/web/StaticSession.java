package com.web;

import com.web.webSocket.MyWebSocketHandler;
import lombok.Getter;
import lombok.Setter;

public class StaticSession {
    @Getter
    @Setter
    private static MyWebSocketHandler myWebSocketHandler;
}
