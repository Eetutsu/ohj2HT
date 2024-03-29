package ajastin.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import ajastin.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2023.04.28 12:56:42 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class AjastinTest {



  // Generated by ComTest BEGIN
  /** 
   * testPoistaPeli65 
   * @throws Exception when error
   */
  @Test
  public void testPoistaPeli65() throws Exception {    // Ajastin: 65
    Ajastin ajastin = new Ajastin(); 
    Profiili matti1 = new Profiili(); 
    ajastin.lisaa(matti1); 
    int id = matti1.getTunnusNro(); 
    Peli dota11 = new Peli(id); dota11.lisaaPeli(id); ajastin.lisaa(dota11); 
    assertEquals("From: Ajastin line: 72", 1, ajastin.annaPelit(matti1).size()); 
    ajastin.poistaPeli(dota11); 
    assertEquals("From: Ajastin line: 74", 0, ajastin.annaPelit(matti1).size()); 
  } // Generated by ComTest END
}