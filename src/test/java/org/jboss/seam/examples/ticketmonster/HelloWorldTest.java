package org.jboss.seam.examples.ticketmonster;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloWorldTest
{
   @Test
   public void testGetText() {
      HelloWorld fixture = new HelloWorld();
      assertEquals("Hello World!", fixture.getText());
   }
}
