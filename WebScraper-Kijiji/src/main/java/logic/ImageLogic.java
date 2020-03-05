/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.ValidationException;
import dal.ImageDAL;
import entity.Image;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author User
 */
public class ImageLogic extends GenericLogic<Image, ImageDAL> {

    public static final String PATH = "path";
    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String ID = "id";

    public ImageLogic() {
        super(new ImageDAL());
    }

    @Override
    public List<Image> getAll() {
        return get(() -> dao().findAll());
    }

    @Override
    public Image getWithId(int id) {
        return get(() -> dao().findById(id));
    }

    public List<Image> getWithUrl(String url) {
        return get(() -> dao().findByUrl(url));

    }

    public Image getWithPath(String path) {
        return get(() -> dao().findByPath(path));

    }

    public List<Image> getWithName(String name) {
        return get(() -> dao().findByName(name));

    }

    @Override
    public List<Image> search(String search) {
        return get(() -> dao().findContaining(search));

    }

    @Override
    public Image createEntity(Map<String, String[]> parameterMap) {
        Image image = new Image();
        if (parameterMap.containsKey(ID)) {
            image.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
        if (parameterMap.get(URL)[0].isEmpty() || (parameterMap.get(URL)[0].length() > 255)) {
                throw new ValidationException("Input length must be between 1 to 255 characters");
            }
        image.setUrl(parameterMap.get(URL)[0]);
        if (parameterMap.get(NAME)[0].isEmpty() || (parameterMap.get(NAME)[0].length() > 255)) {
                throw new ValidationException("Input length must be between 1 to 255 characters");
            }
        image.setName(parameterMap.get(NAME)[0]);
        if (parameterMap.get(PATH)[0].isEmpty() || (parameterMap.get(PATH)[0].length() > 255)) {
                throw new ValidationException("Input length must be between 1 to 255 characters");
            }
        image.setPath(parameterMap.get(PATH)[0]);
        return image;
    }   

    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("ID", "URL", "Name", "Path");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, URL, NAME, PATH);
    }

    @Override
    public List<?> extractDataAsList(Image e) {
        return Arrays.asList(e.getId(), e.getUrl(), e.getName(), e.getPath());
    }

}
