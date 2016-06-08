package com.hpai.dbhelper;


import java.util.ArrayList;
import java.util.List;

import com.hpai.models.MComment;
import com.hpai.models.MGuessInfo;
import com.hpai.models.MGuessCProduct;
import com.hpai.models.MGuessCTestimoni;
import com.hpai.models.MGuessProduct;
import com.hpai.models.MGuessProfile;
import com.hpai.models.MGuessTestimoni;
import com.hpai.models.MStatus;
import com.hpai.models.MUsers;
import com.hpai.models.TableColumn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection extends SQLiteOpenHelper{

	public DBConnection(Context context) {
		super(context, TableColumn.DATABASE_NAME, null, TableColumn.DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
			create_user(db);
			create_product_category(db);
			create_product(db);
			create_testimoni_category(db);
			create_testimoni(db);
			create_profile(db);
			create_info(db);
			create_status(db);
			create_comment(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		create_user(db);
		create_product_category(db);
		create_product(db);
		create_testimoni_category(db);
		create_testimoni(db);
		create_profile(db);
		create_info(db);
	}
	
	
	//INSERT TO DB
	public void insert_product_category(MGuessCProduct cat_product)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_PRODUCT_CATEGORY_ID, cat_product.getId());
    	values.put(TableColumn.COLUMN_PRODUCT_CATEGORY_TITLE, cat_product.getTitle());
    	values.put(TableColumn.COLUMN_PRODUCT_CATEGORY_IMAGE_URL, cat_product.getImgUrl());
    	db.insert(TableColumn.TABLE_PRODUCT_CATEGORY, null, values);
    	db.close();
	}
	
	public void insert_product(MGuessProduct prod)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_PRODUCT_ID, prod.getId());
    	values.put(TableColumn.COLUMN_PRODUCT_TITLE, prod.getTitle());
    	values.put(TableColumn.COLUMN_PRODUCT_DESC, prod.getDesc());
    	values.put(TableColumn.COLUMN_PRODUCT_IMAGE_URL, prod.getImageUri());
    	values.put(TableColumn.COLUMN_PRODUCT_CAT, prod.getCat());
    	db.insert(TableColumn.TABLE_PRODUCT, null, values);
    	db.close();
	}
	
	public void insert_testimoni_category(MGuessCTestimoni cat_testimoni)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_TESTIMONI_CATEGORY_ID, cat_testimoni.getId());
    	values.put(TableColumn.COLUMN_TESTIMONI_CATEGORY_TITLE, cat_testimoni.getTitle());
    	values.put(TableColumn.COLUMN_TESTIMONI_CATEGORY_DESC, cat_testimoni.getDesc());
    	values.put(TableColumn.COLUMN_TESTIMONI_CATEGORY_IMAGE_URL, cat_testimoni.getImageUrl());
    	db.insert(TableColumn.TABLE_TESTIMONI_CATEGORY, null, values);
    	db.close();
	}
	
	public void insert_testimoni(MGuessTestimoni testimoni)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_TESTIMONI_ID, testimoni.getId());
    	values.put(TableColumn.COLUMN_TESTIMONI_TITLE, testimoni.getTitle());
    	values.put(TableColumn.COLUMN_TESTIMONI_NAME, testimoni.getName());
    	values.put(TableColumn.COLUMN_TESTIMONI_DESC, testimoni.getDesc());
    	values.put(TableColumn.COLUMN_TESTIMONI_IMAGE_URL, testimoni.getImageUrl());
    	values.put(TableColumn.COLUMN_TESTIMONI_CAT, testimoni.getCat());
    	db.insert(TableColumn.TABLE_TESTIMONI, null, values);
    	db.close();
	}

	public void insert_profile(MGuessProfile profile)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_PROFILE_ID, profile.getId());
    	values.put(TableColumn.COLUMN_PROFILE_TITLE, profile.getTitle());
    	values.put(TableColumn.COLUMN_PROFILE_DESC, profile.getDesc());
    	values.put(TableColumn.COLUMN_PROFILE_IMAGE_URL, profile.getImageUrl());
    	db.insert(TableColumn.TABLE_PROFILE, null, values);
    	db.close();
	}
	
	public void insert_info(MGuessInfo info)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_INFO_ID, info.getId());
    	values.put(TableColumn.COLUMN_INFO_TITLE, info.getTitle());
    	values.put(TableColumn.COLUMN_INFO_DESC, info.getDesc());
    	values.put(TableColumn.COLUMN_INFO_IMAGE_URL, info.getImageUrl());
    	values.put(TableColumn.COLUMN_INFO_PDF_URL, info.getPDFUrl());
    	db.insert(TableColumn.TABLE_INFO, null, values);
    	db.close();
	}
	public void insert_user(MUsers user)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_USER_ID, user.getUID());
    	values.put(TableColumn.COLUMN_USER_NAME, user.getName());
    	values.put(TableColumn.COLUMN_USER_TOKEN, user.getToken());
    	values.put(TableColumn.COLUMN_USER_STATUS, user.getStatus());
    	values.put(TableColumn.COLUMN_USER_TIME, user.getTime());
    	values.put(TableColumn.COLUMN_USER_INTERVAL, user.getInterval());
    	values.put(TableColumn.COLUMN_USER_AVATAR, user.getAvatar());
    	db.insert(TableColumn.TABLE_USER, null, values);
    	db.close();
	}
	
	public void insert_status(MStatus status)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_STATUS_ID, status.getStatusID());
    	values.put(TableColumn.COLUMN_STATUS_UID, status.getUID());
    	values.put(TableColumn.COLUMN_STATUS_TEXT, status.getStatusText());
    	values.put(TableColumn.COLUMN_STATUS_TIME, status.getStatusTime());
    	values.put(TableColumn.COLUMN_STATUS_FLAG, status.getStatusFlag());
    	values.put(TableColumn.COLUMN_STATUS_UNAME, status.getStatusUname());
    	values.put(TableColumn.COLUMN_STATUS_PICT, status.getStatusPict());
    	db.insert(TableColumn.TABLE_STATUS, null, values);
    	db.close();
	}
	public void insert_comment(MComment comment)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_COMMENT_ID, comment.getCommentID());
    	values.put(TableColumn.COLUMN_COMMENT_STATUS_ID, comment.getStatusID());
    	values.put(TableColumn.COLUMN_COMMENT_UID, comment.getUID());
    	values.put(TableColumn.COLUMN_COMMENT_TEXT, comment.getCommentText());
    	values.put(TableColumn.COLUMN_COMMENT_DATE, comment.getCommentDate());
    	db.insert(TableColumn.TABLE_COMMENT, null, values);
    	db.close();
	}
	
	
	//Update
	public void update_c_product(MGuessCProduct cat_product)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_PRODUCT_CATEGORY_ID, cat_product.getId());
    	values.put(TableColumn.COLUMN_PRODUCT_CATEGORY_TITLE, cat_product.getTitle());
    	values.put(TableColumn.COLUMN_PRODUCT_CATEGORY_IMAGE_URL, cat_product.getImgUrl());
    	db.update(TableColumn.TABLE_PRODUCT_CATEGORY, values, TableColumn.COLUMN_PRODUCT_CATEGORY_ID + " = ?",new String[] { String.valueOf(cat_product.getId()) });
    	db.close();
	}
	
	public void update_c_testimoni(MGuessCTestimoni cat_testimoni)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_TESTIMONI_CATEGORY_ID, cat_testimoni.getId());
    	values.put(TableColumn.COLUMN_TESTIMONI_CATEGORY_TITLE, cat_testimoni.getTitle());
    	values.put(TableColumn.COLUMN_TESTIMONI_CATEGORY_DESC, cat_testimoni.getDesc());
    	values.put(TableColumn.COLUMN_TESTIMONI_CATEGORY_IMAGE_URL, cat_testimoni.getImageUrl());
    	db.update(TableColumn.TABLE_TESTIMONI_CATEGORY, values, TableColumn.COLUMN_TESTIMONI_CATEGORY_ID + " = ?",new String[] { String.valueOf(cat_testimoni.getId()) });
    	db.close();
	}
	
	public void update_product(MGuessProduct prod)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_PRODUCT_ID, prod.getId());
    	values.put(TableColumn.COLUMN_PRODUCT_TITLE, prod.getTitle());
    	values.put(TableColumn.COLUMN_PRODUCT_DESC, prod.getDesc());
    	values.put(TableColumn.COLUMN_PRODUCT_IMAGE_URL, prod.getImageUri());
    	values.put(TableColumn.COLUMN_PRODUCT_CAT, prod.getCat());
    	db.update(TableColumn.TABLE_PRODUCT, values, TableColumn.COLUMN_PRODUCT_ID + " = ?",new String[] { String.valueOf(prod.getId()) });
    	db.close();
	}
	public void update_testimoni(MGuessTestimoni testimoni)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_TESTIMONI_ID, testimoni.getId());
    	values.put(TableColumn.COLUMN_TESTIMONI_TITLE, testimoni.getTitle());
    	values.put(TableColumn.COLUMN_TESTIMONI_NAME, testimoni.getName());
    	values.put(TableColumn.COLUMN_TESTIMONI_DESC, testimoni.getDesc());
    	values.put(TableColumn.COLUMN_TESTIMONI_IMAGE_URL, testimoni.getImageUrl());
    	values.put(TableColumn.COLUMN_TESTIMONI_CAT, testimoni.getCat());
    	db.update(TableColumn.TABLE_TESTIMONI, values, TableColumn.COLUMN_TESTIMONI_ID + " = ?",new String[] { String.valueOf(testimoni.getId()) });
    	db.close();
	}
	public void update_info(MGuessInfo info)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_INFO_ID, info.getId());
    	values.put(TableColumn.COLUMN_INFO_TITLE, info.getTitle());
    	values.put(TableColumn.COLUMN_INFO_DESC, info.getDesc());
    	values.put(TableColumn.COLUMN_INFO_IMAGE_URL, info.getImageUrl());
    	values.put(TableColumn.COLUMN_INFO_PDF_URL, info.getPDFUrl());
    	db.update(TableColumn.TABLE_INFO, values, TableColumn.COLUMN_INFO_ID + " = ?",new String[] { String.valueOf(info.getId()) });
    	db.close();
	}
	public void update_user(MUsers user)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_USER_ID, user.getUID());
    	values.put(TableColumn.COLUMN_USER_NAME, user.getName());
    	values.put(TableColumn.COLUMN_USER_TOKEN, user.getToken());
    	values.put(TableColumn.COLUMN_USER_STATUS, user.getStatus());
    	values.put(TableColumn.COLUMN_USER_TIME, user.getTime());
    	values.put(TableColumn.COLUMN_USER_INTERVAL, user.getInterval());
    	values.put(TableColumn.COLUMN_USER_AVATAR, user.getAvatar());
    	db.update(TableColumn.TABLE_USER, values, TableColumn.COLUMN_USER_ID + " = ?",new String[] { String.valueOf(user.getUID()) });
    	db.close();
	}
	
	public void update_user_is_login(String status,String uid)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_USER_STATUS, status);
    	db.update(TableColumn.TABLE_USER, values, TableColumn.COLUMN_USER_ID + " = ?",new String[] { String.valueOf(uid) });
    	db.close();
	}
	
	public void update_user_token(String token,String uid)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_USER_TOKEN, token);
    	db.update(TableColumn.TABLE_USER, values, TableColumn.COLUMN_USER_ID + " = ?",new String[] { String.valueOf(uid) });
    	db.close();
	}
	
	public void update_user_reg_id(String regid,String uid)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_USER_REGID, regid);
    	db.update(TableColumn.TABLE_USER, values, TableColumn.COLUMN_USER_ID + " = ?",new String[] { String.valueOf(uid) });
    	db.close();
	}
	
	public void update_user_avatar(String avatar,String uid)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_USER_AVATAR, avatar);
    	db.update(TableColumn.TABLE_USER, values, TableColumn.COLUMN_USER_ID + " = ?",new String[] { String.valueOf(uid) });
    	db.close();
	}
	
	public void update_status(MStatus status)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_STATUS_ID, status.getStatusID());
    	values.put(TableColumn.COLUMN_STATUS_UID, status.getUID());
    	values.put(TableColumn.COLUMN_STATUS_TEXT, status.getStatusText());
    	values.put(TableColumn.COLUMN_STATUS_TIME, status.getStatusTime());
    	values.put(TableColumn.COLUMN_STATUS_FLAG, status.getStatusFlag());
    	values.put(TableColumn.COLUMN_STATUS_UNAME, status.getStatusUname());
    	values.put(TableColumn.COLUMN_STATUS_PICT, status.getStatusPict());
    	db.update(TableColumn.TABLE_STATUS, values, TableColumn.COLUMN_STATUS_ID + " = ?",new String[] { String.valueOf(status.getStatusID()) });
    	db.close();
	}
	public void update_comment(MComment comment)
	{
		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TableColumn.COLUMN_COMMENT_ID, comment.getCommentID());
    	values.put(TableColumn.COLUMN_COMMENT_STATUS_ID, comment.getStatusID());
    	values.put(TableColumn.COLUMN_COMMENT_UID, comment.getUID());
    	values.put(TableColumn.COLUMN_COMMENT_TEXT, comment.getCommentText());
    	values.put(TableColumn.COLUMN_COMMENT_DATE, comment.getCommentDate());
    	db.update(TableColumn.TABLE_COMMENT, values, TableColumn.COLUMN_COMMENT_ID + " = ?",new String[] { String.valueOf(comment.getCommentID()) });
    	db.close();
	}
	
	
	
	
	//LIST ALL
	public List<MGuessCProduct> get_product_category() {
        List<MGuessCProduct> catList = new ArrayList<MGuessCProduct>();
        String selectQuery = "SELECT  * FROM " + TableColumn.TABLE_PRODUCT_CATEGORY + " ORDER BY " + TableColumn.COLUMN_PRODUCT_CATEGORY_ID + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
            	MGuessCProduct cat_prod = new MGuessCProduct(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                catList.add(cat_prod);
            } while (cursor.moveToNext());
        }
        return catList;
    }
	
	public List<MGuessCTestimoni> get_testimoni_category() {
        List<MGuessCTestimoni> catList = new ArrayList<MGuessCTestimoni>();
        String selectQuery = "SELECT  * FROM " + TableColumn.TABLE_TESTIMONI_CATEGORY + " ORDER BY " + TableColumn.COLUMN_TESTIMONI_CATEGORY_ID + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
            	MGuessCTestimoni cat_testimoni = new MGuessCTestimoni(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                catList.add(cat_testimoni);
            } while (cursor.moveToNext());
        }
        return catList;
    }
	
	public List<MGuessProfile> get_profile() {
        List<MGuessProfile> profile = new ArrayList<MGuessProfile>();
        String selectQuery = "SELECT  * FROM " + TableColumn.TABLE_PROFILE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
            	MGuessProfile prof = new MGuessProfile(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                profile.add(prof);
            } while (cursor.moveToNext());
        }
        return profile;
    }
	
	public List<MGuessInfo> get_info() {
        List<MGuessInfo> infoList = new ArrayList<MGuessInfo>();
        String selectQuery = "SELECT  * FROM " + TableColumn.TABLE_INFO + " ORDER BY " + TableColumn.COLUMN_INFO_ID + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
            	MGuessInfo info = new MGuessInfo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), 
            			cursor.getString(3),cursor.getString(4));
                infoList.add(info);
            } while (cursor.moveToNext());
        }
        return infoList;
    }
	
	
	public ArrayList<String> table_list() {
		ArrayList<String> data = new ArrayList<String>();
        String selectQuery = "SELECT  name FROM sqlite_master WHERE type = 'table'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
            	data.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return data;
    }
	
	public List<MStatus> get_status() {
        List<MStatus> statusList = new ArrayList<MStatus>();
        String selectQuery = "SELECT  * FROM " + TableColumn.TABLE_STATUS + " ORDER BY " + TableColumn.COLUMN_STATUS_TIME + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
            	MStatus status = new MStatus(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6));
                statusList.add(status);
            } while (cursor.moveToNext());
        }
        return statusList;
    }
	public List<MComment> get_comment() {
        List<MComment> commentList = new ArrayList<MComment>();
        String selectQuery = "SELECT  * FROM " + TableColumn.TABLE_COMMENT + " ORDER BY " + TableColumn.COLUMN_COMMENT_DATE + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
            	MComment comment = new MComment(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                commentList.add(comment);
            } while (cursor.moveToNext());
        }
        return commentList;
    }
	
	
	
	//LIST BY CATEGORY
	public List<MGuessProduct> get_product_by_category(String id) {
        List<MGuessProduct> productList = new ArrayList<MGuessProduct>();
        String selectQuery = "SELECT  * FROM " + TableColumn.TABLE_PRODUCT + " WHERE " +TableColumn.COLUMN_PRODUCT_CAT+" = '"+id+"' ORDER BY "+TableColumn.COLUMN_PRODUCT_ID + " DESC";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
                MGuessProduct prod = new MGuessProduct(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                productList.add(prod);
            } while (cursor.moveToNext());
        }
        return productList;
    }
	
	public List<MGuessTestimoni> get_testimoni_by_category(String id) {
        List<MGuessTestimoni> testimoniList = new ArrayList<MGuessTestimoni>();
        String selectQuery = "SELECT  * FROM " + TableColumn.TABLE_TESTIMONI + " WHERE " +TableColumn.COLUMN_TESTIMONI_CAT+" = '"+id+"' ORDER BY " + TableColumn.COLUMN_TESTIMONI_ID + " DESC";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
                MGuessTestimoni testimoni = new MGuessTestimoni(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                testimoniList.add(testimoni);
            } while (cursor.moveToNext());
        }
        return testimoniList;
    }
	
	
	//SELECT BY ID
	public MGuessProduct get_product_by_id(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TableColumn.TABLE_PRODUCT, new String[] { TableColumn.COLUMN_PRODUCT_ID,
        		TableColumn.COLUMN_PRODUCT_TITLE, TableColumn.COLUMN_PRODUCT_DESC,TableColumn.COLUMN_PRODUCT_IMAGE_URL}, 
        		TableColumn.COLUMN_PRODUCT_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        MGuessProduct product = new MGuessProduct(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
        		cursor.getString(2), cursor.getString(3));
        
        return product;
    }
	
	public MGuessTestimoni get_testimoni_by_id(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TableColumn.TABLE_TESTIMONI, new String[] { TableColumn.COLUMN_TESTIMONI_ID,
        		TableColumn.COLUMN_TESTIMONI_TITLE, TableColumn.COLUMN_TESTIMONI_NAME,TableColumn.COLUMN_TESTIMONI_DESC,TableColumn.COLUMN_TESTIMONI_IMAGE_URL}, 
        		TableColumn.COLUMN_TESTIMONI_ID+ "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        MGuessTestimoni testimoni = new MGuessTestimoni(Integer.parseInt(cursor.getString(0)), cursor.getString(1), 
        		cursor.getString(2), cursor.getString(3), cursor.getString(4));
        
        return testimoni;
    }
	
	public MGuessInfo get_info_by_id(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TableColumn.TABLE_INFO, new String[] { TableColumn.COLUMN_INFO_ID,
        		TableColumn.COLUMN_INFO_TITLE, TableColumn.COLUMN_INFO_DESC,TableColumn.COLUMN_INFO_DESC,TableColumn.COLUMN_INFO_IMAGE_URL,TableColumn.COLUMN_INFO_PDF_URL}, 
        		TableColumn.COLUMN_TESTIMONI_ID+ "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        MGuessInfo info = new MGuessInfo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), 
    			cursor.getString(3),cursor.getString(4));
        
        return info;
    }
	
	public MGuessCTestimoni get_testimoni_category_by_id(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TableColumn.TABLE_TESTIMONI_CATEGORY, new String[] { TableColumn.COLUMN_TESTIMONI_CATEGORY_ID,
        		TableColumn.COLUMN_TESTIMONI_CATEGORY_TITLE, TableColumn.COLUMN_TESTIMONI_CATEGORY_DESC,TableColumn.COLUMN_TESTIMONI_CATEGORY_IMAGE_URL}, 
        		TableColumn.COLUMN_TESTIMONI_CATEGORY_ID+ "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        MGuessCTestimoni testimoni = new MGuessCTestimoni(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), 
    			cursor.getString(3));
        
        return testimoni;
    }
	
	public MStatus get_status_by_id(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TableColumn.TABLE_STATUS, new String[] { TableColumn.COLUMN_STATUS_ID,
        		TableColumn.COLUMN_STATUS_UID, TableColumn.COLUMN_STATUS_TEXT,TableColumn.COLUMN_STATUS_TIME,TableColumn.COLUMN_STATUS_FLAG}, 
        		TableColumn.COLUMN_STATUS_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        MStatus status = new MStatus(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6));
        return status;
    }
		
	
	//DELETE
	public void delete_product_by_category(String cat)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_PRODUCT + " WHERE " + TableColumn.COLUMN_PRODUCT_CAT + " = '" + cat +"' ");
        db.close();
    }
	
	public void delete_testimoni_by_category(String cat)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_TESTIMONI + " WHERE " + TableColumn.COLUMN_TESTIMONI_CAT + " = '" + cat +"' ");
        db.close();
    }
	
	public void delete_product_by_id(int id)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_PRODUCT + " WHERE " + TableColumn.COLUMN_PRODUCT_ID + " = " + id +" ");
        db.close();
    }
	public void delete_testimoni_by_id(int id)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_TESTIMONI + " WHERE " + TableColumn.COLUMN_TESTIMONI_ID + " = " + id +" ");
        db.close();
    }
	public void delete_info_by_id(int id)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_INFO + " WHERE " + TableColumn.COLUMN_INFO_ID + " = " + id +" ");
        db.close();
    }
	public void delete_status_by_id(String id)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_STATUS + " WHERE " + TableColumn.COLUMN_STATUS_ID + " = " + id +" ");
        db.close();
    }
	public void delete_comment_by_id(String id)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_COMMENT + " WHERE " + TableColumn.COLUMN_COMMENT_ID + " = " + id +" ");
        db.close();
    }
	
	
	
	
	public void delete_c_product(String cat)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_PRODUCT_CATEGORY + " WHERE " + TableColumn.COLUMN_PRODUCT_CATEGORY_ID + " = '" + cat +"' ");
        db.close();
    }
	
	public void delete_c_testimoni(int cat)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_TESTIMONI_CATEGORY + " WHERE " + TableColumn.COLUMN_TESTIMONI_CATEGORY_ID + " = " + cat +" ");
        db.close();
    }
	public void delete_user(String id)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_USER + " WHERE " + TableColumn.COLUMN_USER_ID + " = '" + id +"' ");
        db.close();
    }
	
	
	
	
	//RESET TABLE
	public void reset_product_category()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_PRODUCT_CATEGORY);
        db.close();
    }
	
	public void reset_testimoni_category()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_TESTIMONI_CATEGORY);
        db.close();
    }
	
	public void reset_product()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_PRODUCT);
        db.close();
    }
	
	public void reset_testimoni()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_TESTIMONI);
        db.close();
    }
	
	public void reset_profile()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_PROFILE);
        db.close();
    }
	public void reset_info()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_INFO);
        db.close();
    }
	public void reset_status()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_STATUS);
        db.close();
    }
	public void reset_comment()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableColumn.TABLE_COMMENT);
        db.close();
    }
	
	
	//COUNT DATA
	public int get_count_product_category() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_PRODUCT_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
	
	public int get_count_product(String cat_id) {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_PRODUCT + " WHERE product_cat = '" + cat_id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if (cursor.moveToFirst()) {
        	do{
        		count++;
        	}while(cursor.moveToNext());
        }
        cursor.close();
        return count;
    }
	
	public int get_count_testimoni_category() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_TESTIMONI_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
	
	public int get_count_testimoni(String cat_id) {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_TESTIMONI + " WHERE testimoni_cat = '" + cat_id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if (cursor.moveToFirst()) {
        	do{
        		count++;
        	}while(cursor.moveToNext());
        }
        cursor.close();
        return count;
    }
	
	public int get_count_status() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_TESTIMONI;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if (cursor.moveToFirst()) {
        	do{
        		count++;
        	}while(cursor.moveToNext());
        }
        cursor.close();
        return count;
    }
	
	public int get_count_comment(String status_id) {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_COMMENT + " WHERE status_id = '" + status_id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if (cursor.moveToFirst()) {
        	do{
        		count++;
        	}while(cursor.moveToNext());
        }
        cursor.close();
        return count;
    }
	
	public String getLastUserLogin()
	{
		String uid = "";
		String countQuery = "SELECT  user_id FROM " + TableColumn.TABLE_USER + " ORDER BY "+TableColumn.COLUMN_USER_TIME + " DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.moveToFirst()){
        	do{
        		uid = cursor.getString(0);
        	}while(cursor.moveToNext());
        }
        cursor.close();
        return uid;
	}
	
	public String get_regid(String uid)
	{
		String regid = "";
		String countQuery = "SELECT  registration_id FROM " + TableColumn.TABLE_USER + " WHERE user_id='"+uid+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.moveToFirst()){
        	do{
        		regid = cursor.getString(0);
        	}while(cursor.moveToNext());
        }
        cursor.close();
        return regid;
	}
	
	
	public List<MUsers> userIsLogin()
	{
		List<MUsers> userList = new ArrayList<MUsers>();
		String countQuery = "SELECT  * FROM " + TableColumn.TABLE_USER + " WHERE user_status = 'login'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.moveToFirst()){
        	do{
                userList.add(new MUsers(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4)));
        	}while(cursor.moveToNext());
        }
        cursor.close();
        return userList;
	}
	
	public int get_count_user() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if (cursor.moveToFirst()) {
        	do{
        		count++;
        	}while(cursor.moveToNext());
        }
        cursor.close();
        return count;
    }
	
	public int get_count_profile() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_PROFILE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
	
	
	
	public int get_count_table() {
        String countQuery = "SELECT  count(*) FROM sqlite_master WHERE type='table' AND name != 'android_metadata' AND name != 'sqlite_sequence'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
	
	public int get_count_info() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        if (cursor.moveToFirst()) {
        	do{
        		count++;
        	}while(cursor.moveToNext());
        }
        cursor.close();
        return count;
    }
	
	public boolean check_info_empty() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	public boolean check_product_empty() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	public boolean check_product_category_empty() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_PRODUCT_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	public boolean check_testimony_empty() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_TESTIMONI;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	public boolean check_testimoni_category_empty() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_TESTIMONI_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	public boolean check_profile_empty() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_PROFILE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	public boolean check_status_empty() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_STATUS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	public boolean check_commment_empty() {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_COMMENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	//Check ID
	public boolean check_product_id(int id) {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_PRODUCT + " WHERE product_id="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	public boolean check_testimoni_id(int id) {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_TESTIMONI + " WHERE testimoni_id="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	public boolean check_profile_id(int id) {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_PROFILE + " WHERE profile_id="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	public boolean check_info_id(int id) {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_INFO + " WHERE info_id="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	
	public boolean check_cat_product_id(String id) {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_PRODUCT_CATEGORY + " WHERE product_category_id='"+id+"' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	
	public boolean check_cat_testimoni_id(int id) {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_TESTIMONI_CATEGORY + " WHERE testimoni_category_id="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	
	public boolean check_user_id(String id) {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_USER + " WHERE user_id='"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	
	public boolean check_status_id(String id) {
        String countQuery = "SELECT  * FROM " + TableColumn.TABLE_STATUS + " WHERE status_id='"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        if(cursor.moveToFirst())
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
	
	public List<MUsers> getLastUser()
	{
		List<MUsers> userList = new ArrayList<MUsers>();
		String countQuery = "SELECT  * FROM " + TableColumn.TABLE_USER + " ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.moveToFirst()){
        	do{
                userList.add(new MUsers(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5)));
        	}while(cursor.moveToNext());
        }
        cursor.close();
        return userList;
	}
	
	public List<MUsers> getInterval()
	{
		List<MUsers> userList = new ArrayList<MUsers>();
		String countQuery = "SELECT  * FROM " + TableColumn.TABLE_USER + " ORDER BY "+TableColumn.COLUMN_USER_TIME+" DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.moveToFirst()){
        	do{
                userList.add(new MUsers(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7)));
        	}while(cursor.moveToNext());
        }
        cursor.close();
        return userList;
	}
	
	
	
	
	//CREATE TABLE
	private void create_product_category(SQLiteDatabase db)
	{
		String CREATE_TABLE_PRODUCT_CATEGORY = "CREATE TABLE " + TableColumn.TABLE_PRODUCT_CATEGORY + " (" + TableColumn.COLUMN_PRODUCT_CATEGORY_ID + 
				" TEXT PRIMARY KEY , " + TableColumn.COLUMN_PRODUCT_CATEGORY_TITLE + " TEXT," + 
				TableColumn.COLUMN_PRODUCT_CATEGORY_IMAGE_URL + " TEXT" + ")";
		db.execSQL("DROP TABLE IF EXISTS " + TableColumn.TABLE_PRODUCT_CATEGORY);
		db.execSQL(CREATE_TABLE_PRODUCT_CATEGORY);
	}
	private void create_product(SQLiteDatabase db)
	{
		String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TableColumn.TABLE_PRODUCT + " (" + TableColumn.COLUMN_PRODUCT_ID + 
				" INTEGER PRIMARY KEY , " + TableColumn.COLUMN_PRODUCT_TITLE + " TEXT," + 
				TableColumn.COLUMN_PRODUCT_DESC + " TEXT," + TableColumn.COLUMN_PRODUCT_IMAGE_URL + " TEXT," +TableColumn.COLUMN_PRODUCT_CAT + " TEXT " +")";
		db.execSQL("DROP TABLE IF EXISTS " + TableColumn.TABLE_PRODUCT);
		db.execSQL(CREATE_TABLE_PRODUCT);
	}
	private void create_testimoni_category(SQLiteDatabase db)
	{
		String CREATE_TABLE_TESTIMONI_CATEGORY = "CREATE TABLE " + TableColumn.TABLE_TESTIMONI_CATEGORY + " (" + TableColumn.COLUMN_TESTIMONI_CATEGORY_ID + 
				" INTEGER PRIMARY KEY , " + TableColumn.COLUMN_TESTIMONI_CATEGORY_TITLE + " TEXT," + 
				TableColumn.COLUMN_TESTIMONI_CATEGORY_DESC + " TEXT," + TableColumn.COLUMN_TESTIMONI_CATEGORY_IMAGE_URL + " TEXT " +")";
		db.execSQL("DROP TABLE IF EXISTS " + TableColumn.TABLE_TESTIMONI_CATEGORY);
		db.execSQL(CREATE_TABLE_TESTIMONI_CATEGORY);
	}
	private void create_testimoni(SQLiteDatabase db)
	{
		String CREATE_TABLE_TESTIMONI = "CREATE TABLE " + TableColumn.TABLE_TESTIMONI + " (" + TableColumn.COLUMN_TESTIMONI_ID + 
				" INTEGER PRIMARY KEY , " + TableColumn.COLUMN_TESTIMONI_TITLE + " TEXT," + 
				TableColumn.COLUMN_TESTIMONI_NAME + " TEXT," + TableColumn.COLUMN_TESTIMONI_DESC + " TEXT, "
				+ TableColumn.COLUMN_TESTIMONI_IMAGE_URL + " TEXT, "+ TableColumn.COLUMN_TESTIMONI_CAT + " TEXT "+")";
		db.execSQL("DROP TABLE IF EXISTS " + TableColumn.TABLE_TESTIMONI);
		db.execSQL(CREATE_TABLE_TESTIMONI);
	}
	private void create_profile(SQLiteDatabase db)
	{
		String CREATE_TABLE_PROFILE = "CREATE TABLE " + TableColumn.TABLE_PROFILE + " (" + TableColumn.COLUMN_PROFILE_ID + 
				" INTEGER PRIMARY KEY , " + TableColumn.COLUMN_PROFILE_TITLE + " TEXT," + TableColumn.COLUMN_PROFILE_DESC + " TEXT," 
				+ TableColumn.COLUMN_PROFILE_IMAGE_URL + " TEXT "+")";
		db.execSQL("DROP TABLE IF EXISTS " + TableColumn.TABLE_PROFILE);
		db.execSQL(CREATE_TABLE_PROFILE);
	}
	private void create_info(SQLiteDatabase db)
	{
		String CREATE_TABLE_INFO = "CREATE TABLE " + TableColumn.TABLE_INFO + " (" + TableColumn.COLUMN_INFO_ID + 
				" INTEGER PRIMARY KEY , " + TableColumn.COLUMN_INFO_TITLE + " TEXT," + TableColumn.COLUMN_INFO_DESC + " TEXT," 
				+ TableColumn.COLUMN_INFO_IMAGE_URL + " TEXT, "+ TableColumn.COLUMN_INFO_PDF_URL + " TEXT "+")";
		db.execSQL("DROP TABLE IF EXISTS " + TableColumn.TABLE_INFO);
		db.execSQL(CREATE_TABLE_INFO);
	}
	private void create_user(SQLiteDatabase db)
	{
		String CREATE_TABLE_USER = "CREATE TABLE " + TableColumn.TABLE_USER + " (" + TableColumn.COLUMN_USER_ID + 
				" TEXT PRIMARY KEY , " + TableColumn.COLUMN_USER_NAME + " TEXT," + TableColumn.COLUMN_USER_TOKEN + " TEXT," 
				+ TableColumn.COLUMN_USER_STATUS + " TEXT, "+ TableColumn.COLUMN_USER_TIME+" DATETIME, "+TableColumn.COLUMN_USER_REGID+" TEXT,"+TableColumn.COLUMN_USER_INTERVAL+" TEXT, "+TableColumn.COLUMN_USER_AVATAR+" TEXT )";
		db.execSQL("DROP TABLE IF EXISTS " + TableColumn.TABLE_USER);
		db.execSQL(CREATE_TABLE_USER);
	}
	private void create_status(SQLiteDatabase db)
	{
		String CREATE_TABLE_STATUS = "CREATE TABLE " + TableColumn.TABLE_STATUS + " (" + TableColumn.COLUMN_STATUS_ID + 
				" INTEGER PRIMARY KEY , " + TableColumn.COLUMN_STATUS_UID + " TEXT," + TableColumn.COLUMN_STATUS_TEXT + " TEXT," 
				+ TableColumn.COLUMN_STATUS_TIME + " DATETIME, "+ TableColumn.COLUMN_STATUS_FLAG+" TEXT, "+TableColumn.COLUMN_STATUS_UNAME+" TEXT, "+
				TableColumn.COLUMN_STATUS_PICT+" TEXT )";
		db.execSQL("DROP TABLE IF EXISTS " + TableColumn.TABLE_STATUS);
		db.execSQL(CREATE_TABLE_STATUS);
	}
	private void create_comment(SQLiteDatabase db)
	{
		String CREATE_TABLE_COMMENT = "CREATE TABLE " + TableColumn.TABLE_COMMENT + " (" + TableColumn.COLUMN_COMMENT_ID + 
				" INTEGER PRIMARY KEY , " + TableColumn.COLUMN_COMMENT_STATUS_ID + " INTEGER," + TableColumn.COLUMN_COMMENT_UID + " TEXT," 
				+ TableColumn.COLUMN_COMMENT_TEXT + " TEXT, "+ TableColumn.COLUMN_COMMENT_DATE +" DATETIME )";
		db.execSQL("DROP TABLE IF EXISTS " + TableColumn.TABLE_COMMENT);
		db.execSQL(CREATE_TABLE_COMMENT);
	}
	
}
