/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import entity.Category;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Dikshit Dikshit
 */
public class CategoryLogicTest {
    private Map<String, String[]> sampleMap;
    private static Tomcat tomcat;
    private CategoryLogic logic;
    private Category expectedCategory;

    @BeforeAll
    final static void setUpBeforeClass() throws Exception {
        System.out.println(new File("src\\main\\webapp\\").getAbsolutePath());
        tomcat = new Tomcat();
        tomcat.enableNaming();
        tomcat.setPort(8080);
        Context context = tomcat.addWebapp("/WebScraper", new File("src\\main\\webapp").getAbsolutePath());
        context.addApplicationListener("dal.EMFactory");
        tomcat.init();
        tomcat.start();
    }

    @AfterAll
    final static void tearDownAfterClass() throws Exception {
        
    }

    @BeforeEach
    final void setUp() throws Exception {
        logic = new CategoryLogic();
        sampleMap = new HashMap<>();
        sampleMap.put(CategoryLogic.TITLE, new String[]{"Junit 5 Test"});
        sampleMap.put(CategoryLogic.URL, new String[]{"junit"});
        expectedCategory = logic.createEntity(sampleMap);
        logic.add(expectedCategory);
    }

    @AfterEach
    final void tearDown() throws Exception {
        if (expectedCategory != null) {
            logic.delete(expectedCategory);
        }
    }
    
    private void assertCategoryEquals(Category testCategory, Category returnedCategory) {
        assertEquals(testCategory.getId(), returnedCategory.getId());
        assertEquals(testCategory.getTitle(), returnedCategory.getTitle());
        assertEquals(testCategory.getUrl(), returnedCategory.getUrl());
    }
    
    @Test
    final void testGetAll() {
        List<Category> list = logic.getAll();
        int originalSize = list.size();
        
        assertNotNull(expectedCategory);
        
        logic.delete(expectedCategory);
        list = logic.getAll();
        assertEquals(originalSize-1, list.size());
    }

    @Test
    final void testGetWithId() {

        
        Category returnedCategory = logic.getWithId(expectedCategory.getId());
        assertCategoryEquals(expectedCategory, returnedCategory);
    }

    @Test
    final void testgetCategoryWithUrl() {
        
        Category returnedCategory = logic.getWithUrl(expectedCategory.getUrl());
        assertCategoryEquals(expectedCategory, returnedCategory);


    }

    @Test
    public void testgetCategoryWithTitle() {
        
        Category returnedCategory = logic.getWithTitle(expectedCategory.getTitle());
        assertCategoryEquals(expectedCategory, returnedCategory);

    }



    @Test
    public void testGetColumnNames() {
        List<String> list1 = logic.getColumnNames();
        List<String> list2= Arrays.asList("ID", "URL", "Title");
        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }

    }

    @Test
    public void testGetColumnCodes() {
        List<String> list1 = logic.getColumnCodes();
        List<String> list2 = Arrays.asList(CategoryLogic.ID, CategoryLogic.URL, CategoryLogic.TITLE);
        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }

    }

    @Test
    public void testExtractDataAsList() {
        
        List<?> list1 = logic.extractDataAsList(expectedCategory);
        List<?> list2 = Arrays.asList(expectedCategory.getId(),expectedCategory.getUrl(),expectedCategory.getTitle());
        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }
    }

    @Test
    public void testCreateEntity() {
        Category returnedCategory = logic.createEntity(sampleMap);
        returnedCategory.setId(expectedCategory.getId());
        assertCategoryEquals(expectedCategory, returnedCategory);

    }

}