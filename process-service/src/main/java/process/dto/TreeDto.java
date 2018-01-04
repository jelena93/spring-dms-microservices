/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process.dto;

/**
 *
 * @author Jelena
 */
public class TreeDto {
    public static final String ACTIVITY_ICON = "glyphicon glyphicon-ok";
    public static final String PROCESS_ICON = "glyphicon glyphicon-folder-open";
    private long id;
    private String parent;
    private String text;
    private String icon;
    private boolean primitive;
    private boolean activity = false;

    public TreeDto(long id, String parent, String text, String icon) {
        this.id = id;
        this.parent = parent;
        this.text = text;
        this.icon = icon;
        this.activity = true;
    }

    public TreeDto(long id, String parent, String text, String icon, boolean primitive) {
        this.id = id;
        this.parent = parent;
        this.text = text;
        this.icon = icon;
        this.primitive = primitive;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getParent() { return parent; }
    public void setParent(String parent) { this.parent = parent; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public boolean isPrimitive() { return primitive; }
    public void setPrimitive(boolean primitive) { this.primitive = primitive; }
    public boolean isActivity() { return activity; }
    public void setActivity(boolean activity) { this.activity = activity; }
    @Override
    public String toString() {
        return "TreeDto{" + "id=" + id + ", parent=" + parent + ", text=" + text + ", icon=" + icon + ", primitive=" + primitive + ", activity=" + activity + '}';
    }
}
