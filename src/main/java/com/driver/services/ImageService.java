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

        Image image = new Image(description,dimensions,blog);
//        image.setDescription(description);
//        image.setDimensions(dimensions);
//        image.setBlog(blog);

//        List<Image> imageList = blog.getImageList();
//        imageList.add(image);
//        blog.setImageList(imageList);
        blog.getImageList().add(image);

        blogRepository2.save(blog);
        return image;
    }

    public void deleteImage(Integer id){

            Image image = imageRepository2.findById(id).get();

            //finding parent object
            Blog blog = image.getBlog();
            List<Image> imageList = blog.getImageList();
            imageList.remove(image);
            blog.setImageList(imageList);

            blogRepository2.save(blog);
            imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        String [] screenArray = screenDimensions.split("X");
        Image image = imageRepository2.findById(id).get();

        String imageDimensions = image.getDimensions();
        String [] imageArray = imageDimensions.split("X");

        int screenLength = Integer.parseInt(screenArray[0]);
        int screenBreadth = Integer.parseInt(screenArray[1]);

        int imageLength = Integer.parseInt(imageArray[0]);
        int imageBreadth = Integer.parseInt(imageArray[1]);

        return noOfImages(screenLength,screenBreadth,imageLength,imageBreadth);
    }
    private int noOfImages(int screenLength, int screenBreadth, int imageLength, int imageBreadth) {
        int lenC = screenLength/imageLength;
        int lenB = screenBreadth/imageBreadth;
        return lenC*lenB;
    }
}
