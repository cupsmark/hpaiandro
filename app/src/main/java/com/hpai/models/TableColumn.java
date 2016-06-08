package com.hpai.models;

public class TableColumn {

	public static final String DATABASE_NAME = "hpai";
	public static final int DATABASE_VERSION = 9;
	
	public static final String TABLE_PRODUCT_CATEGORY = "table_product_category";
	public static final String TABLE_PRODUCT = "table_product";
	public static final String TABLE_TESTIMONI = "table_testimoni";
	public static final String TABLE_TESTIMONI_CATEGORY = "table_testimoni_category";
	public static final String TABLE_PROFILE = "table_profile";
	public static final String TABLE_INFO = "table_info";
	public static final String TABLE_USER = "table_user";
	public static final String TABLE_STATUS = "table_status";
	public static final String TABLE_COMMENT = "table_comment";

	
	//For PRODUCT category table
	public static final String COLUMN_PRODUCT_CATEGORY_ID = "product_category_id";
	public static final String COLUMN_PRODUCT_CATEGORY_TITLE = "product_category_title";
	public static final String COLUMN_PRODUCT_CATEGORY_IMAGE_URL = "product_category_image_url";
	
	//For PRODUCT table
	public static final String COLUMN_PRODUCT_ID = "product_id";
	public static final String COLUMN_PRODUCT_TITLE = "product_title";
	public static final String COLUMN_PRODUCT_IMAGE_URL = "product_image_url";
	public static final String COLUMN_PRODUCT_DESC = "product_desc";
	public static final String COLUMN_PRODUCT_CAT = "product_cat";
	
	//For TESTIMONI Table
	public static final String COLUMN_TESTIMONI_ID = "testimoni_id";
	public static final String COLUMN_TESTIMONI_NAME = "testimoni_name";
	public static final String COLUMN_TESTIMONI_TITLE = "testimoni_title";
	public static final String COLUMN_TESTIMONI_IMAGE_URL = "testimoni_image_url";
	public static final String COLUMN_TESTIMONI_DESC = "testimoni_desc";
	public static final String COLUMN_TESTIMONI_CAT= "testimoni_cat";
	
	
	//For TESTIMONI Category Table
	public static final String COLUMN_TESTIMONI_CATEGORY_ID = "testimoni_category_id";
	public static final String COLUMN_TESTIMONI_CATEGORY_TITLE = "testimoni_category_title";
	public static final String COLUMN_TESTIMONI_CATEGORY_IMAGE_URL = "testimoni_category_image_url";
	public static final String COLUMN_TESTIMONI_CATEGORY_DESC = "testimoni_category_desc";
	
	//For Profile Table
	public static final String COLUMN_PROFILE_ID = "profile_id";
	public static final String COLUMN_PROFILE_TITLE = "profile_title";
	public static final String COLUMN_PROFILE_IMAGE_URL = "profile_image_url";
	public static final String COLUMN_PROFILE_DESC = "profile_desc";
	
	//For Info Table
	public static final String COLUMN_INFO_ID = "info_id";
	public static final String COLUMN_INFO_TITLE = "info_title";
	public static final String COLUMN_INFO_DESC = "info_desc";
	public static final String COLUMN_INFO_IMAGE_URL = "info_image_url";
	public static final String COLUMN_INFO_PDF_URL = "info_pdf_url";
	
	public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_USER_NAME = "user_name";
	public static final String COLUMN_USER_TOKEN = "user_token";
	public static final String COLUMN_USER_STATUS = "user_status";
	public static final String COLUMN_USER_TIME = "user_time";
	public static final String COLUMN_USER_REGID = "registration_id";
	public static final String COLUMN_USER_INTERVAL = "user_interval";
	public static final String COLUMN_USER_AVATAR = "user_avatar";
	 
	public static final String COLUMN_STATUS_ID = "status_id";
	public static final String COLUMN_STATUS_UID = "uid";
	public static final String COLUMN_STATUS_TEXT = "status_text";
	public static final String COLUMN_STATUS_TIME = "status_time";
	public static final String COLUMN_STATUS_FLAG = "status_flag";
	public static final String COLUMN_STATUS_UNAME = "status_uname";
	public static final String COLUMN_STATUS_PICT = "status_pict";
	
	public static final String COLUMN_COMMENT_ID = "comment_id";
	public static final String COLUMN_COMMENT_STATUS_ID = "status_id";
	public static final String COLUMN_COMMENT_UID = "uid";
	public static final String COLUMN_COMMENT_TEXT = "comment_text";
	public static final String COLUMN_COMMENT_DATE = "comment_date";
	
}
