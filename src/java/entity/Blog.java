/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author HP
 */
public class Blog {

    private int id;
    private int authorID;

    private String postTime,
            title,
            content,
            backlinks,
            imageURL,
            status;
    private boolean isSlider;
    private boolean isDisabled;

    public Blog(int id, int authorID, String postTime, String title, String content, String backlinks, String imageURL, String status, boolean isSlider, boolean isDisabled) {
        this.id = id;
        this.authorID = authorID;
        this.postTime = postTime;
        this.title = title;
        this.content = content;
        this.backlinks = backlinks;
        this.imageURL = imageURL;
        this.status = status;
        this.isSlider = isSlider;
        this.isDisabled = isDisabled;
    }
    
   

    public Blog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBacklinks() {
        return backlinks;
    }

    public void setBacklinks(String backlinks) {
        this.backlinks = backlinks;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public boolean isIsSlider() {
        return isSlider;
    }

    public void setIsSlider(boolean isSlider) {
        this.isSlider = isSlider;
    }

    @Override
    public String toString() {
        return "Blog{" + "id=" + id + ", authorID=" + authorID + ", postTime=" + postTime + ", title=" + title + ", content=" + content + ", backlinks=" + backlinks + ", imageURL=" + imageURL + ", status=" + status + ", isSlider=" + isSlider + ", isDisabled=" + isDisabled + '}';
    }

    

}
