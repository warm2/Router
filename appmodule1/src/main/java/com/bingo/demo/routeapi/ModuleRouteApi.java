package com.bingo.demo.routeapi;

import com.bingo.router.IRoute;
import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.Route;

public interface ModuleRouteApi {

    @Route("app/read")
    IRoute goRead(@Parameter String title);

    @Route("test/user/detail")
    IRoute goUserDetail(@Parameter long id);
}
