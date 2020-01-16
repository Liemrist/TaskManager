package com.example.taskmanager;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskParcelable implements Parcelable {
    private Boolean isFavorite;
    private String title;
    private String description;


    public TaskParcelable(Parcel in) {
        isFavorite = (Boolean) in.readValue(null);
        title = in.readString();
        description = in.readString();
    }

    public TaskParcelable(String title, String description, Boolean isFavorite) {
        this.title = title;
        this.description = description;
        this.isFavorite = isFavorite;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public static final Creator<TaskParcelable> CREATOR = new Creator<TaskParcelable>() {
        @Override
        public TaskParcelable createFromParcel(Parcel in) {
            return new TaskParcelable(in);
        }

        @Override
        public TaskParcelable[] newArray(int size) {
            return new TaskParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(isFavorite);
        parcel.writeString(title);
        parcel.writeString(description);
    }
}
