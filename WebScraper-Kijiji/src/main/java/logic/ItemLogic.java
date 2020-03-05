/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.ValidationException;
import dal.ItemDAL;
import entity.Item;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map;

/**
 *
 * @author User
 */
public class ItemLogic extends GenericLogic<Item, ItemDAL> {

    public static final String DESCRIPTION = "description";
    public static final String CATEGORY_ID = "categoryId";
    public static final String IMAGE_ID = "imageId";
    public static final String LOCATION = "location";
    public static final String PRICE = "price";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String URL = "url";
    public static final String ID = "id";

    public ItemLogic() {
        super(new ItemDAL());
    }

    @Override
    public List<Item> getAll() {
        return get(() -> dao().findAll());
    }

    @Override
    public Item getWithId(int id) {
        return get(() -> dao().findById(id));
    }

    public List<Item> getWithPrice(BigDecimal price) {
        return get(() -> dao().findByPrice(price));
    }

    public List<Item> getWithTitle(String title) {
        return get(() -> dao().findByTitle(title));
    }

    public List<Item> getWithDate(String date) {
        return get(() -> dao().findByDate(date));
    }

    public List<Item> getWithLocation(String location) {
        return get(() -> dao().findByLocation(location));
    }

    public List<Item> getWithDescription(String description) {
        return get(() -> dao().findByDescription(description));
    }

    public Item getWithUrl(String url) {
        return get(() -> dao().findByUrl(url));
    }

    public List<Item> getWithCategory(int categoryId) {
        return get(() -> dao().findByCategory(categoryId));
    }

    @Override
    public List<Item> search(String search) {
        return get(() -> dao().findContaining(search));
    }

    @Override
    public Item createEntity(Map<String, String[]> parameterMap) {
        Item item = new Item();
        if (parameterMap.containsKey(ID)) {
            item.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
        if (parameterMap.get(DESCRIPTION)[0].equals("") || (parameterMap.get(DESCRIPTION)[0].length() > 255)) {
                throw new ValidationException("Input length must be between 1 to 255 characters");
            }
        item.setDescription(parameterMap.get(DESCRIPTION)[0]);
        if (parameterMap.get(LOCATION)[0].equals("") || (parameterMap.get(LOCATION)[0].length() > 45)) {
                throw new ValidationException("Input length must be between 1 to 45 characters");
            }
        item.setLocation(parameterMap.get(LOCATION)[0]);
        try {
            BigDecimal priceCheck = new BigDecimal(parameterMap.get(PRICE)[0].replace("$", "").replace(",", ""));
            if (priceCheck.toString().equals("") || priceCheck.toString().length()>15) {
                throw new ValidationException("price must be in the given format (for e.g. 120.22) and cannot exceed 15 characters");
            }
            item.setPrice(priceCheck.setScale(2, BigDecimal.ROUND_UP));
        } catch (Exception e) {
            item.setPrice(null);
        }
        if (parameterMap.get(TITLE)[0].equals("") || (parameterMap.get(TITLE)[0].length() > 255)) {
                throw new ValidationException("Input length must be between 1 to 255 characters");
            }
        item.setTitle(parameterMap.get(TITLE)[0]);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            item.setDate(formatter.parse(parameterMap.get(DATE)[0]));
        } catch (ParseException e) {
            Date date = new Date();
            item.setDate(date);
        }
        if (parameterMap.get(URL)[0].equals("") || (parameterMap.get(URL)[0].length() > 255)) {
                throw new ValidationException("Input length must be between 1 to 255 characters");
            }
        item.setUrl(parameterMap.get(URL)[0]);
        return item;
    }

    @Override
    public List<String> getColumnNames() {
        return java.util.Arrays.asList("ID", "Image ID", "Category ID", "Price", "Title", "Date", "Location", "Description", "URL");
    }

    @Override
    public List<String> getColumnCodes() {
        return java.util.Arrays.asList(ID, IMAGE_ID, CATEGORY_ID, PRICE, TITLE, DATE, LOCATION, DESCRIPTION, URL);
    }

    @Override
    public List<?> extractDataAsList(Item e) {
        return Arrays.asList(e.getId(), e.getImage().getId(), e.getCategory().getId(), e.getPrice(), e.getTitle(), e.getDate(), e.getLocation(), e.getDescription(), e.getUrl());
    }

}
