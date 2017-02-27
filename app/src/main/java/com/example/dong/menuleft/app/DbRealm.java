package com.example.dong.menuleft.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by DONG on 15-Feb-17.
 */

public class DbRealm extends Application {


    @Override
    public void onCreate() {
        super.onCreate();


        Realm.init(this);

        RealmConfiguration cfg = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(cfg);

//        Realm realm=Realm.getDefaultInstance();
//
//
//        Firebase.setAndroidContext(this);
//        Firebase roof = new Firebase("https://shoponline-57869.firebaseio.com");
//
//
//        //type
//
//        roof.child("database").child("type").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Product product =  postSnapshot.getValue(Product.class);
//                    realm.beginTransaction();
//                    realm.copyToRealmOrUpdate(product);
//                    realm.commitTransaction();
//
//                }
//            }
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });


//        //product
//        roof.child("database").child("product").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Product product =  postSnapshot.getValue(Product.class);
//                    realm.beginTransaction();
//                    realm.copyToRealmOrUpdate(product);
//                    realm.commitTransaction();
//
//                }
//            }
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
    }
}