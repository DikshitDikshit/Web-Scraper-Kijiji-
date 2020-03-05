/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entity.Image;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Dikshit Dikshit
 */
public class ImageLogicTest {

    private ImageLogic logic;
    private static Tomcat tomcat;
    private Map<String, String[]> sampleMap;
    private Image expectedImage;

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
        logic = new ImageLogic();

        sampleMap = new HashMap<>();
        sampleMap.put(ImageLogic.URL, new String[]{"hhhhhhhhhhhhh"});
        sampleMap.put(ImageLogic.NAME, new String[]{"gggggggggggg"});
        sampleMap.put(ImageLogic.PATH, new String[]{"kkkkkkkkkk"});
        expectedImage = logic.createEntity(sampleMap);
        logic.add(expectedImage);

    }

    @AfterEach
    final void tearDown() throws Exception {
        if (expectedImage != null) {
            logic.delete(expectedImage);
        }
    }

    private void assertImageEquals(Image testImage, Image returnedImage) {
        assertEquals(testImage.getId(), returnedImage.getId());
        assertEquals(testImage.getName(), returnedImage.getName());
        assertEquals(testImage.getPath(), returnedImage.getPath());
        assertEquals(testImage.getUrl(), returnedImage.getUrl());
    }

    @Test
    final void testGetAll() {

        List<Image> list = logic.getAll();
        int originalSize = list.size();

        assertNotNull(expectedImage);

        logic.delete(expectedImage);

        list = logic.getAll();
        assertEquals(originalSize - 1, list.size());
    }

    @Test
    final void testGetWithId() {

        Image returnedImage = logic.getWithId(expectedImage.getId());

        assertImageEquals(expectedImage, returnedImage);

    }

    @Test
    final void testGetWithUrl() {

        List<Image> list2 = logic.getWithUrl(expectedImage.getUrl());
        Image returnedImage = list2.get(0);
        assertImageEquals(expectedImage, returnedImage);
    }

    @Test
    final void testGetWithPath() {

        Image returnedImage = logic.getWithPath(expectedImage.getPath());
        assertImageEquals(expectedImage, returnedImage);
    }

    @Test
    final void testWithName() {

        List<Image> list2 = logic.getWithName(expectedImage.getName());

        Image returnedImage = list2.get(0);
        assertImageEquals(expectedImage, returnedImage);
    }

    @Test
    final void testCreateEntity() {
        Image returnedImage = logic.createEntity(sampleMap);
        returnedImage.setId(expectedImage.getId());
        assertImageEquals(expectedImage, returnedImage);
    }

    @Test
    final void testGetColumnNames() {

        List<String> list1 = logic.getColumnNames();
        List<String> list2 = Arrays.asList("ID", "URL", "Name", "Path");

        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }

    }

    @Test
    final void testGetColumnCodes() {

        List<String> list1 = logic.getColumnCodes();
        List<String> list2 = Arrays.asList("id", "url", "name", "path");

        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }

    }

    @Test
    final void testExtractDataAsList() {
        

        List<?> list1 = Arrays.asList(expectedImage.getId(), expectedImage.getUrl(), expectedImage.getName(), expectedImage.getPath());
        List<?> list2 = logic.extractDataAsList(expectedImage);

        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }

    }

}
