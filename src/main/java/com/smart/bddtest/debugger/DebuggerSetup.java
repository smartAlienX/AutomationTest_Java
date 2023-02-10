package com.smart.bddtest.debugger;

import io.cucumber.java.en.Given;

public class DebuggerSetup {

    @Given("Client debug start")
    public void debugStart() {
        System.out.println("debugStart");
    }

    @Given("Client debug {} start")
    public void debugStart(String a) {
        System.out.println("debugStart");
    }

}
