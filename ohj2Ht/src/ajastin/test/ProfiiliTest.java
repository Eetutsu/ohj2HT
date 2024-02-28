package ajastin.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import ajastin.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2023.04.28 13:07:59 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class ProfiiliTest {



  // Generated by ComTest BEGIN
  /** testGetNimi56 */
  @Test
  public void testGetNimi56() {    // Profiili: 56
    Profiili matti = new Profiili(); 
    matti.annaTiedot(); 
    { String _l_=matti.getNimi(),_r_="Meikäläinen Matti .*"; if ( !_l_.matches(_r_) ) fail("From: Profiili line: 59" + " does not match: ["+ _l_ + "] != [" + _r_ + "]");}; 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAseta96 */
  @Test
  public void testAseta96() {    // Profiili: 96
    Profiili profiili = new Profiili(); 
    assertEquals("From: Profiili line: 98", null, profiili.aseta(1,"Ankka Aku")); 
    assertEquals("From: Profiili line: 99", null, profiili.aseta(2,"Drago")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testRekisteroi182 */
  @Test
  public void testRekisteroi182() {    // Profiili: 182
    Profiili matti1 = new Profiili(); 
    assertEquals("From: Profiili line: 184", 0, matti1.getTunnusNro()); 
    matti1.rekisteroi(); 
    Profiili matti2 = new Profiili(); 
    matti2.rekisteroi(); 
    int n1 = matti1.getTunnusNro(); 
    int n2 = matti2.getTunnusNro(); 
    assertEquals("From: Profiili line: 190", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString224 */
  @Test
  public void testToString224() {    // Profiili: 224
    Profiili profiili = new Profiili(); 
    profiili.parse("3|Eetu Alanen| Tsuppi"); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse249 */
  @Test
  public void testParse249() {    // Profiili: 249
    Profiili profiili = new Profiili(); 
    profiili.parse("   3  |  Eetu Alanen | Tsuppi    C"); 
    assertEquals("From: Profiili line: 252", 3, profiili.getTunnusNro()); 
    profiili.rekisteroi(); 
    int n = profiili.getTunnusNro(); 
    profiili.rekisteroi();  // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEquals275 */
  @Test
  public void testEquals275() {    // Profiili: 275
    Profiili p   = new Profiili(); 
    p.parse("   3  |  Eetu Alanen | Tsuppi     "); 
    Profiili p2  = new Profiili(); 
    p2.parse("   3  |  Eetu Alanen | Tsuppi     "); 
    Profiili  p3 = new Profiili(); 
    p3.parse("   3  |  Peetu Palanen|Tsubban    "); 
    assertEquals("From: Profiili line: 283", true, p.equals(p2)); 
    assertEquals("From: Profiili line: 284", true, p2.equals(p)); 
    assertEquals("From: Profiili line: 285", false, p.equals(p3)); 
    assertEquals("From: Profiili line: 286", false, p3.equals(p2)); 
  } // Generated by ComTest END
}