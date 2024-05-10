package com.santander.peliculacrud;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"com.santander.peliculacrud.servicetest",
        "com.santander.peliculacrud.controllertest",
})

public class AllUnitTests {
}
