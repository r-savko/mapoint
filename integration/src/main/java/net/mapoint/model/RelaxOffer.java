package net.mapoint.model;

import java.util.List;

public class RelaxOffer {

    private long id;
    private String title;
    private String shortTitle;
    private String url;
    private List<RelaxParam> params;
    private RelaxTimeTable timetable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<RelaxParam> getParams() {
        return params;
    }

    public void setParams(List<RelaxParam> params) {
        this.params = params;
    }

    public RelaxTimeTable getTimetable() {
        return timetable;
    }

    public void setTimetable(RelaxTimeTable timetable) {
        this.timetable = timetable;
    }

    @Override
    public String toString() {
        return "RelaxOffer{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", shortTitle='" + shortTitle + '\'' +
            ", url='" + url + '\'' +
            ", params=" + params +
            ", timetable=" + timetable +
            '}';
    }
}
