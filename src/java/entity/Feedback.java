/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 *
 * @author HP
 */
public class Feedback {

    private int id,
            orderDetailID,
            reviewerID;
    private int product_id;
    private String reviewTime;
    private int rating;
    private String content;
    private String images;
    private boolean isDisabled;
    private User user;
    private Product product;
    private ProductVariant productVariant;
    private String Status;

    private String reply;

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
    
    

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
        public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public Feedback(int id, int orderDetailID, int reviewerID, String reviewTime, int rating, String content, String images, boolean isDisabled) {
        this.id = id;
        this.orderDetailID = orderDetailID;
        this.reviewerID = reviewerID;
        this.reviewTime = reviewTime;
        this.rating = rating;
        this.content = content;
        this.images = images;
        this.isDisabled = isDisabled;
    }

    public Feedback(int id, int orderDetailID, int reviewerID, String reviewTime, int rating, String content, String images, String Status) {
        this.id = id;
        this.orderDetailID = orderDetailID;
        this.reviewerID = reviewerID;
        this.reviewTime = reviewTime;
        this.rating = rating;
        this.content = content;
        this.images = images;
        this.Status = Status;
    }
    

    public Feedback(int id, int orderDetailID, int reviewerID, int product_id, String reviewTime, int rating, String content, String images, boolean isDisabled) {
        this.id = id;
        this.orderDetailID = orderDetailID;
        this.reviewerID = reviewerID;
        this.product_id = product_id;
        this.reviewTime = reviewTime;
        this.rating = rating;
        this.content = content;
        this.images = images;
        this.isDisabled = isDisabled;
    }

    public Feedback( int orderDetailID, int reviewerID, int product_id, String reviewTime, int rating, String content, String images, boolean isDisabled, String Status) {
        this.orderDetailID = orderDetailID;
        this.reviewerID = reviewerID;
        this.product_id = product_id;
        this.reviewTime = reviewTime;
        this.rating = rating;
        this.content = content;
        this.images = images;
        this.isDisabled = isDisabled;
        this.Status = Status;
    }


    public Feedback(int id, int orderDetailID, int reviewerID, int product_id, String reviewTime, int rating, String content, String images, boolean isDisabled, User user, Product product, ProductVariant productVariant, String Status, String reply) {
        this.id = id;
        this.orderDetailID = orderDetailID;
        this.reviewerID = reviewerID;
        this.product_id = product_id;
        this.reviewTime = reviewTime;
        this.rating = rating;
        this.content = content;
        this.images = images;
        this.isDisabled = isDisabled;
        this.user = user;
        this.product = product;
        this.productVariant = productVariant;
        this.Status = Status;
        this.reply = reply;
    }

    
    
    
    public List<String> getImages() {
        if (images == null || images.isEmpty()) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(images, new TypeReference<List<String>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void setImages1(List<String> imageList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.images = objectMapper.writeValueAsString(imageList);
        } catch (Exception e) {
            e.printStackTrace();
            this.images = "[]"; 
        }
    }
    public Feedback() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(int reviewerID) {
        this.reviewerID = reviewerID;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }
    
    public String getSubContent() {
        int maxLength = 100;
        if (content.length() > maxLength) {
            return content.substring(0, maxLength) + "...";
        } else {
            return content;
        }
    }
    public void setContent(String content) {
        this.content = content;
    }

//    public String getImages() {
//        return images;
//    }

    public void setImages(String images) {
        this.images = images;
    }

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }
    public String getIsDisabled() {
        if (this.isDisabled == true) {
            return "Inactive";
        }
        return "Active";
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
    

}
