package com.notes.notesapp.user.user_repo;

import org.springframework.stereotype.Component;

@Component("userRepoCustom")
public class UserRepoCustomImpl implements UserRepoCustom{
    private String collectionName = "Users";

    @Override
    public String getCollectionName() {
        return collectionName;
    }

    @Override
    public void setCollectionName(String collectionName) {
        this.collectionName =  collectionName;
    }
}
