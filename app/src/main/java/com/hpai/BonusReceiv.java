package com.hpai;


import com.hpai.gui.Home;
import com.hpai.lib.internal.GridListAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;



public class BonusReceiv extends Activity {

	ListView listNotif;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bonus_receiv);

		final String title[] = getIntent().getExtras().getStringArray("title");
		final String desc[] = getIntent().getExtras().getStringArray("desc");
		final String ids[] = getIntent().getExtras().getStringArray("id");
		final String images[] = getIntent().getExtras().getStringArray("image");
		final String token = getIntent().getExtras().getString("token");
		
		GridListAdapter adapter = new GridListAdapter(BonusReceiv.this, images, title, desc, BonusReceiv.this);
		listNotif = (ListView) findViewById(R.id.listView1);
		listNotif.setAdapter(adapter);
		listNotif.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				try{
					finish();
					Intent i = new Intent(BonusReceiv.this,BonusNotif.class);
					i.putExtra("token", token);
					i.putExtra("id", ids[position]);
					i.putExtra("ids", ids);
					i.putExtra("title", title);
					i.putExtra("desc", desc);
					i.putExtra("image", images);
					startActivity(i);
					
				}
				catch(Exception e)
				{
					AlertDialog.Builder b = new AlertDialog.Builder(BonusReceiv.this);
					b.setMessage(e.getMessage().toString());
					b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					b.create().show();
				}
			}
		});
		
		Button btnBack = (Button) findViewById(R.id.notiflist_btn_back);
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent i = new Intent(BonusReceiv.this,Home.class);
				i.putExtra("module", "");
				i.putExtra("login", "falset");
				startActivity(i);
			}
		});
	}

}
