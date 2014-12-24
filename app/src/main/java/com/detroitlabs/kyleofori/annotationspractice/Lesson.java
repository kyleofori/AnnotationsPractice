package com.detroitlabs.kyleofori.annotationspractice;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bobbake4 on 11/13/14.
 */

public class Lesson implements Parcelable {

    private String title;
    private String lessonUrl;
    private String description;
    private boolean isFavorited;
    private String lessonId;

    public Lesson(String title, String lessonUrl, String description, boolean isFavorited, String lessonId){
        this.title = title;
        this.lessonUrl = lessonUrl;
        this.description = description;
        this.isFavorited = isFavorited;
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public String getLessonUrl() {
        return lessonUrl;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setFavorited(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    public Drawable getStar(Context context) {
        Drawable star;
        if(isFavorited()) {
            star = context.getResources().getDrawable(R.drawable.favestar);
        } else {
            star = context.getResources().getDrawable(R.drawable.star_none);
        }
        return star;
    }

    protected Lesson(Parcel in) {
        title = in.readString();
        lessonUrl = in.readString();
        description = in.readString();
        isFavorited = in.readByte() != 0x00;
        lessonId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(lessonUrl);
        dest.writeString(description);
        dest.writeByte((byte) (isFavorited ? 0x01 : 0x00));
        dest.writeString(lessonId);
    }

    @SuppressWarnings("unused")
    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };
}