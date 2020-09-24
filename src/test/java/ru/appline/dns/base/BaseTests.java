package ru.appline.dns.base;

import org.junit.After;
import org.junit.Before;
import ru.appline.frameworks.dns.managers.InitManager;
import ru.appline.frameworks.dns.managers.ManagerPages;

public class BaseTests {
    protected ManagerPages app = ManagerPages.getManagerPages();
    @Before
    public void beforeEach() {
        InitManager.initFramework();
    }
    @After
    public void afterEach() {
        InitManager.quitFramework();
    }
}
