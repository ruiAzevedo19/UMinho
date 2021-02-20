import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ChromeTester extends Thread {
     /* --- Class variables --- */
     public WebDriver driver;
     public String url;
     public double[] time;
     public int index;

     /**
      * Parametrized Construtor
      *
      * @param driver Driver for browser
      * @param time   Execution time for operations
      * @param index  Number of operation
      */
     public ChromeTester(WebDriver driver, String url, double[] time, int index) {
          this.driver = driver;
          this.url = url;
          this.time = time;
          this.index = index;
     }

     // --- Waits for loading a page
     public void waitForPageLoaded() {
          ExpectedCondition<Boolean> pageLoadCondition = driver1 -> {
               assert driver1 != null;
               return ( (JavascriptExecutor) driver1 ).executeScript( "return document.readyState" ).equals( "complete" );
          };
          WebDriverWait wait = new WebDriverWait( driver, 30 );
          wait.until( pageLoadCondition );
          try {
               Thread.sleep( 2000 );
          } catch(InterruptedException ie) {
               ie.printStackTrace();
          }
     }

     // --- Login Behaviour
     private void login(boolean close) throws Exception {
          WebElement element;
          driver.get( url + "login" );

          waitForPageLoaded();

          // -- Login
          element = driver.findElement( By.id( "input-10" ) );
          element.sendKeys( "jf5138@gmail.com" );
          element = driver.findElement( By.id( "input-12" ) );
          element.sendKeys( "sdb2020" );
          element.sendKeys( Keys.ENTER );

          if( close ) {  // if the operation is only login, close the browser after the operation
               WebDriverWait wait = new WebDriverWait( driver, 30 );
               wait.until( ExpectedConditions.urlMatches( "http://34.77.156.245:10000" ) );
               Thread.sleep( 2000 );
               driver.quit();
          }
     }

     // --- ( Login + Create page ) behaviour
     private void login_createPage() throws Exception {
          WebElement element;
          WebDriverWait wait = new WebDriverWait( driver, 30 );

          // --- Login
          login( false );

          waitForPageLoaded();
          wait.until( ExpectedConditions.urlMatches( "http://34.77.156.245:10000" ) );

          Thread.sleep( 2000 );

          // --- Click the page to remove left side bar
          driver.findElement( By.xpath( "//html" ) ).click();

          Thread.sleep( 1000 );

          // --- Find create page button and click it
          List<WebElement> buttons = driver.findElements( By.tagName( "button" ) );
          buttons.get( 1 ).click();

          Thread.sleep( 3000 );

          // --- Enters name of new page based on current time
          Random r = new Random();
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern( "yyyyMMddHHmmss" );
          LocalDateTime now = LocalDateTime.now();
          String format = dtf.format( now ) + r.nextInt();
          element = driver.findElement( By.id( "input-103" ) );
          element.sendKeys( format );

          // --- Press select button
          element = driver.findElement( By.xpath( "/html/body/div/div/div[3]/div/div/div[4]/div/button[2]" ) );
          element.click();

          wait.until( ExpectedConditions.urlMatches( "http://34.77.156.245:10000/e/en/new-page" + format ) );

          Thread.sleep( 1000 );

          // --- Choose html editor
          element = driver.findElement( By.xpath( "/html/body/div/div/div[3]/div/div[1]/div/div[2]/div/div[3]/div/div/img" ) );
          element.click();

          Thread.sleep( 2000 );

          // --- Define page name
          element = driver.findElement( By.id( "input-161" ) );
          element.sendKeys( format );

          // --- Confirm
          buttons = driver.findElements( By.tagName( "button" ) );
          buttons.get( 17 ).click();

          Thread.sleep( 1000 );

          // --- Save page

          buttons = driver.findElements( By.tagName( "i" ) );
          buttons.get( 0 ).click();
          waitForPageLoaded();
          wait.until( ExpectedConditions.urlMatches( "http://34.77.156.245:10000/en/new-page" + format ) );

          // --- Close browser
          driver.quit();
     }

     // --- Thread work
     @Override
     public void run() {
          long start = System.currentTimeMillis();

          try{
               //login( true );
               login_createPage();
          }catch(Exception e){
               e.printStackTrace();
               System.out.println("Thread " + Thread.currentThread().getName());
          }

          long end = System.currentTimeMillis();

          time[ index ] = ( end - start ) / 1000.0;
     }

     // --- Creates threads to simulate browser navigation and outputs time report
     public static void main(String[] args) throws Exception {
          String url = "http://34.77.156.245:10000/";
          List<double[]> report = new ArrayList<>();
          int threadsExp = 4;
          int runs = 1;
          ChromeOptions options = new ChromeOptions();
          options.addArguments( "headless" );

          int i, j;
          for(int r = 0 ; r < runs ; r++) {
               for(i = 0; i < threadsExp ; i++) {
                    int numThreads = (int) Math.pow( 2, i );
                    report.add( new double[ numThreads ] );
                    ChromeTester[] chrome = new ChromeTester[ numThreads ];

                    for(j = 0; j < numThreads ; j++) {
                         WebDriver driver = new ChromeDriver( options );
                         chrome[ j ] = new ChromeTester( driver, url, report.get( i ), j );
                    }
                    for(j = 0; j < numThreads ; j++) {
                         chrome[ j ].start();
                    }
                    for(j = 0; j < numThreads ; j++) {
                         chrome[ j ].join();
                         System.out.println( "Ended " + j );
                    }
               }
               double m;
               i = 0;
               int threads;
               for(double[] time : report) {
                    threads = (int) Math.pow( 2, i );
                    m = Arrays.stream( time ).sum() / threads;
                    System.out.println( "Threads " + threads + " : " + m + "s" );
                    i++;
               }
               report.clear();
          }
     }
}