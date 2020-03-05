/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entity.Image;
import entity.Item;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

/**
 *
 * @author Dikshit
 */
public class ItemLogicTest {

    private Map<String, String[]> sampleMap;
    private ItemLogic logic;
    private Item expectedItem;
    private Image image;
    private ImageLogic imgLogic;
    private static Tomcat tomcat;

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
        tomcat.stop();
    }

    @BeforeEach
    final void setUp() throws Exception {
        logic = new ItemLogic();

        sampleMap = new HashMap<>();
        sampleMap.put(ItemLogic.DATE, new String[]{"01/01/2019"});
        sampleMap.put(ItemLogic.DESCRIPTION, new String[]{"junit5"});
        sampleMap.put(ItemLogic.LOCATION, new String[]{"junit5"});
        sampleMap.put(ItemLogic.PRICE, new String[]{"12.00"});
        sampleMap.put(ItemLogic.TITLE, new String[]{"junit5"});
        sampleMap.put(ItemLogic.URL, new String[]{"junit5"});
        expectedItem = logic.createEntity(sampleMap);
        expectedItem.setId(3);
        expectedItem.setCategory(new CategoryLogic().getAll().get(0));
        imgLogic = new ImageLogic();
        List<Image> imgList = imgLogic.getAll();

        if (imgList.size() > 0) {
            expectedItem.setImage(imgList.get(0));
        } else {
            image = new Image();
            image.setName("test");
            image.setPath("test");
            image.setUrl("test");
            
            imgLogic.add(image);
            
            expectedItem.setImage(imgLogic.getWithId(image.getId()));
        }
        logic.add(expectedItem);
    }

    @AfterEach
    final void tearDown() throws Exception {
        if (expectedItem != null) {
            logic.delete(expectedItem);
        }
    }

    @Test
    public void testGetAll() {
        List<Item> list = logic.getAll();
        int originalSize = list.size();

        assertNotNull(expectedItem);

        logic.delete(expectedItem);
        list = logic.getAll();
        assertEquals(originalSize - 1, list.size());
    }

    private void assertAccountEquals(Item testItem, Item returnedItem) {
        //assert all field to guarantee they are the same
        assertEquals(testItem.getId(), returnedItem.getId());
        assertEquals(testItem.getPrice(), returnedItem.getPrice());
        assertEquals(testItem.getTitle(), returnedItem.getTitle());
        assertEquals(testItem.getLocation(), returnedItem.getLocation());
        assertEquals(testItem.getDescription(), returnedItem.getDescription());
        assertEquals(testItem.getUrl(), returnedItem.getUrl());
        assertEquals(testItem.getCategory(), returnedItem.getCategory());
    }

    @Test
    final void testGetWithId() {

        Item returnedItem = logic.getWithId(expectedItem.getId());
        assertAccountEquals(expectedItem, returnedItem);
    }

    @Test
    final void testGetWithPrice() {
        List<Item> returnedList = logic.getWithPrice(expectedItem.getPrice());
        Item returnedItem = returnedList.get(0);
        assertAccountEquals(expectedItem, returnedItem);
    }

    @Test
    final void testGetWithTitle() {

        List<Item> returnedList = logic.getWithTitle(expectedItem.getTitle());
        Item returnedItem = returnedList.get(0);
        assertAccountEquals(expectedItem, returnedItem);
    }

    @Test
    final void testGetWithLocation() {

        List<Item> list = logic.getWithLocation(expectedItem.getLocation());
        Item returnedItem = list.get(0);

        assertAccountEquals(expectedItem, returnedItem);

    }

    @Test
    final void testGetWithDescription() {

        List<Item> list = logic.getWithDescription(expectedItem.getDescription());
        Item returnedItem = list.get(0);

        assertAccountEquals(expectedItem, returnedItem);
    }

    @Test
    final void testGetWithUrl() {

        Item returnedItem = logic.getWithUrl(expectedItem.getUrl());

        assertAccountEquals(expectedItem, returnedItem);
    }

    @Test
    final void testCreateEntity() {
        Item returnedItem = logic.createEntity(sampleMap);
        returnedItem.setCategory(new CategoryLogic().getAll().get(0));
        returnedItem.setImage(image);
        returnedItem.setId(3);
        assertAccountEquals(expectedItem, returnedItem);
    }

    @Test
    final void testGetColumnNames() {
        List<String> list = logic.getColumnNames();

        assertEquals(Arrays.asList("ID", "Image ID", "Category ID", "Price", "Title", "Date", "Location", "Description", "URL"), list);
    }

    @Test
    final void testGetColumnCodes() {

        List<String> list = logic.getColumnCodes();
        assertEquals(Arrays.asList(ItemLogic.ID, ItemLogic.IMAGE_ID, ItemLogic.CATEGORY_ID, ItemLogic.PRICE, ItemLogic.TITLE, ItemLogic.DATE, ItemLogic.LOCATION, ItemLogic.DESCRIPTION, ItemLogic.URL), list);

    }

    @Test
    final void testExtractDataAsList() {

        List<?> list1 = logic.extractDataAsList(expectedItem);
        List<?> list2 = Arrays.asList(expectedItem.getId(), expectedItem.getImage().getId(), expectedItem.getCategory().getId(), expectedItem.getPrice(), expectedItem.getTitle(), expectedItem.getDate(), expectedItem.getLocation(), expectedItem.getDescription(), expectedItem.getUrl());

        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }

    }

}
