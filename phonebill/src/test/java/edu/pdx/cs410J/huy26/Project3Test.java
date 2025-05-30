package edu.pdx.cs410J.huy26;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project3</code> class.  This is different
 * from <code>Project3IT</code> which is an integration test (and can handle the calls
 * to {@link System#exit(int)} and the like.
 */
public class Project3Test {
  @Test
  public void readmeCanBeReadAsResource() throws IOException {
    try (
            InputStream readme = Project3.class.getResourceAsStream("README.txt");
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("This is a README file!"));
    }
  }
}
