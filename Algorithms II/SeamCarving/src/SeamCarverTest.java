import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import edu.princeton.cs.algs4.Picture;
import junit.framework.TestCase;

public class SeamCarverTest extends TestCase {
    private Picture pic;
    
    @Override
    protected void setUp() throws Exception {
        System.out.println("Setting it up!");
        this.pic = new Picture(100, 50);
    }
    
    @Test
    public void testSeamCarver() {
        SeamCarver sm = new SeamCarver(pic);
        //this.pic.set(1, 5, new Color(10));
        Picture sPic = sm.picture();
        assertNotNull(sPic);
        assertNotSame(this.pic, sPic);
    }

    @Test
    public void testPicture() {
        fail("Not yet implemented");
    }

    @Test
    public void testWidth() {
        fail("Not yet implemented");
    }

    @Test
    public void testHeight() {
        fail("Not yet implemented");
    }

    @Test
    public void testEnergy() {
        fail("Not yet implemented");
    }

    @Test
    public void testFindHorizontalSeam() {
        fail("Not yet implemented");
    }

    @Test
    public void testFindVerticalSeam() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveHorizontalSeam() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveVerticalSeam() {
        fail("Not yet implemented");
    }

}
