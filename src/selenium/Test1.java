package selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import java.io.File;  
import java.io.FileInputStream;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test1 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  public String [] data2;
  public String [] data1;
  public int col = 2;
  public Integer row;

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://psych.liebes.top/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    row = new Integer(97);
  }

  @Test
  public void test1() throws Exception {
	getdata();
	System.out.println(row);
	for(int i=0; i<row; i++) {
    driver.get(baseUrl + "/st");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys(String.valueOf(data1[i]));
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys(String.valueOf(data1[i]).substring(4));
    driver.findElement(By.id("submitButton")).click();
    driver.findElement(By.cssSelector("p.login-box-msg")).getText();
    assertEquals(driver.findElement(By.cssSelector("p.login-box-msg")).getText(),data2[i]);

	}
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
  
  public void getdata() {          
      try{  
            
          // 指定excel的路径  
          File src = new File(".\\files\\input.xlsx");  
            
          // 加载文件  
          FileInputStream fis = new FileInputStream(src);  
            
          // 加载workbook  
          @SuppressWarnings("resource")  
          XSSFWorkbook wb=new XSSFWorkbook(fis);  
            
          //加载sheet，这里我们只有一个sheet,默认是sheet1  
          XSSFSheet sh1= wb.getSheetAt(0);  
          
          data2  = new String[sh1.getPhysicalNumberOfRows()];
          data1  = new String[sh1.getPhysicalNumberOfRows()];
          row = new Integer(sh1.getPhysicalNumberOfRows());
          for(int i=0;i<sh1.getPhysicalNumberOfRows();i++){  
              // j是控制列，我们上面数据是2列                        
                  data1[i] =  sh1.getRow(i).getCell(0).getStringCellValue();  
                  data2[i] =  sh1.getRow(i).getCell(1).getStringCellValue();
                  System.out.println(i +" "+ data1[i]+ " "+data2[i] + " "+row );
          } 
      } catch (Exception e){   
          System.out.println(e.getMessage());             
      }  

  }  
  
}