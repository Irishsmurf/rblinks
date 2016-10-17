package com.redbrick.devchat.rblinks;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.redbrick.devchat.rblinks.RbLinkbot;

public class TestRbLinkBot {

  @Test
  public void testSetName() {
    RbLinkbot bot = new RbLinkbot("SomeName");
    assertEquals(bot.getName(), "SomeName");
  }
}
