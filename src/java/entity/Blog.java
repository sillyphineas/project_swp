
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import org.eclipse.jdt.internal.compiler.ast.FalseLiteral;
import org.eclipse.jdt.internal.compiler.ast.TrueLiteral;

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
    private String authorName;
    
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

    public Blog(int id, int authorID, String postTime, String title, String content, String backlinks, String imageURL, boolean isDisabled) {
        this.id = id;
        this.authorID = authorID;
        this.postTime = postTime;
        this.title = title;
        this.content = content;
        this.backlinks = backlinks;
        this.imageURL = imageURL;
        this.isDisabled = isDisabled;
    }
    
    public Blog() {
    }

    public boolean isIsSlider() {
        return isSlider;
    }

    public void setIsSlider(boolean isSlider) {
        this.isSlider = isSlider;
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

    public String getIsDisabled() {
        if (this.isDisabled == true) {
            return "Disabled";
        }
        return "Active";
    }

    @Override
    public String toString() {
        return "Blog{" + "id=" + id + ", authorID=" + authorID + ", postTime=" + postTime + ", title=" + title + ", content=" + content + ", imageURL=" + imageURL + ", backlinks=" + backlinks + ", status=" + status + ", isDisabled=" + isDisabled + '}';
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

}
