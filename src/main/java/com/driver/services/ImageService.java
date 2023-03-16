package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Blog blog = blogRepository2.findById(blogId).get();

        Image image = new Image();
        image.setDescription(description);
        image.setDimensions(dimensions);
        image.setBlog(blog);

        List<Image> imageList = blog.getImageList();
        imageList.add(image);
        blog.setImageList(imageList);

        blogRepository2.save(blog);
        return image;
    }

    public void deleteImage(Integer id){

        if (imageRepository2.existsById(id)){

            Image image = imageRepository2.findById(id).get();

            //finding parent object
            Blog blog = image.getBlog();
            List<Image> imageList = blog.getImageList();
            imageList.remove(image);
            blog.setImageList(imageList);

            blogRepository2.save(blog);
            imageRepository2.deleteById(id);
        }
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        Image image = imageRepository2.findById(id).get();
        String dimension = image.getDimensions();
        dimension = dimension.toLowerCase();
        screenDimensions = screenDimensions.toLowerCase();
        String[] dimensions = dimension.split("x");
        String[] screenDimension = screenDimensions.split("x");
        int finalDimension = Integer.parseInt(dimensions[0])*Integer.parseInt(dimensions[1]);
        int finalScreenDimension =  Integer.parseInt(screenDimension[0])*Integer.parseInt(screenDimension[1]);

        return finalScreenDimension/finalDimension;

    }
}
