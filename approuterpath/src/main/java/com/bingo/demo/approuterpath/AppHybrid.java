package com.bingo.demo.approuterpath;

import com.bingo.router.annotations.PathClass;

@PathClass("apphybrid")
public class AppHybrid {
    @PathClass("apphybrid/web")
    public class Web {

    }

    @PathClass("apphybrid/empty")
    public class Empty {

    }
}
